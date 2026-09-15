package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;

import java.io.Serial;

/**
 * 账户不存在异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class AccountNotExistsException extends HandlerException {

    @Serial
    private static final long serialVersionUID = -2617630388423642740L;

    private final StringIdKey accountKey;

    public AccountNotExistsException(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public AccountNotExistsException(Throwable cause, StringIdKey accountKey) {
        super(cause);
        this.accountKey = accountKey;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_ACCOUNT_NOT_EXISTS, accountKey);
    }
}
