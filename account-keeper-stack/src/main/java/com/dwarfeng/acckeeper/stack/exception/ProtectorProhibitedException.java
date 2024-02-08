package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 保护器禁止登录异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorProhibitedException extends HandlerException {

    private static final long serialVersionUID = 3096458178415018573L;

    private final StringIdKey protectorInfoKey;

    public ProtectorProhibitedException(StringIdKey protectorInfoKey) {
        this.protectorInfoKey = protectorInfoKey;
    }

    public ProtectorProhibitedException(Throwable cause, StringIdKey protectorInfoKey) {
        super(cause);
        this.protectorInfoKey = protectorInfoKey;
    }

    @Override
    public String getMessage() {
        return "保护器禁止 " + protectorInfoKey + " 本次登录";
    }
}
