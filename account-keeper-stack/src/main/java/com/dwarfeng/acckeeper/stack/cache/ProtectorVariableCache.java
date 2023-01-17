package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 保护器变量信息缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorVariableCache extends BatchBaseCache<ProtectorVariableKey, ProtectorVariable> {
}
