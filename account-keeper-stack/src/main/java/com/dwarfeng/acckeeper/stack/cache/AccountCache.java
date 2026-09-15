package com.dwarfeng.acckeeper.stack.cache;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.cache.BatchBaseCache;

/**
 * 账户缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface AccountCache extends BatchBaseCache<StringIdKey, Account> {
}
