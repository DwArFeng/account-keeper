package com.dwarfeng.acckeeper.sdk.util;

import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 account-keeper 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(AccountAlreadyExistedException.class, ServiceExceptionCodes.ACCOUNT_ALREADY_EXISTED);
        map.put(AccountDisabledException.class, ServiceExceptionCodes.ACCOUNT_DISABLED);
        map.put(AccountNotExistsException.class, ServiceExceptionCodes.ACCOUNT_NOT_EXISTS);
        map.put(LoginStateExpiredException.class, ServiceExceptionCodes.LOGIN_STATE_EXPIRED);
        map.put(LoginStateNotExistsException.class, ServiceExceptionCodes.LOGIN_STATE_NOT_EXISTS);
        map.put(PasswordIncorrectException.class, ServiceExceptionCodes.PASSWORD_INCORRECT);
        map.put(SerialVersionInconsistentException.class, ServiceExceptionCodes.SERIAL_VERSION_INCONSISTENT);
        map.put(ProtectorException.class, ServiceExceptionCodes.PROTECTOR_FAILED);
        map.put(ProtectorExecutionException.class, ServiceExceptionCodes.PROTECTOR_EXECUTION_FAILED);
        map.put(ProtectorMakeException.class, ServiceExceptionCodes.PROTECTOR_MAKE_FAILED);
        map.put(UnsupportedProtectorTypeException.class, ServiceExceptionCodes.UNSUPPORTED_PROTECTOR_TYPE);
        map.put(ProtectorInfoNotExistsException.class, ServiceExceptionCodes.PROTECTOR_INFO_NOT_EXISTED);
        map.put(ProtectorProhibitedException.class, ServiceExceptionCodes.PROTECTOR_PROHIBITED);
        map.put(LoginStateKeyConflictException.class, ServiceExceptionCodes.LOGIN_STATE_KEY_CONFLICT);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
