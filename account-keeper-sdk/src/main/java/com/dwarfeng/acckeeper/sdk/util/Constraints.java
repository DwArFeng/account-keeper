package com.dwarfeng.acckeeper.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class Constraints {

    /**
     * ID的长度约束。
     */
    public static final int LENGTH_ID = 100;

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

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
