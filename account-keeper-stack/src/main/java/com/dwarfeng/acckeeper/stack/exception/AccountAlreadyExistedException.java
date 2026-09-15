package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 账户已经存在异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class AccountAlreadyExistedException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -6858822681947772805L;

    private final StringIdKey accountKey;

    public AccountAlreadyExistedException(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public AccountAlreadyExistedException(Throwable cause, StringIdKey accountKey) {
        super(cause);
        this.accountKey = accountKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_ACCOUNT_ALREADY_EXISTED, accountKey);
    }
}
