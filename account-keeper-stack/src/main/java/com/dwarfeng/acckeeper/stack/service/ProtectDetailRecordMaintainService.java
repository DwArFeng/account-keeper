package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 保护详细信息记录维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectDetailRecordMaintainService extends BatchCrudService<RecordKey, ProtectDetailRecord>,
        EntireLookupService<ProtectDetailRecord>, PresetLookupService<ProtectDetailRecord> {

    String CHILD_FOR_LOGIN_HISTORY = "child_for_login_history";
    String CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC = "child_for_login_history_record_id_asc";
}
