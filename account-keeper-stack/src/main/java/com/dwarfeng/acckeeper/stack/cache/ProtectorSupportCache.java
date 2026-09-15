package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.cache.BatchBaseCache;

/**
 * 保护器支持缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorSupportCache extends BatchBaseCache<StringIdKey, ProtectorSupport> {
}
