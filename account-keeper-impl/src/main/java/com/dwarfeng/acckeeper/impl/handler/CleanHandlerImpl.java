package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.CleanHandler;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class CleanHandlerImpl implements CleanHandler {

    private final GeneralStartableHandler delegate;

    public CleanHandlerImpl(CleanWorker worker) {
        this.delegate = new GeneralStartableHandler(worker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return delegate.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        delegate.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        delegate.stop();
    }

    @Component
    public static class CleanWorker implements Worker {

        private final ApplicationContext ctx;

        private final ThreadPoolTaskScheduler scheduler;

        @Value("${clean.expired_login_state.cron}")
        private String expiredLoginStateCron;

        private Future<?> expiredLoginStateFuture;

        public CleanWorker(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
            this.ctx = ctx;
            this.scheduler = scheduler;
        }

        @Override
        public void work() {
            ExpiredLoginStateCleanTask task = ctx.getBean(ExpiredLoginStateCleanTask.class);
            expiredLoginStateFuture = scheduler.schedule(task, new CronTrigger(expiredLoginStateCron));
        }

        @Override
        public void rest() {
            expiredLoginStateFuture.cancel(true);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ExpiredLoginStateCleanTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredLoginStateCleanTask.class);

        private final LoginStateMaintainService loginStateMaintainService;

        public ExpiredLoginStateCleanTask(LoginStateMaintainService loginStateMaintainService) {
            this.loginStateMaintainService = loginStateMaintainService;
        }

        @Override
        public void run() {
            try {
                // 记录日志。
                LOGGER.info("清理登录状态...");

                // 定义变量。
                int cleanedCount = 0;
                List<LongIdKey> expiredLoginStateKeysToDelete;

                // 清理没有关联账户的登录状态。
                expiredLoginStateKeysToDelete = loginStateMaintainService.lookupAsList(
                        LoginStateMaintainService.WITHOUT_ACCOUNT, new Object[0]
                ).stream().map(LoginState::getKey).collect(Collectors.toList());
                loginStateMaintainService.batchDelete(expiredLoginStateKeysToDelete);
                LOGGER.debug("清理没有关联账户的登录状态, 共 {} 个", expiredLoginStateKeysToDelete.size());
                cleanedCount += expiredLoginStateKeysToDelete.size();

                // 清理过期的登录状态。
                expiredLoginStateKeysToDelete = loginStateMaintainService.lookupAsList(
                        LoginStateMaintainService.EXPIRE_DATE_BEFORE_NOW, new Object[0]
                ).stream().map(LoginState::getKey).collect(Collectors.toList());
                loginStateMaintainService.batchDelete(expiredLoginStateKeysToDelete);
                LOGGER.debug("清理过时的登录状态, 共 {} 个", expiredLoginStateKeysToDelete.size());
                cleanedCount += expiredLoginStateKeysToDelete.size();

                // 清理序列版本小于对应账户序列版本的登录状态。
                expiredLoginStateKeysToDelete = loginStateMaintainService.lookupAsList(
                        LoginStateMaintainService.SERIAL_VERSION_LOWER_THAN_ACCOUNT, new Object[0]
                ).stream().map(LoginState::getKey).collect(Collectors.toList());
                loginStateMaintainService.batchDelete(expiredLoginStateKeysToDelete);
                LOGGER.debug("清理序列版本小于对应账户序列版本的登录状态, 共 {} 个", expiredLoginStateKeysToDelete.size());
                cleanedCount += expiredLoginStateKeysToDelete.size();

                // 记录日志。
                LOGGER.info("清理登录状态完成, 共清理 {} 个登录状态", cleanedCount);
            } catch (Exception e) {
                LOGGER.warn("清理过期登录状态时发送异常, 清理未完成, 异常信息如下: ", e);
            }
        }
    }
}
