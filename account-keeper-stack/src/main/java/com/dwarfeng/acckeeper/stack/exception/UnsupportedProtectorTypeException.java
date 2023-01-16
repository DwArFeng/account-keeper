package com.dwarfeng.acckeeper.stack.exception;

/**
 * 不支持的保护器类型异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class UnsupportedProtectorTypeException extends ProtectorException {

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
        return "不支持的保护器类型: " + type;
    }
}
