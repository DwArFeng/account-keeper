package com.dwarfeng.acckeeper.sdk.exception;

import com.dwarfeng.acckeeper.base.sdk.i18n.MessageContext;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * account-keeper 服务异常代码测试。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public class ServiceExceptionCodeSuppliersTest {

    @Test
    public void shouldUseAcckeeperRangeAndLocalizedMessages() {
        ServiceException english = MessageContext.call(
                Locale.ENGLISH,
                () -> new ServiceException(ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT.get())
        );
        ServiceException chinese = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> new ServiceException(ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT.get())
        );

        assertEquals(1050, english.getCode().getCode());
        assertNotEquals(english.getCode().getTip(), chinese.getCode().getTip());
    }

    @Test
    public void shouldApplyUpdatedOffsetToNewCodes() {
        int previousOffset = ServiceExceptionCodeSuppliers.getExceptionCodeOffset();
        try {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(9000);
            assertEquals(9050, ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT.get().getCode());
        } finally {
            ServiceExceptionCodeSuppliers.setExceptionCodeOffset(previousOffset);
        }
    }

    @Test
    public void shouldPreserveCodeAndMessageAfterSerialization() throws IOException, ClassNotFoundException {
        ServiceException origin = new ServiceException(ServiceExceptionCodeSuppliers.ACCOUNT_NOT_EXISTS.get());

        ServiceException restored;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(origin);
            objectOutputStream.flush();

            try (ObjectInputStream objectInputStream = new ObjectInputStream(
                    new ByteArrayInputStream(outputStream.toByteArray())
            )) {
                restored = (ServiceException) objectInputStream.readObject();
            }
        }

        assertEquals(origin.getCode().getCode(), restored.getCode().getCode());
        assertEquals(origin.getCode().getTip(), restored.getCode().getTip());
        assertEquals(
                origin.getMessage(),
                MessageContext.call(Locale.ENGLISH, restored::getMessage)
        );
    }
}
