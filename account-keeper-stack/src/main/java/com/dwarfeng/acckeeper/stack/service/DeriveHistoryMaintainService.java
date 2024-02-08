package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 派生历史维护服务。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface DeriveHistoryMaintainService extends BatchCrudService<LongIdKey, DeriveHistory>,
        EntireLookupService<DeriveHistory>, PresetLookupService<DeriveHistory> {

    String ACCOUNT_ID_EQUALS = "account_id_equals";
    String ACCOUNT_ID_LIKE = "account_id_like";
    String HAPPENED_DATE_DESC = "happened_date_desc";
    String ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC = "account_id_equals_happened_date_desc";
    String ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC = "account_id_like_happened_date_desc";
}
