package com.dwarfeng.acckeeper.node.launcher;

import com.dwarfeng.acckeeper.node.handler.LauncherSettingHandler;
import com.dwarfeng.acckeeper.stack.service.CleanQosService;
import com.dwarfeng.acckeeper.stack.service.ProtectorSupportMaintainService;
import com.dwarfeng.acckeeper.stack.service.ResetQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

            // 判断是否重置保护器支持。
            if (launcherSettingHandler.isResetProtectorSupport()) {
                LOGGER.info("重置保护器支持...");
                ProtectorSupportMaintainService maintainService = ctx.getBean(ProtectorSupportMaintainService.class);
                try {
                    maintainService.reset();
                } catch (ServiceException e) {
                    LOGGER.warn("保护器支持重置失败，异常信息如下", e);
                }
            }

            // 拿出程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
            ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

            // 处理清理处理器的启动选项。
            CleanQosService cleanQosService = ctx.getBean(CleanQosService.class);
            // 清理处理器是否上线清理服务。
            long onlineCleanDelay = launcherSettingHandler.getOnlineCleanDelay();
            if (onlineCleanDelay == 0) {
                LOGGER.info("立即上线清理服务...");
                try {
                    cleanQosService.online();
                } catch (ServiceException e) {
                    LOGGER.error("无法上线清理服务，异常原因如下", e);
                }
            } else if (onlineCleanDelay > 0) {
                LOGGER.info(onlineCleanDelay + " 毫秒后上线清理服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("上线清理服务...");
                            try {
                                cleanQosService.online();
                            } catch (ServiceException e) {
                                LOGGER.error("无法上线清理服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + onlineCleanDelay)
                );
            }
            // 清理处理器是否启动清理服务。
            long enableCleanDelay = launcherSettingHandler.getEnableCleanDelay();
            if (enableCleanDelay == 0) {
                LOGGER.info("立即启动清理服务...");
                try {
                    cleanQosService.online();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动清理服务，异常原因如下", e);
                }
            } else if (enableCleanDelay > 0) {
                LOGGER.info(enableCleanDelay + " 毫秒后启动清理服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动清理服务...");
                            try {
                                cleanQosService.start();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动清理服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + enableCleanDelay)
                );
            }

            // 处理重置处理器的启动选项。
            ResetQosService resetQosService = ctx.getBean(ResetQosService.class);
            // 重置处理器是否启动重置服务。
            long startResetDelay = launcherSettingHandler.getStartResetDelay();
            if (startResetDelay == 0) {
                LOGGER.info("立即启动重置服务...");
                try {
                    resetQosService.start();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动重置服务，异常原因如下", e);
                }
            } else if (startResetDelay > 0) {
                LOGGER.info(startResetDelay + " 毫秒后启动重置服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动重置服务...");
                            try {
                                resetQosService.start();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动重置服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + startResetDelay)
                );
            }
        });
    }
}
