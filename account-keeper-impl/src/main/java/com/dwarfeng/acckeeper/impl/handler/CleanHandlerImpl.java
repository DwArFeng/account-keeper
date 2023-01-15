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

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class CleanHandlerImpl implements CleanHandler {

    private final GeneralStartableHandler delegate;

    public CleanHandlerImpl(CleanupWorker worker) {
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
    public static class CleanupWorker implements Worker {

        private final ApplicationContext ctx;

        private final ThreadPoolTaskScheduler scheduler;

        @Value("${cleanup.expired_login_state.cron}")
        private String expiredLoginStateCron;

        private Future<?> expiredLoginStateFuture;

        public CleanupWorker(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
            this.ctx = ctx;
            this.scheduler = scheduler;
        }

        @Override
        public void work() {
            ExpiredLoginStateCleanupTask task = ctx.getBean(ExpiredLoginStateCleanupTask.class);
            expiredLoginStateFuture = scheduler.schedule(task, new CronTrigger(expiredLoginStateCron));
        }

        @Override
        public void rest() {
            expiredLoginStateFuture.cancel(true);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ExpiredLoginStateCleanupTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredLoginStateCleanupTask.class);

        private final LoginStateMaintainService loginStateMaintainService;

        public ExpiredLoginStateCleanupTask(LoginStateMaintainService loginStateMaintainService) {
            this.loginStateMaintainService = loginStateMaintainService;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("清理登录状态...");

                Date currentDate = new Date();
                List<LongIdKey> expiredLoginStateKeys = loginStateMaintainService.lookupAsList(
                        LoginStateMaintainService.EXPIRE_DATE_BEFORE, new Date[]{currentDate}
                ).stream().map(LoginState::getKey).collect(Collectors.toList());
                loginStateMaintainService.batchDelete(expiredLoginStateKeys);
            } catch (Exception e) {
                LOGGER.warn("清理过期登录状态时发送异常, 清理未完成, 异常信息如下: ", e);
            }
        }
    }
}
