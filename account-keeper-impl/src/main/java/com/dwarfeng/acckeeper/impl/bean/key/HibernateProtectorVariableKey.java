package com.dwarfeng.acckeeper.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateProtectorVariableKey implements Key {

    private static final long serialVersionUID = 5192976103699990164L;

    private String protectorInfoId;
    private String variableId;

    public HibernateProtectorVariableKey() {
    }

    public HibernateProtectorVariableKey(String protectorInfoId, String variableId) {
        this.protectorInfoId = protectorInfoId;
        this.variableId = variableId;
    }

    public String getProtectorInfoId() {
        return protectorInfoId;
    }

    public void setProtectorInfoId(String protectorInfoId) {
        this.protectorInfoId = protectorInfoId;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HibernateProtectorVariableKey that = (HibernateProtectorVariableKey) o;

        if (!Objects.equals(protectorInfoId, that.protectorInfoId))
            return false;
        return Objects.equals(variableId, that.variableId);
    }

    @Override
    public int hashCode() {
        int result = protectorInfoId != null ? protectorInfoId.hashCode() : 0;
        result = 31 * result + (variableId != null ? variableId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HibernateProtectorVariableKey{" +
                "protectorInfoId='" + protectorInfoId + '\'' +
                ", variableId='" + variableId + '\'' +
                '}';
    }
}
