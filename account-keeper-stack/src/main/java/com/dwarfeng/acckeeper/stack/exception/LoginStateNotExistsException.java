package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 登录状态不存在异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginStateNotExistsException extends HandlerException {

    private static final long serialVersionUID = 2664525030558970797L;

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
        return "登录状态 " + loginStateKey + " 不存在";
    }
}
