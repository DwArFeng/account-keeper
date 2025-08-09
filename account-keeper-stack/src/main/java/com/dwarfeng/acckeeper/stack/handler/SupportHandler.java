package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置保护器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetProtector() throws HandlerException;
}
