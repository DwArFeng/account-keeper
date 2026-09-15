package com.dwarfeng.acckeeper.sdk.exception;

import com.dwarfeng.acckeeper.stack.exception.PasswordIncorrectException;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 服务异常映射帮助类测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class ServiceExceptionHelperTest {

    @Test
    public void shouldStoreSuppliersWithoutResolvingCodes() {
        Map<Class<? extends Exception>, Supplier<ServiceException.Code>> map = new HashMap<>();
        ServiceExceptionHelper.putDefaultDestination(map);

        Supplier<ServiceException.Code> supplier = map.get(PasswordIncorrectException.class);
        assertSame(ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT, supplier);
        assertEquals(1050, supplier.get().getCode());
    }
}
