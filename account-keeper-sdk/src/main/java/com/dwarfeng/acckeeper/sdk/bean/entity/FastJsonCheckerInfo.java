package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 检查器信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonCheckerInfo implements Bean {

    private static final long serialVersionUID = -8069892181499809495L;

    public static FastJsonCheckerInfo of(CheckerInfo checkerInfo) {
        if (Objects.isNull(checkerInfo)) {
            return null;
        } else {
            return new FastJsonCheckerInfo(
                    FastJsonStringIdKey.of(checkerInfo.getKey()),
                    checkerInfo.getType(), checkerInfo.getParam(), checkerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "type", ordinal = 2)
    private String type;

    @JSONField(name = "param", ordinal = 3)
    private String param;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonCheckerInfo() {
    }

    public FastJsonCheckerInfo(FastJsonStringIdKey key, String type, String param, String remark) {
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
        return "FastJsonCheckerInfo{" +
                "key=" + key +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
