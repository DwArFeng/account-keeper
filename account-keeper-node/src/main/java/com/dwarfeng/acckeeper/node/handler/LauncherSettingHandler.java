package com.dwarfeng.acckeeper.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.start_cleanup_delay}")
    private long startCleanupDelay;

    public long getStartCleanupDelay() {
        return startCleanupDelay;
    }
}
