package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 登录历史缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginHistoryCache extends BatchBaseCache<LongIdKey, LoginHistory> {
}
