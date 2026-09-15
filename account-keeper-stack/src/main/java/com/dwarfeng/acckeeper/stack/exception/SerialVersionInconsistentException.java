package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 序列版本不一致异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class SerialVersionInconsistentException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 2977626486967645233L;

    private final StringIdKey loginStateKey;

    public SerialVersionInconsistentException(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public SerialVersionInconsistentException(Throwable cause, StringIdKey loginStateKey) {
        super(cause);
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_SERIAL_VERSION_INCONSISTENT, loginStateKey);
    }
}
