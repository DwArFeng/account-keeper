package com.dwarfeng.acckeeper.sdk.exception;

import com.dwarfeng.acckeeper.sdk.internal.i18n.SdkMessageKey;
import com.dwarfeng.acckeeper.sdk.internal.i18n.SdkMessages;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;

import java.util.function.Supplier;

/**
 * account-keeper 服务异常代码供应器。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class ServiceExceptionCodeSuppliers {

    private static volatile int EXCEPTION_CODE_OFFSET = 1000;

    public static final Supplier<ServiceException.Code> ACCOUNT_ALREADY_EXISTED =
            () -> code(0, SdkMessageKey.SERVICE_EXCEPTION_ACCOUNT_ALREADY_EXISTED);
    public static final Supplier<ServiceException.Code> ACCOUNT_DISABLED =
            () -> code(10, SdkMessageKey.SERVICE_EXCEPTION_ACCOUNT_DISABLED);
    public static final Supplier<ServiceException.Code> ACCOUNT_NOT_EXISTS =
            () -> code(20, SdkMessageKey.SERVICE_EXCEPTION_ACCOUNT_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> LOGIN_STATE_EXPIRED =
            () -> code(30, SdkMessageKey.SERVICE_EXCEPTION_LOGIN_STATE_EXPIRED);
    public static final Supplier<ServiceException.Code> LOGIN_STATE_NOT_EXISTS =
            () -> code(40, SdkMessageKey.SERVICE_EXCEPTION_LOGIN_STATE_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> PASSWORD_INCORRECT =
            () -> code(50, SdkMessageKey.SERVICE_EXCEPTION_PASSWORD_INCORRECT);
    public static final Supplier<ServiceException.Code> SERIAL_VERSION_INCONSISTENT =
            () -> code(60, SdkMessageKey.SERVICE_EXCEPTION_SERIAL_VERSION_INCONSISTENT);
    public static final Supplier<ServiceException.Code> PROTECTOR_FAILED =
            () -> code(70, SdkMessageKey.SERVICE_EXCEPTION_PROTECTOR_FAILED);
    public static final Supplier<ServiceException.Code> PROTECTOR_EXECUTION_FAILED =
            () -> code(71, SdkMessageKey.SERVICE_EXCEPTION_PROTECTOR_EXECUTION_FAILED);
    public static final Supplier<ServiceException.Code> PROTECTOR_MAKE_FAILED =
            () -> code(72, SdkMessageKey.SERVICE_EXCEPTION_PROTECTOR_MAKE_FAILED);
    public static final Supplier<ServiceException.Code> UNSUPPORTED_PROTECTOR_TYPE =
            () -> code(73, SdkMessageKey.SERVICE_EXCEPTION_UNSUPPORTED_PROTECTOR_TYPE);
    public static final Supplier<ServiceException.Code> PROTECTOR_INFO_NOT_EXISTS =
            () -> code(80, SdkMessageKey.SERVICE_EXCEPTION_PROTECTOR_INFO_NOT_EXISTS);
    public static final Supplier<ServiceException.Code> PROTECTOR_PROHIBITED =
            () -> code(90, SdkMessageKey.SERVICE_EXCEPTION_PROTECTOR_PROHIBITED);
    public static final Supplier<ServiceException.Code> LOGIN_STATE_KEY_CONFLICT =
            () -> code(100, SdkMessageKey.SERVICE_EXCEPTION_LOGIN_STATE_KEY_CONFLICT);

    private static ServiceException.Code code(int code, SdkMessageKey key) {
        return new ServiceException.Code(EXCEPTION_CODE_OFFSET + code, SdkMessages.message(key));
    }

    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;
    }

    private ServiceExceptionCodeSuppliers() {
        throw new IllegalStateException("禁止实例化");
    }
}
