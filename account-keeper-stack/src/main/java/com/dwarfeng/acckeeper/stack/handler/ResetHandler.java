package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置保护。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetProtect() throws HandlerException;
}
