package com.dwarfeng.acckeeper.stack.exception;

/**
 * 保护器执行异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorExecutionException extends ProtectorException {

    private static final long serialVersionUID = 4682998802208066235L;

    public ProtectorExecutionException() {
    }

    public ProtectorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtectorExecutionException(String message) {
        super(message);
    }

    public ProtectorExecutionException(Throwable cause) {
        super(cause);
    }
}
