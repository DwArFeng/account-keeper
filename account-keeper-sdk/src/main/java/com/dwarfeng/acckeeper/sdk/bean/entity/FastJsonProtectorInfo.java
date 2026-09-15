package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.FastJsonStringIdKey;

import java.io.Serial;
import java.util.Objects;

/**
 * FastJson 保护器信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonProtectorInfo implements Bean {

    @Serial
    private static final long serialVersionUID = -110233412663648690L;

    public static FastJsonProtectorInfo of(ProtectorInfo protectorInfo) {
        if (Objects.isNull(protectorInfo)) {
            return null;
        } else {
            return new FastJsonProtectorInfo(
                    FastJsonStringIdKey.of(protectorInfo.getKey()),
                    protectorInfo.getType(), protectorInfo.getParam(), protectorInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "param", ordinal = 4)
    private String param;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonProtectorInfo() {
    }

    public FastJsonProtectorInfo(FastJsonStringIdKey key, String type, String param, String remark) {
        this.key = key;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
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
        return "FastJsonProtectorInfo{" +
                "key=" + key +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
