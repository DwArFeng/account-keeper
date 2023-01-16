package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 保护本地缓存处理器。
 *
 * <p>处理器在本地保存数据，缓存中的数据可以保证与数据源保持同步。
 * <p>数据存放在本地，必要时才与数据访问层通信，这有助于程序效率的提升。
 * <p>该处理器线程安全。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectLocalCacheHandler extends LocalCacheHandler<StringIdKey, Protector> {
}
