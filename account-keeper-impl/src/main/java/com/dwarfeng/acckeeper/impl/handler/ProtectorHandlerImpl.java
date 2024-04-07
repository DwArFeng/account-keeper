package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.acckeeper.stack.exception.UnsupportedProtectorTypeException;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.handler.ProtectorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ProtectorHandlerImpl implements ProtectorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectorHandlerImpl.class);

    private final List<ProtectorMaker> protectorMakers;

    public ProtectorHandlerImpl(List<ProtectorMaker> protectorMakers) {
        this.protectorMakers = Optional.ofNullable(protectorMakers).orElse(Collections.emptyList());
    }

    @Override
    public Protector make(String type, String param) throws ProtectorException {
        try {
            // 生成保护器。
            LOGGER.debug("通过保护器信息构建新的的保护器...");
            ProtectorMaker protectorMaker = protectorMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedProtectorTypeException(type));
            Protector protector = protectorMaker.makeProtector(type, param);
            LOGGER.debug("保护器构建成功!");
            LOGGER.debug("保护器: {}", protector);
            return protector;
        } catch (ProtectorException e) {
            throw e;
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }
}
