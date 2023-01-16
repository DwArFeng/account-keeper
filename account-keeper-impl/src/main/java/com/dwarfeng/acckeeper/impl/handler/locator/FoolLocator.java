package com.dwarfeng.acckeeper.impl.handler.locator;

import com.dwarfeng.acckeeper.stack.handler.LocateHandler.LocateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * 任何 IP 地址都返回未知结果的傻瓜定位器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FoolLocator extends AbstractLocator {

    public static final String SUPPORT_TYPE = "fool";

    public FoolLocator() {
        super(SUPPORT_TYPE);
    }

    @Override
    public LocateResult locate(@Nullable String ipAddress) throws HandlerException {
        return new LocateResult(null, null, null);
    }

    @Override
    public String toString() {
        return "FoolLocator{" +
                "locatorType='" + locatorType + '\'' +
                '}';
    }
}
