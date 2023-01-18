package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 保护详细信息记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonProtectDetailRecord implements Bean {

    private static final long serialVersionUID = -8765356637668105971L;

    public static FastJsonProtectDetailRecord of(ProtectDetailRecord protectDetailRecord) {
        if (Objects.isNull(protectDetailRecord)) {
            return null;
        } else {
            return new FastJsonProtectDetailRecord(
                    FastJsonRecordKey.of(protectDetailRecord.getKey()),
                    protectDetailRecord.getValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    public FastJsonProtectDetailRecord() {
    }

    public FastJsonProtectDetailRecord(FastJsonRecordKey key, String value) {
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
        return "FastJsonProtectDetailRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
