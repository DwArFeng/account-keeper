package com.dwarfeng.acckeeper.impl.handler.protector;

import com.dwarfeng.acckeeper.impl.handler.ProtectorMaker;
import com.dwarfeng.acckeeper.impl.handler.ProtectorSupporter;

import java.util.Objects;

/**
 * 抽象保护器注册。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public abstract class AbstractProtectorRegistry implements ProtectorMaker, ProtectorSupporter {

    protected String protectorType;

    public AbstractProtectorRegistry() {
    }

    public AbstractProtectorRegistry(String protectorType) {
        this.protectorType = protectorType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(protectorType, type);
    }

    @Override
    public String provideType() {
        return protectorType;
    }

    public String getProtectorType() {
        return protectorType;
    }

    public void setProtectorType(String protectorType) {
        this.protectorType = protectorType;
    }

    @Override
    public String toString() {
        return "AbstractProtectorRegistry{" +
                "protectorType='" + protectorType + '\'' +
                '}';
    }
}
