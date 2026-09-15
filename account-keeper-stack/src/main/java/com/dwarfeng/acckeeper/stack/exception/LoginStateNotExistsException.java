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
 * @since 2.0.0
 */
public class LoginStateNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -3672772144305708632L;

    private final StringIdKey loginStateKey;

    public LoginStateNotExistsException(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public LoginStateNotExistsException(Throwable cause, StringIdKey loginStateKey) {
        super(cause);
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_LOGIN_STATE_NOT_EXISTS, loginStateKey);
    }
}
