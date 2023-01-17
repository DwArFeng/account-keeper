package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 保护器信息缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorInfoCache extends BatchBaseCache<StringIdKey, ProtectorInfo> {
}
