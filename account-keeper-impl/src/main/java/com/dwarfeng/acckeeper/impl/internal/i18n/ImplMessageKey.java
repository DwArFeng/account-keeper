package com.dwarfeng.acckeeper.impl.internal.i18n;

import static com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages.Catalog.IMPL;

/**
 * Impl 模块消息键。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public enum ImplMessageKey {

    LOG_ACCOUNT_DELETING(IMPL, "log.account_deleting"),
    LOG_ACCOUNT_PASSWORD_CHECK_FAILED(IMPL, "log.account_password_check_failed"),
    LOG_ACCOUNT_PASSWORD_RESET_REQUESTED(IMPL, "log.account_password_reset_requested"),
    LOG_ACCOUNT_PASSWORD_UPDATE_REQUESTED(IMPL, "log.account_password_update_requested"),
    LOG_ACCOUNT_REGISTERING(IMPL, "log.account_registering"),
    LOG_CLEAN_EXPIRED_LOGIN_STATE(IMPL, "log.clean_expired_login_state"),
    LOG_CLEAN_FAILED(IMPL, "log.clean_failed"),
    LOG_CLEAN_FINISHED(IMPL, "log.clean_finished"),
    LOG_CLEAN_LOGIN_STATE_WITHOUT_ACCOUNT(IMPL, "log.clean_login_state_without_account"),
    LOG_CLEAN_STALE_LOGIN_STATE(IMPL, "log.clean_stale_login_state"),
    LOG_CLEAN_STARTING(IMPL, "log.clean_starting"),
    LOG_COMMAND_OPTION_PARSE_FAILED(IMPL, "log.command_option_parse_failed"),
    LOG_DERIVE_HISTORY_RECORDED_PUSH_FAILED(IMPL, "log.derive_history_recorded_push_failed"),
    LOG_DUBBO_RESETTER_STARTING(IMPL, "log.dubbo_resetter_starting"),
    LOG_DUBBO_RESETTER_STOPPING(IMPL, "log.dubbo_resetter_stopping"),
    LOG_LOGIN_HISTORY_RECORDED_PUSH_FAILED(IMPL, "log.login_history_recorded_push_failed"),
    LOG_LOGIN_STATE_KEY_GENERATE_COLLISION(IMPL, "log.login_state_key_generate_collision"),
    LOG_LOGIN_STATE_KEY_GENERATE_EXHAUSTED(IMPL, "log.login_state_key_generate_exhausted"),
    LOG_LOGIN_STATE_KEY_GENERATE_RETRIED(IMPL, "log.login_state_key_generate_retried"),
    LOG_LOGIN_STATE_STATIC_TYPE_SKIPPED(IMPL, "log.login_state_static_type_skipped"),
    LOG_PROTECT_RESET_MESSAGE_PUSH_FAILED(IMPL, "log.protect_reset_message_push_failed"),
    LOG_PROTECT_RESET_MESSAGE_RECEIVED(IMPL, "log.protect_reset_message_received"),
    LOG_PROTECTOR_BUILD_SUCCEEDED(IMPL, "log.protector_build_succeeded"),
    LOG_PROTECTOR_BUILDING(IMPL, "log.protector_building"),
    LOG_PROTECTOR_DETAIL(IMPL, "log.protector_detail"),
    LOG_PROXY_PUSHER_PUSH_FAILED(IMPL, "log.proxy_pusher_push_failed"),
    LOG_PURGE_DERIVE_HISTORY_DIVERGENT(IMPL, "log.purge_derive_history_divergent"),
    LOG_PURGE_DERIVE_HISTORY_DIVERGENT_HINT(IMPL, "log.purge_derive_history_divergent_hint"),
    LOG_PURGE_DERIVE_HISTORY_FINISHED(IMPL, "log.purge_derive_history_finished"),
    LOG_PURGE_DERIVE_HISTORY_STARTING(IMPL, "log.purge_derive_history_starting"),
    LOG_PURGE_FAILED_PUSH_FAILED(IMPL, "log.purge_failed_push_failed"),
    LOG_PURGE_FINISHED_PUSH_FAILED(IMPL, "log.purge_finished_push_failed"),
    LOG_PURGE_LOGIN_HISTORY_DIVERGENT(IMPL, "log.purge_login_history_divergent"),
    LOG_PURGE_LOGIN_HISTORY_DIVERGENT_HINT(IMPL, "log.purge_login_history_divergent_hint"),
    LOG_PURGE_LOGIN_HISTORY_FINISHED(IMPL, "log.purge_login_history_finished"),
    LOG_PURGE_LOGIN_HISTORY_STARTING(IMPL, "log.purge_login_history_starting"),
    LOG_PURGE_PLAN_NOT_STARTED(IMPL, "log.purge_plan_not_started"),
    LOG_PURGE_PLAN_STARTED(IMPL, "log.purge_plan_started"),
    LOG_PURGE_RETENTION_DATE(IMPL, "log.purge_retention_date"),
    LOG_PURGE_RETENTION_DATE_CALCULATING(IMPL, "log.purge_retention_date_calculating"),
    LOG_PURGE_RETENTION_DURATION(IMPL, "log.purge_retention_duration"),
    LOG_PURGE_TASK_FAILED(IMPL, "log.purge_task_failed"),
    LOG_PURGE_TASK_FINISHED(IMPL, "log.purge_task_finished"),
    LOG_PURGE_TASK_SKIPPED_INTERNAL(IMPL, "log.purge_task_skipped_internal"),
    LOG_PURGE_TASK_STARTING(IMPL, "log.purge_task_starting"),
    LOG_PURGE_WORKER_RESTING(IMPL, "log.purge_worker_resting"),
    LOG_PURGE_WORKER_WORKING(IMPL, "log.purge_worker_working"),
    LOG_RESET_SCHEDULE_TRIGGERED(IMPL, "log.reset_schedule_triggered"),
    LOG_RESETTER_EXECUTE_FAILED(IMPL, "log.resetter_execute_failed"),
    LOG_RESETTER_HANDLER_INITIALIZING(IMPL, "log.resetter_handler_initializing"),
    LOG_RESETTER_START_FAILED(IMPL, "log.resetter_start_failed"),
    LOG_RESETTER_STOP_FAILED(IMPL, "log.resetter_stop_failed"),
    LOG_RESETTERS_STARTING(IMPL, "log.resetters_starting"),
    LOG_RESETTERS_STOPPING(IMPL, "log.resetters_stopping"),
    SPI_PROTECTOR_DO_NOTHING_PROTECTOR_DESCRIPTION(IMPL, "spi.protector.do_nothing_protector.description"),
    SPI_PROTECTOR_DO_NOTHING_PROTECTOR_LABEL(IMPL, "spi.protector.do_nothing_protector.label"),
    SPI_PROTECTOR_PASSWORD_INCORRECT_PROTECTOR_DESCRIPTION(
            IMPL, "spi.protector.password_incorrect_protector.description"
    ),
    SPI_PROTECTOR_PASSWORD_INCORRECT_PROTECTOR_LABEL(IMPL, "spi.protector.password_incorrect_protector.label"),
    TELQOS_ACCESS_DESCRIPTION(IMPL, "telqos.access.description"),
    TELQOS_ACCESS_FIELD_IS_LOGIN(IMPL, "telqos.access.field.is_login"),
    TELQOS_ACCESS_FIELD_ITEM_ACCOUNT_KEY(IMPL, "telqos.access.field.item_account_key"),
    TELQOS_ACCESS_FIELD_ITEM_EXPIRE_DATE(IMPL, "telqos.access.field.item_expire_date"),
    TELQOS_ACCESS_FIELD_ITEM_GENERATED_DATE(IMPL, "telqos.access.field.item_generated_date"),
    TELQOS_ACCESS_FIELD_ITEM_LOGIN_STATE_KEY(IMPL, "telqos.access.field.item_login_state_key"),
    TELQOS_ACCESS_FIELD_ITEM_REMARK(IMPL, "telqos.access.field.item_remark"),
    TELQOS_ACCESS_FIELD_ITEM_SERIAL_VERSION(IMPL, "telqos.access.field.item_serial_version"),
    TELQOS_ACCESS_FIELD_ITEM_TYPE(IMPL, "telqos.access.field.item_type"),
    TELQOS_ACCESS_FIELD_LOGIN_STATE(IMPL, "telqos.access.field.login_state"),
    TELQOS_ACCESS_OPTION_AUTH_INSPECT(IMPL, "telqos.access.option.auth_inspect"),
    TELQOS_ACCESS_OPTION_DYNAMIC_LOGIN(IMPL, "telqos.access.option.dynamic_login"),
    TELQOS_ACCESS_OPTION_KICK(IMPL, "telqos.access.option.kick"),
    TELQOS_ACCESS_OPTION_LOGOUT(IMPL, "telqos.access.option.logout"),
    TELQOS_ACCESS_OPTION_POSTPONE(IMPL, "telqos.access.option.postpone"),
    TELQOS_ACCESS_OPTION_STATIC_LOGIN(IMPL, "telqos.access.option.static_login"),
    TELQOS_ACCESS_OPTION_TRUSTED_DYNAMIC_LOGIN(IMPL, "telqos.access.option.trusted_dynamic_login"),
    TELQOS_ACCESS_OPTION_TRUSTED_STATIC_LOGIN(IMPL, "telqos.access.option.trusted_static_login"),
    TELQOS_ACCESS_RESULT_AUTH_INSPECT_RESULT_HEADER(IMPL, "telqos.access.result.auth_inspect_result_header"),
    TELQOS_ACCESS_RESULT_DYNAMIC_LOGIN_RESULT_HEADER(IMPL, "telqos.access.result.dynamic_login_result_header"),
    TELQOS_ACCESS_RESULT_KICK_SUCCEEDED(IMPL, "telqos.access.result.kick_succeeded"),
    TELQOS_ACCESS_RESULT_LOGIN_STATE_NULL(IMPL, "telqos.access.result.login_state_null"),
    TELQOS_ACCESS_RESULT_LOGOUT_SUCCEEDED(IMPL, "telqos.access.result.logout_succeeded"),
    TELQOS_ACCESS_RESULT_POSTPONE_RESULT_HEADER(IMPL, "telqos.access.result.postpone_result_header"),
    TELQOS_ACCESS_RESULT_STATIC_LOGIN_RESULT_HEADER(IMPL, "telqos.access.result.static_login_result_header"),
    TELQOS_ACCESS_RESULT_TRUSTED_DYNAMIC_LOGIN_RESULT_HEADER(
            IMPL, "telqos.access.result.trusted_dynamic_login_result_header"
    ),
    TELQOS_ACCESS_RESULT_TRUSTED_STATIC_LOGIN_RESULT_HEADER(
            IMPL, "telqos.access.result.trusted_static_login_result_header"
    ),
    TELQOS_ACCOUNT_DESCRIPTION(IMPL, "telqos.account.description"),
    TELQOS_ACCOUNT_OPTION_CHECK_PASSWORD(IMPL, "telqos.account.option.check_password"),
    TELQOS_ACCOUNT_OPTION_DELETE(IMPL, "telqos.account.option.delete"),
    TELQOS_ACCOUNT_OPTION_INVALID(IMPL, "telqos.account.option.invalid"),
    TELQOS_ACCOUNT_OPTION_REGISTER(IMPL, "telqos.account.option.register"),
    TELQOS_ACCOUNT_OPTION_RESET_PASSWORD(IMPL, "telqos.account.option.reset_password"),
    TELQOS_ACCOUNT_OPTION_UPDATE(IMPL, "telqos.account.option.update"),
    TELQOS_ACCOUNT_OPTION_UPDATE_PASSWORD(IMPL, "telqos.account.option.update_password"),
    TELQOS_ACCOUNT_RESULT_DELETE_SUCCEEDED(IMPL, "telqos.account.result.delete_succeeded"),
    TELQOS_ACCOUNT_RESULT_INVALID_SUCCEEDED(IMPL, "telqos.account.result.invalid_succeeded"),
    TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_FAILED(IMPL, "telqos.account.result.password_check_failed"),
    TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_PASSED(IMPL, "telqos.account.result.password_check_passed"),
    TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_RESULT(IMPL, "telqos.account.result.password_check_result"),
    TELQOS_ACCOUNT_RESULT_PASSWORD_RESET_SUCCEEDED(IMPL, "telqos.account.result.password_reset_succeeded"),
    TELQOS_ACCOUNT_RESULT_PASSWORD_UPDATE_SUCCEEDED(IMPL, "telqos.account.result.password_update_succeeded"),
    TELQOS_ACCOUNT_RESULT_REGISTER_SUCCEEDED(IMPL, "telqos.account.result.register_succeeded"),
    TELQOS_ACCOUNT_RESULT_UPDATE_SUCCEEDED(IMPL, "telqos.account.result.update_succeeded"),
    TELQOS_CLEAN_DESCRIPTION(IMPL, "telqos.clean.description"),
    TELQOS_CLEAN_FIELD_STATUS(IMPL, "telqos.clean.field.status"),
    TELQOS_CLEAN_OPTION_OFFLINE(IMPL, "telqos.clean.option.offline"),
    TELQOS_CLEAN_OPTION_ONLINE(IMPL, "telqos.clean.option.online"),
    TELQOS_CLEAN_OPTION_START(IMPL, "telqos.clean.option.start"),
    TELQOS_CLEAN_OPTION_STATUS(IMPL, "telqos.clean.option.status"),
    TELQOS_CLEAN_OPTION_STOP(IMPL, "telqos.clean.option.stop"),
    TELQOS_CLEAN_RESULT_OFFLINE(IMPL, "telqos.clean.result.offline"),
    TELQOS_CLEAN_RESULT_ONLINE(IMPL, "telqos.clean.result.online"),
    TELQOS_CLEAN_RESULT_STARTED(IMPL, "telqos.clean.result.started"),
    TELQOS_CLEAN_RESULT_STOPPED(IMPL, "telqos.clean.result.stopped"),
    TELQOS_COMMON_FIELD_ACCOUNT_KEY(IMPL, "telqos.common.field.account_key"),
    TELQOS_COMMON_FIELD_EXPIRE_DATE(IMPL, "telqos.common.field.expire_date"),
    TELQOS_COMMON_FIELD_GENERATED_DATE(IMPL, "telqos.common.field.generated_date"),
    TELQOS_COMMON_FIELD_LOGIN_STATE_KEY(IMPL, "telqos.common.field.login_state_key"),
    TELQOS_COMMON_FIELD_REMARK(IMPL, "telqos.common.field.remark"),
    TELQOS_COMMON_FIELD_SERIAL_VERSION(IMPL, "telqos.common.field.serial_version"),
    TELQOS_COMMON_FIELD_TYPE(IMPL, "telqos.common.field.type"),
    TELQOS_COMMON_OPTION_JSON_FILE(IMPL, "telqos.common.option.json_file"),
    TELQOS_COMMON_OPTION_JSON_STRING(IMPL, "telqos.common.option.json_string"),
    TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE(IMPL, "telqos.common.result.internal_unreachable"),
    TELQOS_COMMON_RESULT_NOT_EXISTS(IMPL, "telqos.common.result.not_exists"),
    TELQOS_COMMON_RESULT_UNSUPPORTED(IMPL, "telqos.common.result.unsupported"),
    TELQOS_DERIVE_DESCRIPTION(IMPL, "telqos.derive.description"),
    TELQOS_DERIVE_OPTION_DYNAMIC_DERIVE(IMPL, "telqos.derive.option.dynamic_derive"),
    TELQOS_DERIVE_OPTION_STATIC_DERIVE(IMPL, "telqos.derive.option.static_derive"),
    TELQOS_DERIVE_RESULT_DYNAMIC_DERIVE_RESULT_HEADER(IMPL, "telqos.derive.result.dynamic_derive_result_header"),
    TELQOS_DERIVE_RESULT_STATIC_DERIVE_RESULT_HEADER(IMPL, "telqos.derive.result.static_derive_result_header"),
    TELQOS_LOGIN_STATE_KEY_GENERATE_DESCRIPTION(IMPL, "telqos.login_state_key_generate.description"),
    TELQOS_LOGIN_STATE_KEY_GENERATE_OPTION_SIZE(IMPL, "telqos.login_state_key_generate.option.size"),
    TELQOS_LOGIN_STATE_KEY_GENERATE_OPTION_TEST(IMPL, "telqos.login_state_key_generate.option.test"),
    TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_FORMAT_ERROR(
            IMPL, "telqos.login_state_key_generate.result.size_format_error"
    ),
    TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_NOT_POSITIVE(
            IMPL, "telqos.login_state_key_generate.result.size_not_positive"
    ),
    TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_PROMPT(IMPL, "telqos.login_state_key_generate.result.size_prompt"),
    TELQOS_LOGIN_STATE_LOOKUP_DESCRIPTION(IMPL, "telqos.login_state_lookup.description"),
    TELQOS_LOGIN_STATE_LOOKUP_OPTION_LOOKUP(IMPL, "telqos.login_state_lookup.option.lookup"),
    TELQOS_LOGIN_STATE_LOOKUP_RESULT_DATA_TOTAL(IMPL, "telqos.login_state_lookup.result.data_total"),
    TELQOS_LOGIN_STATE_LOOKUP_RESULT_INDEX(IMPL, "telqos.login_state_lookup.result.index"),
    TELQOS_LOGIN_STATE_LOOKUP_RESULT_LOGIN_STATES_NULL(IMPL, "telqos.login_state_lookup.result.login_states_null"),
    TELQOS_LOGIN_STATE_LOOKUP_RESULT_QUERY_RESULT_HEADER(IMPL, "telqos.login_state_lookup.result.query_result_header"),
    TELQOS_LOGIN_STATE_LOOKUP_RESULT_QUERY_RESULT_NULL(IMPL, "telqos.login_state_lookup.result.query_result_null"),
    TELQOS_PROTECT_LOCAL_CACHE_DESCRIPTION(IMPL, "telqos.protect_local_cache.description"),
    TELQOS_PROTECT_LOCAL_CACHE_OPTION_CLEAR(IMPL, "telqos.protect_local_cache.option.clear"),
    TELQOS_PROTECT_LOCAL_CACHE_OPTION_LOOKUP(IMPL, "telqos.protect_local_cache.option.lookup"),
    TELQOS_PROTECT_LOCAL_CACHE_RESULT_ARGUMENT_TYPE_HINT(IMPL, "telqos.protect_local_cache.result.argument_type_hint"),
    TELQOS_PROTECT_LOCAL_CACHE_RESULT_CLEARED(IMPL, "telqos.protect_local_cache.result.cleared"),
    TELQOS_PROTECT_LOCAL_CACHE_RESULT_COMMAND_FORMAT_ERROR(
            IMPL, "telqos.protect_local_cache.result.command_format_error"
    ),
    TELQOS_PURGE_DESCRIPTION(IMPL, "telqos.purge.description"),
    TELQOS_PURGE_FIELD_STATUS(IMPL, "telqos.purge.field.status"),
    TELQOS_PURGE_OPTION_OFFLINE(IMPL, "telqos.purge.option.offline"),
    TELQOS_PURGE_OPTION_ONLINE(IMPL, "telqos.purge.option.online"),
    TELQOS_PURGE_OPTION_START(IMPL, "telqos.purge.option.start"),
    TELQOS_PURGE_OPTION_STATUS(IMPL, "telqos.purge.option.status"),
    TELQOS_PURGE_OPTION_STOP(IMPL, "telqos.purge.option.stop"),
    TELQOS_PURGE_RESULT_OFFLINE(IMPL, "telqos.purge.result.offline"),
    TELQOS_PURGE_RESULT_ONLINE(IMPL, "telqos.purge.result.online"),
    TELQOS_PURGE_RESULT_STARTED(IMPL, "telqos.purge.result.started"),
    TELQOS_PURGE_RESULT_STOPPED(IMPL, "telqos.purge.result.stopped"),
    TELQOS_RESET_DESCRIPTION(IMPL, "telqos.reset.description"),
    TELQOS_RESET_FIELD_STARTED(IMPL, "telqos.reset.field.started"),
    TELQOS_RESET_OPTION_INSPECT(IMPL, "telqos.reset.option.inspect"),
    TELQOS_RESET_OPTION_RESET(IMPL, "telqos.reset.option.reset"),
    TELQOS_RESET_OPTION_START(IMPL, "telqos.reset.option.start"),
    TELQOS_RESET_OPTION_STATUS(IMPL, "telqos.reset.option.status"),
    TELQOS_RESET_OPTION_STOP(IMPL, "telqos.reset.option.stop"),
    TELQOS_RESET_RESULT_STARTED(IMPL, "telqos.reset.result.started"),
    TELQOS_RESET_RESULT_STOPPED(IMPL, "telqos.reset.result.stopped"),
    TELQOS_RESET_RESULT_SUCCEEDED(IMPL, "telqos.reset.result.succeeded"),
    TELQOS_SUPPORT_DESCRIPTION(IMPL, "telqos.support.description"),
    TELQOS_SUPPORT_OPTION_RESET_PROTECTOR(IMPL, "telqos.support.option.reset_protector"),
    TELQOS_SUPPORT_RESULT_RESET_PROTECTOR_SUCCEEDED(IMPL, "telqos.support.result.reset_protector_succeeded");

    private final ImplMessages.Catalog catalog;
    private final String key;

    ImplMessageKey(ImplMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    ImplMessages.Catalog catalog() {
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
