package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.data.stack.cache.BatchBaseCache;

/**
 * 派生历史缓存。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface DeriveHistoryCache extends BatchBaseCache<LongIdKey, DeriveHistory> {
}
