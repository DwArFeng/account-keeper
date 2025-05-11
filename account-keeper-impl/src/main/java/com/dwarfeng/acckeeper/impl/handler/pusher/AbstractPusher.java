package com.dwarfeng.acckeeper.impl.handler.pusher;

/**
 * 抽象推送器。
 *
 * <p>
 * 推送器的抽象实现。
 *
 * @author DwArFeng
 * @see com.dwarfeng.acckeeper.sdk.handler.pusher.AbstractPusher
 * @since 1.6.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractPusher extends com.dwarfeng.acckeeper.sdk.handler.pusher.AbstractPusher {

    public AbstractPusher() {
    }

    public AbstractPusher(String sinkType) {
        super(sinkType);
    }
}
