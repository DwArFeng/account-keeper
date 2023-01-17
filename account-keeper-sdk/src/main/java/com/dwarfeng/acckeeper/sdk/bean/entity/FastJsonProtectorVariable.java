package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 保护器变量。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonProtectorVariable implements Bean {

    private static final long serialVersionUID = 8551292769768381306L;

    public static FastJsonProtectorVariable of(ProtectorVariable protectorVariable) {
        if (Objects.isNull(protectorVariable)) {
            return null;
        } else {
            return new FastJsonProtectorVariable(
                    FastJsonProtectorVariableKey.of(protectorVariable.getKey()),
                    protectorVariable.getValue(), protectorVariable.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonProtectorVariableKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonProtectorVariable() {
    }

    public FastJsonProtectorVariable(FastJsonProtectorVariableKey key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public FastJsonProtectorVariableKey getKey() {
        return key;
    }

    public void setKey(FastJsonProtectorVariableKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonProtectorVariable{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
