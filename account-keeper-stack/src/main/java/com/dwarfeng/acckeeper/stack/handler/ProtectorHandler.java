package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.exception.ProtectorException;

/**
 * 保护器处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorHandler {

    /**
     * 根据指定的判断器信息构造一个判断器。
     *
     * @param type  保护器类型。
     * @param param 保护器参数。
     * @return 构造的判断器。
     * @throws ProtectorException 保护器异常。
     */
    Protector make(String type, String param) throws ProtectorException;
}
