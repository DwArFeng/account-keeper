package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 保护详细信息记录缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectDetailRecordCache extends BatchBaseCache<RecordKey, ProtectDetailRecord> {
}
