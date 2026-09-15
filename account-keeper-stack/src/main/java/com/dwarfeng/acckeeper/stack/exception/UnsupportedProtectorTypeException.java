package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessageKey;
import com.dwarfeng.acckeeper.stack.internal.i18n.StackMessages;

import java.io.Serial;

/**
 * 不支持的保护器类型异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class UnsupportedProtectorTypeException extends ProtectorException {

    @Serial
    private static final long serialVersionUID = 5153478811788730779L;

    private final String type;

    public UnsupportedProtectorTypeException(String type) {
        this.type = type;
    }

    public UnsupportedProtectorTypeException(Throwable cause, String type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return StackMessages.message(StackMessageKey.EXCEPTION_UNSUPPORTED_PROTECTOR_TYPE, type);
    }
}
