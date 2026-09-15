package com.dwarfeng.acckeeper.stack.internal.i18n;

import static com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages.Catalog.STACK;

/**
 * Stack 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum StackMessageKey {

    EXCEPTION_ACCOUNT_ALREADY_EXISTED(STACK, "exception.account_already_existed"),
    EXCEPTION_ACCOUNT_DISABLED(STACK, "exception.account_disabled"),
    EXCEPTION_ACCOUNT_NOT_EXISTS(STACK, "exception.account_not_exists"),
    EXCEPTION_LOGIN_STATE_EXPIRED(STACK, "exception.login_state_expired"),
    EXCEPTION_LOGIN_STATE_KEY_CONFLICT(STACK, "exception.login_state_key_conflict"),
    EXCEPTION_LOGIN_STATE_NOT_EXISTS(STACK, "exception.login_state_not_exists"),
    EXCEPTION_PASSWORD_INCORRECT(STACK, "exception.password_incorrect"),
    EXCEPTION_PROTECTOR_INFO_NOT_EXISTS(STACK, "exception.protector_info_not_exists"),
    EXCEPTION_PROTECTOR_MAKE(STACK, "exception.protector_make"),
    EXCEPTION_PROTECTOR_PROHIBITED(STACK, "exception.protector_prohibited"),
    EXCEPTION_SERIAL_VERSION_INCONSISTENT(STACK, "exception.serial_version_inconsistent"),
    EXCEPTION_UNSUPPORTED_PROTECTOR_TYPE(STACK, "exception.unsupported_protector_type");

    private final StackMessages.Catalog catalog;
    private final String key;

    StackMessageKey(StackMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    StackMessages.Catalog catalog() {
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
