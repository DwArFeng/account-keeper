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

    String ACCOUNT_ID_EQUALS = "account_id_equals";
    String ACCOUNT_ID_LIKE = "account_id_like";
    String HAPPENED_DATE_DESC = "happened_date_desc";
    String ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC = "account_id_equals_happened_date_desc";
    String ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC = "account_id_like_happened_date_desc";
}
