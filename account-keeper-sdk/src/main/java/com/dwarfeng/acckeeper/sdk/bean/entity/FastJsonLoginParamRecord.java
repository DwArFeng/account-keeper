package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 登录参数记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonLoginParamRecord implements Bean {

    private static final long serialVersionUID = -6318424608515140712L;

    public static FastJsonLoginParamRecord of(LoginParamRecord loginParamRecord) {
        if (Objects.isNull(loginParamRecord)) {
            return null;
        } else {
            return new FastJsonLoginParamRecord(
                    FastJsonRecordKey.of(loginParamRecord.getKey()),
                    loginParamRecord.getValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    public FastJsonLoginParamRecord() {
    }

    public FastJsonLoginParamRecord(FastJsonRecordKey key, String value) {
        this.key = key;
        this.value = value;
    }

    public FastJsonRecordKey getKey() {
        return key;
    }

    public void setKey(FastJsonRecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FastJsonLoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
