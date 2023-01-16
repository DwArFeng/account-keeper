package com.dwarfeng.acckeeper.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 1000;

    public static final ServiceException.Code ACCOUNT_ALREADY_EXISTED =
            new ServiceException.Code(offset(0), "account already existed");
    public static final ServiceException.Code ACCOUNT_DISABLED =
            new ServiceException.Code(offset(10), "account disabled");
    public static final ServiceException.Code ACCOUNT_NOT_EXISTS =
            new ServiceException.Code(offset(20), "account not exists");
    public static final ServiceException.Code LOGIN_STATE_EXPIRED =
            new ServiceException.Code(offset(30), "login state expired");
    public static final ServiceException.Code LOGIN_STATE_NOT_EXISTS =
            new ServiceException.Code(offset(40), "login state not exists");
    public static final ServiceException.Code PASSWORD_INCORRECT =
            new ServiceException.Code(offset(50), "password incorrect");
    public static final ServiceException.Code SERIAL_VERSION_INCONSISTENT =
            new ServiceException.Code(offset(60), "serial version inconsistent");
    public static final ServiceException.Code PROTECTOR_FAILED =
            new ServiceException.Code(offset(70), "protector failed");
    public static final ServiceException.Code PROTECTOR_EXECUTION_FAILED =
            new ServiceException.Code(offset(71), "protector execution failed");
    public static final ServiceException.Code PROTECTOR_MAKE_FAILED =
            new ServiceException.Code(offset(72), "protector make failed");
    public static final ServiceException.Code UNSUPPORTED_PROTECTOR_TYPE =
            new ServiceException.Code(offset(73), "unsupported protector type");
    public static final ServiceException.Code PROTECTOR_INFO_NOT_EXISTED =
            new ServiceException.Code(offset(80), "protector info not existed");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        ACCOUNT_ALREADY_EXISTED.setCode(offset(0));
        ACCOUNT_DISABLED.setCode(offset(10));
        ACCOUNT_NOT_EXISTS.setCode(offset(20));
        LOGIN_STATE_EXPIRED.setCode(offset(30));
        LOGIN_STATE_NOT_EXISTS.setCode(offset(40));
        PASSWORD_INCORRECT.setCode(offset(50));
        SERIAL_VERSION_INCONSISTENT.setCode(offset(60));
        PROTECTOR_FAILED.setCode(offset(70));
        PROTECTOR_EXECUTION_FAILED.setCode(offset(71));
        PROTECTOR_MAKE_FAILED.setCode(offset(72));
        UNSUPPORTED_PROTECTOR_TYPE.setCode(offset(73));
        PROTECTOR_INFO_NOT_EXISTED.setCode(offset(80));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
