package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 保护器信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorInfoNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -5809409267859120191L;

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
        return StackMessages.message(StackMessageKey.EXCEPTION_PROTECTOR_INFO_NOT_EXISTS, protectorInfoKey);
    }
}
