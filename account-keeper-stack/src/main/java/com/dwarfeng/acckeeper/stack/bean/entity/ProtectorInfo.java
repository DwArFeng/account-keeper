package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.basic.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 保护器信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorInfo implements Entity<StringIdKey> {

    @Serial
    private static final long serialVersionUID = 206133960300730808L;

    private StringIdKey key;
    private String type;
    private String param;
    private String remark;

    public ProtectorInfo() {
    }

    public ProtectorInfo(StringIdKey key, String type, String param, String remark) {
        this.key = key;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProtectorInfo{" +
                "key=" + key +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
