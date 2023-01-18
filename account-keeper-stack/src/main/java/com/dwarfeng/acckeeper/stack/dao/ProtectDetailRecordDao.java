package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 保护详细信息记录数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectDetailRecordDao extends BatchBaseDao<RecordKey, ProtectDetailRecord>,
        EntireLookupDao<ProtectDetailRecord>, PresetLookupDao<ProtectDetailRecord> {
}
