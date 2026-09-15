package com.dwarfeng.acckeeper.sdk.exception;

import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * account-keeper 服务异常映射帮助类。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class ServiceExceptionHelper {

    public static Map<Class<? extends Exception>, Supplier<ServiceException.Code>> putDefaultDestination(
            Map<Class<? extends Exception>, Supplier<ServiceException.Code>> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }
        map.put(AccountAlreadyExistedException.class, ServiceExceptionCodeSuppliers.ACCOUNT_ALREADY_EXISTED);
        map.put(AccountDisabledException.class, ServiceExceptionCodeSuppliers.ACCOUNT_DISABLED);
        map.put(AccountNotExistsException.class, ServiceExceptionCodeSuppliers.ACCOUNT_NOT_EXISTS);
        map.put(LoginStateExpiredException.class, ServiceExceptionCodeSuppliers.LOGIN_STATE_EXPIRED);
        map.put(LoginStateNotExistsException.class, ServiceExceptionCodeSuppliers.LOGIN_STATE_NOT_EXISTS);
        map.put(PasswordIncorrectException.class, ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT);
        map.put(SerialVersionInconsistentException.class, ServiceExceptionCodeSuppliers.SERIAL_VERSION_INCONSISTENT);
        map.put(ProtectorException.class, ServiceExceptionCodeSuppliers.PROTECTOR_FAILED);
        map.put(ProtectorExecutionException.class, ServiceExceptionCodeSuppliers.PROTECTOR_EXECUTION_FAILED);
        map.put(ProtectorMakeException.class, ServiceExceptionCodeSuppliers.PROTECTOR_MAKE_FAILED);
        map.put(UnsupportedProtectorTypeException.class, ServiceExceptionCodeSuppliers.UNSUPPORTED_PROTECTOR_TYPE);
        map.put(ProtectorInfoNotExistsException.class, ServiceExceptionCodeSuppliers.PROTECTOR_INFO_NOT_EXISTS);
        map.put(ProtectorProhibitedException.class, ServiceExceptionCodeSuppliers.PROTECTOR_PROHIBITED);
        map.put(LoginStateKeyConflictException.class, ServiceExceptionCodeSuppliers.LOGIN_STATE_KEY_CONFLICT);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止实例化");
    }
}
