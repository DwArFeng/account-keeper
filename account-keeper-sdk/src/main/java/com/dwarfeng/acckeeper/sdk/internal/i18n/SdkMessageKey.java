package com.dwarfeng.acckeeper.sdk.internal.i18n;

import static com.dwarfeng.acckeeper.sdk.internal.i18n.SdkMessages.Catalog.SDK;

/**
 * SDK 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum SdkMessageKey {

    SERVICE_EXCEPTION_ACCOUNT_ALREADY_EXISTED(SDK, "service_exception.account_already_existed"),
    SERVICE_EXCEPTION_ACCOUNT_DISABLED(SDK, "service_exception.account_disabled"),
    SERVICE_EXCEPTION_ACCOUNT_NOT_EXISTS(SDK, "service_exception.account_not_exists"),
    SERVICE_EXCEPTION_LOGIN_STATE_EXPIRED(SDK, "service_exception.login_state_expired"),
    SERVICE_EXCEPTION_LOGIN_STATE_KEY_CONFLICT(SDK, "service_exception.login_state_key_conflict"),
    SERVICE_EXCEPTION_LOGIN_STATE_NOT_EXISTS(SDK, "service_exception.login_state_not_exists"),
    SERVICE_EXCEPTION_PASSWORD_INCORRECT(SDK, "service_exception.password_incorrect"),
    SERVICE_EXCEPTION_PROTECTOR_EXECUTION_FAILED(SDK, "service_exception.protector_execution_failed"),
    SERVICE_EXCEPTION_PROTECTOR_FAILED(SDK, "service_exception.protector_failed"),
    SERVICE_EXCEPTION_PROTECTOR_INFO_NOT_EXISTS(SDK, "service_exception.protector_info_not_exists"),
    SERVICE_EXCEPTION_PROTECTOR_MAKE_FAILED(SDK, "service_exception.protector_make_failed"),
    SERVICE_EXCEPTION_PROTECTOR_PROHIBITED(SDK, "service_exception.protector_prohibited"),
    SERVICE_EXCEPTION_SERIAL_VERSION_INCONSISTENT(SDK, "service_exception.serial_version_inconsistent"),
    SERVICE_EXCEPTION_UNSUPPORTED_PROTECTOR_TYPE(SDK, "service_exception.unsupported_protector_type");

    private final SdkMessages.Catalog catalog;
    private final String key;

    SdkMessageKey(SdkMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    SdkMessages.Catalog catalog() {
        return catalog;
    }

    public String key() {
        return key;
    }
}
