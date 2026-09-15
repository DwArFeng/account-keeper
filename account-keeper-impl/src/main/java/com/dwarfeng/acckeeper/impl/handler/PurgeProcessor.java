package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.dutil.basic.sdk.number.NumberUtil;
import com.dwarfeng.dutil.basic.sdk.time.TimeMeasurer;
import com.dwarfeng.dutil.basic.stack.number.unit.Time;
import com.dwarfeng.subgrade.basic.stack.bean.dto.PagingInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 清除处理器。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
@Component
public class PurgeProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurgeProcessor.class);

    private final DeriveHistoryMaintainService deriveHistoryMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;

    private final PushHandler pushHandler;

    private final ThreadPoolTaskScheduler scheduler;

    private final PurgeTask purgeTask = new PurgeTask();

    private final Lock lock = new ReentrantLock();

    private ScheduledFuture<?> purgeTaskScheduledFuture;

    /**
     * 上一次保留的派生历史距离清除时间的偏移量。
     *
     * <p>
     * 该变量用于判断清除派生历史是否发散（即每次清除的数据小于生成的数据）。
     */
    private long lastRetentionDeriveHistoryOffset = 0;

    /**
     * 上一次保留的登录历史距离清除时间的偏移量。
     *
     * <p>
     * 该变量用于判断清除登录历史是否发散（即每次清除的数据小于生成的数据）。
     */
    private long lastRetentionLoginHistoryOffset = 0;

    @Value("${com.dwarfeng.acckeeper.purge.retention_duration}")
    private Long purgeRetentionDuration;

    @Value("${com.dwarfeng.acckeeper.purge.task_cron}")
    private String purgeTaskCron;

    @Value("${com.dwarfeng.acckeeper.purge.max_page_size}")
    private int purgeMaxPageSize;
    @Value("${com.dwarfeng.acckeeper.purge.max_deletion_size}")
    private int purgeMaxDeletionSize;

    public PurgeProcessor(
            DeriveHistoryMaintainService deriveHistoryMaintainService,
            LoginHistoryMaintainService loginHistoryMaintainService,
            PushHandler pushHandler,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.deriveHistoryMaintainService = deriveHistoryMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.pushHandler = pushHandler;
        this.scheduler = scheduler;
    }

    public void work() {
        lock.lock();
        try {
            if (Objects.nonNull(purgeTaskScheduledFuture)) {
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
            // 如果 purgeRetentionDuration <= 0 则不启动清除计划。
            if (purgeRetentionDuration <= 0) {
                LOGGER.info(ImplMessages.message(ImplMessageKey.LOG_PURGE_PLAN_NOT_STARTED));
            }
            // 否则启动清除计划。
            else {
                LOGGER.info(ImplMessages.message(
                        ImplMessageKey.LOG_PURGE_PLAN_STARTED, purgeRetentionDuration, purgeTaskCron
                ));
                LOGGER.info(ImplMessages.message(
                        ImplMessageKey.LOG_PURGE_RETENTION_DURATION,
                        new DecimalFormat("0.00").format(
                                NumberUtil.unitTrans(purgeRetentionDuration, Time.MS, Time.DAY).doubleValue()
                        )
                ));
                purgeTaskScheduledFuture = scheduler.schedule(purgeTask, new CronTrigger(purgeTaskCron));
            }
        } finally {
            lock.unlock();
        }
    }

    public void rest() {
        lock.lock();
        try {
            if (Objects.isNull(purgeTaskScheduledFuture)) {
                return;
            }
            purgeTaskScheduledFuture.cancel(false);
            purgeTaskScheduledFuture = null;
        } finally {
            lock.unlock();
        }
    }

    private class PurgeTask implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                run0();
            } finally {
                lock.unlock();
            }
        }

        private void run0() {
            // 二次检查 purgeRetentionDuration 是否大于 0。
            if (purgeRetentionDuration <= 0) {
                LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_TASK_SKIPPED_INTERNAL));
                return;
            }

            // 定义清除结果。
            PurgeFinishedResult purgeFinishedResult = null;

            try {
                // 日志记录。
                LOGGER.info(ImplMessages.message(ImplMessageKey.LOG_PURGE_TASK_STARTING));
                // 计算保留日期。
                LOGGER.debug(ImplMessages.message(ImplMessageKey.LOG_PURGE_RETENTION_DATE_CALCULATING));
                Date retentionDate = new Date(System.currentTimeMillis() - purgeRetentionDuration);
                LOGGER.debug(ImplMessages.message(ImplMessageKey.LOG_PURGE_RETENTION_DATE, retentionDate));
                // 定义计时器。
                TimeMeasurer tm;
                // 清除派生历史。
                LOGGER.info(ImplMessages.message(ImplMessageKey.LOG_PURGE_DERIVE_HISTORY_STARTING));
                tm = new TimeMeasurer();
                tm.start();
                PurgeResult deriveHistoryPurgeResult = purgeDeriveHistory(retentionDate);
                int deriveHistoryDeletionCount = deriveHistoryPurgeResult.deletionCount();
                boolean deriveHistoryDivergent = deriveHistoryPurgeResult.divergent();
                tm.stop();
                LOGGER.info(ImplMessages.message(
                        ImplMessageKey.LOG_PURGE_DERIVE_HISTORY_FINISHED, deriveHistoryDeletionCount, tm.getTimeMs()
                ));
                // 清除登录历史。
                LOGGER.info(ImplMessages.message(ImplMessageKey.LOG_PURGE_LOGIN_HISTORY_STARTING));
                tm = new TimeMeasurer();
                tm.start();
                PurgeResult loginHistoryPurgeResult = purgeLoginHistory(retentionDate);
                int loginHistoryDeletionCount = loginHistoryPurgeResult.deletionCount();
                boolean loginHistoryDivergent = loginHistoryPurgeResult.divergent();
                tm.stop();
                LOGGER.info(ImplMessages.message(
                        ImplMessageKey.LOG_PURGE_LOGIN_HISTORY_FINISHED, loginHistoryDeletionCount, tm.getTimeMs()
                ));
                // 构造推送结果。
                purgeFinishedResult = new PurgeFinishedResult(
                        deriveHistoryDeletionCount, deriveHistoryDivergent,
                        loginHistoryDeletionCount, loginHistoryDivergent
                );
                // 日志记录。
                LOGGER.info(ImplMessages.message(ImplMessageKey.LOG_PURGE_TASK_FINISHED));
            } catch (Exception e) {
                // 日志记录。
                LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_TASK_FAILED), e);
            }

            // 如果清除结果不为 null，则代表清除成功；否则清除失败。分别推送相应的事件。
            if (Objects.nonNull(purgeFinishedResult)) {
                // 推送清除完成事件。
                try {
                    pushHandler.purgeFinished(purgeFinishedResult);
                } catch (Exception e) {
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_FINISHED_PUSH_FAILED), e);
                }
            } else {
                // 推送清除失败事件。
                try {
                    pushHandler.purgeFailed();
                } catch (Exception e) {
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_FAILED_PUSH_FAILED), e);
                }
            }
        }

        private PurgeResult purgeDeriveHistory(Date retentionDate) throws Exception {
            // 定义返回结果的参数。
            int deletionCount = 0;
            boolean divergent = false;

            // 分页查询并删除，直至全部删除或达到最大删除数量。
            while (deletionCount < purgeMaxDeletionSize) {
                // 确定当前页的大小。
                int pageSize = Math.min(purgeMaxPageSize, purgeMaxDeletionSize - deletionCount);
                // 按照当前页大小查询待清除的派生历史数据。
                List<DeriveHistory> deriveHistories = deriveHistoryMaintainService.lookupAsList(
                        DeriveHistoryMaintainService.TO_PURGED,
                        new Object[]{retentionDate},
                        new PagingInfo(0, pageSize)
                );
                if (deriveHistories.isEmpty()) {
                    // 没有更多数据可删除
                    break;
                }
                // 调用维护服务批量删除当前页的数据。
                deriveHistoryMaintainService.batchDelete(
                        deriveHistories.stream().map(DeriveHistory::getKey).collect(Collectors.toList())
                );
                // 增加删除数量。
                deletionCount += deriveHistories.size();
            }

            // 查询待清除的第一个数据。
            DeriveHistory deriveHistory = deriveHistoryMaintainService.lookupFirst(
                    DeriveHistoryMaintainService.TO_PURGED,
                    new Object[]{retentionDate}
            );

            // 如果第一个数据是 null，说明过期的数据全部删除完毕。
            if (Objects.isNull(deriveHistory)) {
                // 将 lastRetentionDeriveHistoryOffset 置为 0。
                lastRetentionDeriveHistoryOffset = 0;
            }
            // 如果第一个数据不是 null，说明有数据未被清除。
            else {
                // 计算偏移量。
                long currentRetentionDeriveHistoryOffset =
                        retentionDate.getTime() - deriveHistory.getHappenedDate().getTime();
                // 如果偏移量大于上一次的偏移量，说明清除发散，记录日志。
                if (currentRetentionDeriveHistoryOffset > lastRetentionDeriveHistoryOffset) {
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_DERIVE_HISTORY_DIVERGENT));
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_DERIVE_HISTORY_DIVERGENT_HINT));
                    divergent = true;
                }
                // 更新 lastRetentionDeriveHistoryOffset。
                lastRetentionDeriveHistoryOffset = currentRetentionDeriveHistoryOffset;
            }

            // 生成 PurgeResult 并返回。
            return new PurgeResult(deletionCount, divergent);
        }

        private PurgeResult purgeLoginHistory(Date retentionDate) throws Exception {
            // 定义返回结果的参数。
            int deletionCount = 0;
            boolean divergent = false;

            // 分页查询并删除，直至全部删除或达到最大删除数量。
            while (deletionCount < purgeMaxDeletionSize) {
                // 确定当前页的大小。
                int pageSize = Math.min(purgeMaxPageSize, purgeMaxDeletionSize - deletionCount);
                // 按照当前页大小查询待清除的登录历史数据。
                List<LoginHistory> loginHistories = loginHistoryMaintainService.lookupAsList(
                        LoginHistoryMaintainService.TO_PURGED,
                        new Object[]{retentionDate},
                        new PagingInfo(0, pageSize)
                );
                if (loginHistories.isEmpty()) {
                    // 没有更多数据可删除
                    break;
                }
                // 调用维护服务批量删除当前页的数据。
                loginHistoryMaintainService.batchDelete(
                        loginHistories.stream().map(LoginHistory::getKey).collect(Collectors.toList())
                );
                // 增加删除数量。
                deletionCount += loginHistories.size();
            }

            // 查询待清除的第一个数据。
            LoginHistory loginHistory = loginHistoryMaintainService.lookupFirst(
                    LoginHistoryMaintainService.TO_PURGED,
                    new Object[]{retentionDate}
            );

            // 如果第一个数据是 null，说明过期的数据全部删除完毕。
            if (Objects.isNull(loginHistory)) {
                // 将 lastRetentionLoginHistoryOffset 置为 0。
                lastRetentionLoginHistoryOffset = 0;
            }
            // 如果第一个数据不是 null，说明有数据未被清除。
            else {
                // 计算偏移量。
                long currentRetentionLoginHistoryOffset =
                        retentionDate.getTime() - loginHistory.getHappenedDate().getTime();
                // 如果偏移量大于上一次的偏移量，说明清除发散，记录日志。
                if (currentRetentionLoginHistoryOffset > lastRetentionLoginHistoryOffset) {
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_LOGIN_HISTORY_DIVERGENT));
                    LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PURGE_LOGIN_HISTORY_DIVERGENT_HINT));
                    divergent = true;
                }
                // 更新 lastRetentionLoginHistoryOffset。
                lastRetentionLoginHistoryOffset = currentRetentionLoginHistoryOffset;
            }

            // 生成 PurgeResult 并返回。
            return new PurgeResult(deletionCount, divergent);
        }
    }

    private record PurgeResult(int deletionCount, boolean divergent) {

        @Override
        public @NotNull String toString() {
            return "PurgeResult{" +
                    "deletionCount=" + deletionCount +
                    ", divergent=" + divergent +
                    '}';
        }
    }
}
