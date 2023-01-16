package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.handler.LocateHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class LocateHandlerImpl implements LocateHandler {

    private final List<Locator> locators;

    @Value("${locator.type}")
    private String locatorType;

    private Locator locator;

    public LocateHandlerImpl(List<Locator> locators) {
        this.locators = Optional.ofNullable(locators).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.locator = locators.stream().filter(p -> p.supportType(locatorType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 locator 类型: " + locatorType));
    }

    @Override
    public LocateResult locate(@Nullable String ipAddress) throws HandlerException {
        return locator.locate(ipAddress);
    }
}
