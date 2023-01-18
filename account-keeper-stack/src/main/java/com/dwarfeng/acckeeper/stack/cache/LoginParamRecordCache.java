package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 登陆参数记录缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginParamRecordCache extends BatchBaseCache<RecordKey, LoginParamRecord> {
}
