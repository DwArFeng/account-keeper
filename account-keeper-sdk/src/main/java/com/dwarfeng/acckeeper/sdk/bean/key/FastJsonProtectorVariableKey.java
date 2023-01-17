package com.dwarfeng.acckeeper.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonProtectorVariableKey implements Key {

    private static final long serialVersionUID = 1327673449106290420L;

    public static FastJsonProtectorVariableKey of(ProtectorVariableKey protectorVariableKey) {
        if (Objects.isNull(protectorVariableKey)) {
            return null;
        } else {
            return new FastJsonProtectorVariableKey(
                    protectorVariableKey.getProtectorInfoId(),
                    protectorVariableKey.getVariableId()
            );
        }
    }

    @JSONField(name = "protector_info_id", ordinal = 1)
    private String protectorInfoId;

    @JSONField(name = "variable_id", ordinal = 2)
    private String variableId;

    public FastJsonProtectorVariableKey() {
    }

    public FastJsonProtectorVariableKey(String protectorInfoId, String variableId) {
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

        FastJsonProtectorVariableKey that = (FastJsonProtectorVariableKey) o;

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
        return "FastJsonProtectorVariableKey{" +
                "protectorInfoId='" + protectorInfoId + '\'' +
                ", variableId='" + variableId + '\'' +
                '}';
    }
}
