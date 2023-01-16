package com.dwarfeng.acckeeper.node.launcher;

import com.dwarfeng.acckeeper.node.handler.LauncherSettingHandler;
import com.dwarfeng.acckeeper.stack.service.CleanQosService;
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

            // 拿出程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
            ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

            // 处理清理处理器的启动选项。
            CleanQosService cleanQosService = ctx.getBean(CleanQosService.class);
            // 清理处理器是否启动清理服务。
            long startCleanupDelay = launcherSettingHandler.getStartCleanupDelay();
            if (startCleanupDelay == 0) {
                LOGGER.info("立即启动清理服务...");
                try {
                    cleanQosService.start();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动清理服务，异常原因如下", e);
                }
            } else if (startCleanupDelay > 0) {
                LOGGER.info(startCleanupDelay + " 毫秒后启动清理服务...");
                scheduler.schedule(
                        () -> {
                            LOGGER.info("启动清理服务...");
                            try {
                                cleanQosService.start();
                            } catch (ServiceException e) {
                                LOGGER.error("无法启动清理服务，异常原因如下", e);
                            }
                        },
                        new Date(System.currentTimeMillis() + startCleanupDelay)
                );
            }
        });
    }
}
