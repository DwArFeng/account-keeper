package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(AccountAlreadyExistedException.class, ServiceExceptionCodes.ACCOUNT_ALREADY_EXISTED);
        destination.put(AccountDisabledException.class, ServiceExceptionCodes.ACCOUNT_DISABLED);
        destination.put(AccountNotExistsException.class, ServiceExceptionCodes.ACCOUNT_NOT_EXISTS);
        destination.put(LoginStateExpiredException.class, ServiceExceptionCodes.LOGIN_STATE_EXPIRED);
        destination.put(LoginStateNotExistsException.class, ServiceExceptionCodes.LOGIN_STATE_NOT_EXISTS);
        destination.put(PasswordIncorrectException.class, ServiceExceptionCodes.PASSWORD_INCORRECT);
        destination.put(SerialVersionInconsistentException.class, ServiceExceptionCodes.SERIAL_VERSION_INCONSISTENT);
        destination.put(ProtectorException.class, ServiceExceptionCodes.PROTECTOR_FAILED);
        destination.put(ProtectorExecutionException.class, ServiceExceptionCodes.PROTECTOR_EXECUTION_FAILED);
        destination.put(ProtectorMakeException.class, ServiceExceptionCodes.PROTECTOR_MAKE_FAILED);
        destination.put(UnsupportedProtectorTypeException.class, ServiceExceptionCodes.UNSUPPORTED_PROTECTOR_TYPE);
        destination.put(ProtectorInfoNotExistsException.class, ServiceExceptionCodes.PROTECTOR_INFO_NOT_EXISTED);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
