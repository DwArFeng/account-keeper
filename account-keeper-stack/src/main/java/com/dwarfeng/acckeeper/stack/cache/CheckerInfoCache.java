package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 检查器信息缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface CheckerInfoCache extends BatchBaseCache<StringIdKey, CheckerInfo> {
}
