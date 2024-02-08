package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 登录参数记录维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginParamRecordMaintainService extends BatchCrudService<RecordKey, LoginParamRecord>,
        EntireLookupService<LoginParamRecord>, PresetLookupService<LoginParamRecord> {

    String CHILD_FOR_LOGIN_HISTORY = "child_for_login_history";
    String CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC = "child_for_login_history_record_id_asc";
}
