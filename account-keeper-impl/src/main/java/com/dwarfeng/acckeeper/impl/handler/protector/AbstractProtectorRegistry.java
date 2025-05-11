package com.dwarfeng.acckeeper.impl.handler.protector;

/**
 * 抽象保护器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.acckeeper.sdk.handler.protector.AbstractProtectorRegistry
 * @since 1.6.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractProtectorRegistry extends
        com.dwarfeng.acckeeper.sdk.handler.protector.AbstractProtectorRegistry {

    public AbstractProtectorRegistry() {
    }

    public AbstractProtectorRegistry(String protectorType) {
        super(protectorType);
    }
}
