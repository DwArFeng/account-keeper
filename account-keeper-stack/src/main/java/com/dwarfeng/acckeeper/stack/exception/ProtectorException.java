package com.dwarfeng.acckeeper.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 保护器异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorException extends HandlerException {

    private static final long serialVersionUID = -2139225837251534021L;

    public ProtectorException() {
    }

    public ProtectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtectorException(String message) {
        super(message);
    }

    public ProtectorException(Throwable cause) {
        super(cause);
    }
}
