package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 登录历史维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginHistoryMaintainService extends BatchCrudService<LongIdKey, LoginHistory>,
        EntireLookupService<LoginHistory>, PresetLookupService<LoginHistory> {

    String CHILD_FOR_ACCOUNT = "child_for_account";
    String HAPPENED_DATE_DESC = "happened_date_desc";
    String CHILD_FOR_ACCOUNT_HAPPENED_DATE_DESC = "child_for_account_happened_date_desc";
}
