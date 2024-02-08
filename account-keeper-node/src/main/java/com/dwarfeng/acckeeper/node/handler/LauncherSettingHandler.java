package com.dwarfeng.acckeeper.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_protector_support}")
    private boolean resetProtectorSupport;

    @Value("${launcher.start_clean_delay}")
    private long startCleanDelay;

    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetProtectorSupport() {
        return resetProtectorSupport;
    }

    public long getStartCleanDelay() {
        return startCleanDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }
}
