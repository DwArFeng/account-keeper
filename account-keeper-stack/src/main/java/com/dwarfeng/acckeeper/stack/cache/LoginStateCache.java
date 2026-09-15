package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.cache.BatchBaseCache;

/**
 * 登录状态缓存。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface LoginStateCache extends BatchBaseCache<StringIdKey, LoginState> {
}
