package com.dwarfeng.acckeeper.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class Constraints {

    /**
     * 通用 ID 的长度约束。
     *
     * @since 2.0.0
     */
    public static final int LENGTH_ID_COMMON = 100;

    /**
     * 登录状态 ID 的长度约束。
     *
     * @since 2.0.0
     */
    public static final int LENGTH_ID_LOGIN_STATE = 128;

    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 100;

    /**
     * 密码的长度（此处指加密过的密码的长度）约束。
     */
    public static final int LENGTH_PASSWORD = 60;

    /**
     * 标签的长度约束。
     */
    public static final int LENGTH_LABEL = 20;

    /**
     * 类型的长度约束。
     */
    public static final int LENGTH_TYPE = 50;

    /**
     * 消息的长度约束。
     */
    public static final int LENGTH_MESSAGE = 100;

    /**
     * ID 的长度约束。
     *
     * <p>
     * 该常量已废弃，请使用 {@link #LENGTH_ID_COMMON} 代替。
     *
     * @see #LENGTH_ID_COMMON
     * @deprecated 该常量已废弃，请使用 {@link #LENGTH_ID_COMMON} 代替。
     */
    @Deprecated
    public static final int LENGTH_ID = LENGTH_ID_COMMON;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
