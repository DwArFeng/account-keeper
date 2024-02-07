package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 登陆状态维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginStateMaintainService extends BatchCrudService<LongIdKey, LoginState>,
        EntireLookupService<LoginState>, PresetLookupService<LoginState> {

    String CHILD_FOR_ACCOUNT = "child_for_account";
    String EXPIRE_DATE_BEFORE = "expire_date_before";

    /**
     * 查询没有账户的登陆状态。
     *
     * <p>
     * 清理作业使用。
     *
     * @since 1.7.0
     */
    String WITHOUT_ACCOUNT = "without_account";

    /**
     * 查询过期时间早于当前时间的登陆状态。
     *
     * <p>
     * 清理作业使用。
     *
     * @since 1.7.0
     */
    String EXPIRE_DATE_BEFORE_NOW = "expire_date_before_now";

    /**
     * 查询序列版本小于对应账户序列版本的登录状态。
     *
     * <p>
     * 清理作业使用。
     *
     * @since 1.7.0
     */
    String SERIAL_VERSION_LOWER_THAN_ACCOUNT = "serial_version_lower_than_account";

    /**
     * @since 1.7.0
     */
    String GENERATED_DATE_DESC = "generated_date_desc";

    /**
     * @since 1.7.0
     */
    String CHILD_FOR_ACCOUNT_GENERATED_DATE_DESC = "child_for_account_generated_date_desc";

    /**
     * @since 1.7.0
     */
    String CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC = "child_for_account_type_equals_dynamic";

    /**
     * @since 1.7.0
     */
    String CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC_GENERATED_DATE_DESC =
            "child_for_account_type_equals_dynamic_generated_date_desc";

    /**
     * @since 1.7.0
     */
    String CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC = "child_for_account_type_equals_static";

    /**
     * @since 1.7.0
     */
    String CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC_GENERATED_DATE_DESC =
            "child_for_account_type_equals_static_generated_date_desc";

    /**
     * @since 1.7.0
     */
    String ID_EQUALS = "id_equals";
}
