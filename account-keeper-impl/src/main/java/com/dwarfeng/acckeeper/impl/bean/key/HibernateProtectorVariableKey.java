package com.dwarfeng.acckeeper.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

public class HibernateProtectorVariableKey implements Key {

    private static final long serialVersionUID = 2620113708077340102L;

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
    public String toString() {
        return "HibernateProtectorVariableKey{" +
                "protectorInfoId='" + protectorInfoId + '\'' +
                ", variableId='" + variableId + '\'' +
                '}';
    }
}
