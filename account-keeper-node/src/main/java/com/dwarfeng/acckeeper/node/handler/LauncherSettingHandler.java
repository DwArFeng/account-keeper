package com.dwarfeng.acckeeper.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.acckeeper.launcher.reset_protector_support}")
    private boolean resetProtectorSupport;

    @Value("${com.dwarfeng.acckeeper.launcher.online_clean_delay}")
    private long onlineCleanDelay;
    @Value("${com.dwarfeng.acckeeper.launcher.enable_clean_delay}")
    private long enableCleanDelay;

    @Value("${com.dwarfeng.acckeeper.launcher.start_reset_delay}")
    private long startResetDelay;

    @Value("${com.dwarfeng.acckeeper.launcher.online_purge_delay}")
    private long onlinePurgeDelay;
    @Value("${com.dwarfeng.acckeeper.launcher.enable_purge_delay}")
    private long enablePurgeDelay;

    public boolean isResetProtectorSupport() {
        return resetProtectorSupport;
    }

    public long getOnlineCleanDelay() {
        return onlineCleanDelay;
    }

    public long getEnableCleanDelay() {
        return enableCleanDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    public long getOnlinePurgeDelay() {
        return onlinePurgeDelay;
    }

    public long getEnablePurgeDelay() {
        return enablePurgeDelay;
    }
}
