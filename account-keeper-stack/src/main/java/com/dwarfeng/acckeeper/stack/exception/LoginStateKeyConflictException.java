package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 登录状态主键冲突异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginStateKeyConflictException extends HandlerException {

    private static final long serialVersionUID = 1403378562448949459L;

    public LoginStateKeyConflictException() {
    }

    public LoginStateKeyConflictException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "登录状态主键冲突";
    }
}
