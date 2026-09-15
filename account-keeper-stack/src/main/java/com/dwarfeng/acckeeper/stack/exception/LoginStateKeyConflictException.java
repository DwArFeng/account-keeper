package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 登录状态主键冲突异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginStateKeyConflictException extends HandlerException {

    @Serial
    private static final long serialVersionUID = 1403378562448949459L;

    public LoginStateKeyConflictException() {
    }

    public LoginStateKeyConflictException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_LOGIN_STATE_KEY_CONFLICT);
    }
}
