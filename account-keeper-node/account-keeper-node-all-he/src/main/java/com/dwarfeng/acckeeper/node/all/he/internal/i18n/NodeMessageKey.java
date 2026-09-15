package com.dwarfeng.acckeeper.node.all.he.internal.i18n;

import static com.dwarfeng.acckeeper.node.all.he.internal.i18n.NodeMessages.Catalog.NODE;

/**
 * Node All HE 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum NodeMessageKey {

    LOG_LAUNCHER_ONLINE_CLEAN_FAILED(NODE, "log.launcher_online_clean_failed"),
    LOG_LAUNCHER_ONLINE_PURGE_FAILED(NODE, "log.launcher_online_purge_failed"),
    LOG_LAUNCHER_ONLINING_CLEAN(NODE, "log.launcher_onlining_clean"),
    LOG_LAUNCHER_ONLINING_CLEAN_DELAYED(NODE, "log.launcher_onlining_clean_delayed"),
    LOG_LAUNCHER_ONLINING_CLEAN_IMMEDIATELY(NODE, "log.launcher_onlining_clean_immediately"),
    LOG_LAUNCHER_ONLINING_PURGE(NODE, "log.launcher_onlining_purge"),
    LOG_LAUNCHER_ONLINING_PURGE_DELAYED(NODE, "log.launcher_onlining_purge_delayed"),
    LOG_LAUNCHER_ONLINING_PURGE_IMMEDIATELY(NODE, "log.launcher_onlining_purge_immediately"),
    LOG_LAUNCHER_RESET_PROTECTOR_SUPPORT_FAILED(NODE, "log.launcher_reset_protector_support_failed"),
    LOG_LAUNCHER_RESETTING_PROTECTOR_SUPPORT(NODE, "log.launcher_resetting_protector_support"),
    LOG_LAUNCHER_START_CLEAN_FAILED(NODE, "log.launcher_start_clean_failed"),
    LOG_LAUNCHER_START_PURGE_FAILED(NODE, "log.launcher_start_purge_failed"),
    LOG_LAUNCHER_START_RESET_FAILED(NODE, "log.launcher_start_reset_failed"),
    LOG_LAUNCHER_STARTING_CLEAN(NODE, "log.launcher_starting_clean"),
    LOG_LAUNCHER_STARTING_CLEAN_DELAYED(NODE, "log.launcher_starting_clean_delayed"),
    LOG_LAUNCHER_STARTING_CLEAN_IMMEDIATELY(NODE, "log.launcher_starting_clean_immediately"),
    LOG_LAUNCHER_STARTING_PURGE(NODE, "log.launcher_starting_purge"),
    LOG_LAUNCHER_STARTING_PURGE_DELAYED(NODE, "log.launcher_starting_purge_delayed"),
    LOG_LAUNCHER_STARTING_PURGE_IMMEDIATELY(NODE, "log.launcher_starting_purge_immediately"),
    LOG_LAUNCHER_STARTING_RESET(NODE, "log.launcher_starting_reset"),
    LOG_LAUNCHER_STARTING_RESET_DELAYED(NODE, "log.launcher_starting_reset_delayed"),
    LOG_LAUNCHER_STARTING_RESET_IMMEDIATELY(NODE, "log.launcher_starting_reset_immediately");

    private final NodeMessages.Catalog catalog;
    private final String key;

    NodeMessageKey(NodeMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    NodeMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 获取消息键。
     *
     * @return 消息键。
     */
    public String key() {
        return key;
    }
}
