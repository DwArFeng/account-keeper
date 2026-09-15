package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 保护器禁止登录异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorProhibitedException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 7906939195014433732L;

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
        return StackMessages.message(StackMessageKey.EXCEPTION_PROTECTOR_PROHIBITED, protectorInfoKey);
    }
}
