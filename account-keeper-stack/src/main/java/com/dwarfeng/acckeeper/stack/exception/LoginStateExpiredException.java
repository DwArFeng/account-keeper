package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 登录状态不存在异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class LoginStateExpiredException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 4983604166075825281L;

    private final StringIdKey loginStateKey;

    public LoginStateExpiredException(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public LoginStateExpiredException(Throwable cause, StringIdKey loginStateKey) {
        super(cause);
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_LOGIN_STATE_EXPIRED, loginStateKey);
    }
}
