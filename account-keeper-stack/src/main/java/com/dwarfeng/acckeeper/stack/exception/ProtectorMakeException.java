package com.dwarfeng.acckeeper.stack.exception;

/**
 * 保护器构造异常。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorMakeException extends ProtectorException {

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
        return "保护器构造异常, 类型为: " + protectorType + ", 参数为: " + param;
    }
}
