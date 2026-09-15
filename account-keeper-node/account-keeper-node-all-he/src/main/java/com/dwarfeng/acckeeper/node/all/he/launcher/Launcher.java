package com.dwarfeng.acckeeper.node.all.he.launcher;

import com.dwarfeng.acckeeper.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.acckeeper.node.all.he.internal.i18n.NodeMessageKey;
import com.dwarfeng.acckeeper.node.all.he.internal.i18n.NodeMessages;
import com.dwarfeng.acckeeper.stack.service.CleanQosService;
import com.dwarfeng.acckeeper.stack.service.PurgeQosService;
import com.dwarfeng.acckeeper.stack.service.ResetQosService;
import com.dwarfeng.acckeeper.stack.service.SupportQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    static void main() {
        ApplicationUtil.launch(new String[]{
                "classpath:com/dwarfeng/acckeeper/node/all/he/spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置保护器。
            mayResetProtector(ctx);

            // 根据启动器设置处理器的设置，选择性上线清理服务。
            mayOnlineClean(ctx);
            // 根据启动器设置处理器的设置，选择性启动清理服务。
            mayEnableClean(ctx);

            // 根据启动器设置处理器的设置，选择性启动重置服务。
            mayStartReset(ctx);

            // 根据启动器设置处理器的设置，选择性上线清除服务。
            mayOnlinePurge(ctx);
            // 根据启动器设置处理器的设置，选择性启动清除服务。
            mayEnablePurge(ctx);
        });
    }

    private static void mayResetProtector(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 如果不重置保护器，则返回。
        if (!launcherSettingHandler.isResetProtectorSupport()) {
            return;
        }

        // 重置保护器支持。
        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_RESETTING_PROTECTOR_SUPPORT));
        SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
        try {
            supportQosService.resetProtector();
        } catch (ServiceException e) {
            LOGGER.warn(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_RESET_PROTECTOR_SUPPORT_FAILED), e);
        }
    }

    private static void mayOnlineClean(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理清理处理器的启动选项。
        CleanQosService cleanQosService = ctx.getBean(CleanQosService.class);

        // 清理处理器是否上线清理服务。
        long onlineCleanDelay = launcherSettingHandler.getOnlineCleanDelay();
        if (onlineCleanDelay == 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_CLEAN_IMMEDIATELY));
            try {
                cleanQosService.online();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINE_CLEAN_FAILED), e);
            }
        } else if (onlineCleanDelay > 0) {
            LOGGER.info(
                    NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_CLEAN_DELAYED, onlineCleanDelay)
            );
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_CLEAN));
                        try {
                            cleanQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINE_CLEAN_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(onlineCleanDelay)
            );
        }
    }

    private static void mayEnableClean(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理清理处理器的启动选项。
        CleanQosService cleanQosService = ctx.getBean(CleanQosService.class);

        // 清理处理器是否启动清理服务。
        long enableCleanDelay = launcherSettingHandler.getEnableCleanDelay();
        if (enableCleanDelay == 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_CLEAN_IMMEDIATELY));
            try {
                cleanQosService.start();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_CLEAN_FAILED), e);
            }
        } else if (enableCleanDelay > 0) {
            LOGGER.info(
                    NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_CLEAN_DELAYED, enableCleanDelay)
            );
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_CLEAN));
                        try {
                            cleanQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_CLEAN_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(enableCleanDelay)
            );
        }
    }

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理重置处理器的启动选项。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 重置处理器是否启动重置服务。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET_IMMEDIATELY));
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_RESET_FAILED), e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info(
                    NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET_DELAYED, startResetDelay)
            );
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_RESET));
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_RESET_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(startResetDelay)
            );
        }
    }

    private static void mayOnlinePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理清除处理器的启动选项。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 清除处理器是否上线清除服务。
        long onlinePurgeDelay = launcherSettingHandler.getOnlinePurgeDelay();
        if (onlinePurgeDelay == 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_PURGE_IMMEDIATELY));
            try {
                purgeQosService.online();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINE_PURGE_FAILED), e);
            }
        } else if (onlinePurgeDelay > 0) {
            LOGGER.info(
                    NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_PURGE_DELAYED, onlinePurgeDelay)
            );
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINING_PURGE));
                        try {
                            purgeQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_ONLINE_PURGE_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(onlinePurgeDelay)
            );
        }
    }

    private static void mayEnablePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 处理清除处理器的启动选项。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 清除处理器是否启动清除服务。
        long enablePurgeDelay = launcherSettingHandler.getEnablePurgeDelay();
        if (enablePurgeDelay == 0) {
            LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_PURGE_IMMEDIATELY));
            try {
                purgeQosService.start();
            } catch (ServiceException e) {
                LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_PURGE_FAILED), e);
            }
        } else if (enablePurgeDelay > 0) {
            LOGGER.info(
                    NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_PURGE_DELAYED, enablePurgeDelay)
            );
            scheduler.schedule(
                    () -> {
                        LOGGER.info(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_STARTING_PURGE));
                        try {
                            purgeQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error(NodeMessages.message(NodeMessageKey.LOG_LAUNCHER_START_PURGE_FAILED), e);
                        }
                    },
                    Instant.now().plusMillis(enablePurgeDelay)
            );
        }
    }
}
