package com.dwarfeng.acckeeper.impl.handler.locator;

import com.dwarfeng.acckeeper.impl.handler.Locator;

import java.util.Objects;

/**
 * 抽象定位器。
 *
 * <p>
 * 定位器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractLocator implements Locator {

    protected String locatorType;

    public AbstractLocator() {
    }

    public AbstractLocator(String sinkType) {
        this.locatorType = sinkType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(locatorType, type);
    }

    public String getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    @Override
    public String toString() {
        return "AbstractLocator{" +
                "locatorType='" + locatorType + '\'' +
                '}';
    }
}
