package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 保护器变量。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorVariable implements Entity<ProtectorVariableKey> {

    private static final long serialVersionUID = 2911513087694913296L;

    private ProtectorVariableKey key;
    private String value;
    private String remark;

    public ProtectorVariable() {
    }

    public ProtectorVariable(ProtectorVariableKey key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    @Override
    public ProtectorVariableKey getKey() {
        return key;
    }

    @Override
    public void setKey(ProtectorVariableKey key) {
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
        return "ProtectorVariable{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
