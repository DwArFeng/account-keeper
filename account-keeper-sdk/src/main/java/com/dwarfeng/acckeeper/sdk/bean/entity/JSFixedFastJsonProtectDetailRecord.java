package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.JSFixedFastJsonRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.Objects;

/**
 * JSFixed FastJson 保护详细信息记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonProtectDetailRecord implements Bean {

    @Serial
    private static final long serialVersionUID = -6228330830986332235L;

    public static JSFixedFastJsonProtectDetailRecord of(ProtectDetailRecord protectDetailRecord) {
        if (Objects.isNull(protectDetailRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonProtectDetailRecord(
                    JSFixedFastJsonRecordKey.of(protectDetailRecord.getKey()),
                    protectDetailRecord.getValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    public JSFixedFastJsonProtectDetailRecord() {
    }

    public JSFixedFastJsonProtectDetailRecord(JSFixedFastJsonRecordKey key, String value) {
        this.key = key;
        this.value = value;
    }

    public JSFixedFastJsonRecordKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonRecordKey key) {
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
        return "JSFixedFastJsonProtectDetailRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
