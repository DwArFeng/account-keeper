package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;

import java.io.Serial;

/**
 * 保护器构造异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorMakeException extends ProtectorException {

    @Serial
    private static final long serialVersionUID = -7554425071831738683L;

    private final String protectorType;
    private final String param;

    public ProtectorMakeException(String protectorType, String param) {
        this.protectorType = protectorType;
        this.param = param;
    }

    public ProtectorMakeException(Throwable cause, String protectorType, String param) {
        super(cause);
        this.protectorType = protectorType;
        this.param = param;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_PROTECTOR_MAKE, protectorType, param);
    }
}
