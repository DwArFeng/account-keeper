package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 保护器信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = 9158313250080832368L;

    private final StringIdKey protectorInfoKey;

    public ProtectorInfoNotExistsException(StringIdKey protectorInfoKey) {
        this.protectorInfoKey = protectorInfoKey;
    }

    public ProtectorInfoNotExistsException(Throwable cause, StringIdKey protectorInfoKey) {
        super(cause);
        this.protectorInfoKey = protectorInfoKey;
    }

    @Override
    public String getMessage() {
        return "保护器信息 " + protectorInfoKey + " 不存在";
    }
}
