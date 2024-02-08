package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.JSFixedFastJsonRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 登录参数记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonLoginParamRecord implements Bean {

    private static final long serialVersionUID = -3908086017826850843L;

    public static JSFixedFastJsonLoginParamRecord of(LoginParamRecord loginParamRecord) {
        if (Objects.isNull(loginParamRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonLoginParamRecord(
                    JSFixedFastJsonRecordKey.of(loginParamRecord.getKey()),
                    loginParamRecord.getValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    public JSFixedFastJsonLoginParamRecord() {
    }

    public JSFixedFastJsonLoginParamRecord(JSFixedFastJsonRecordKey key, String value) {
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
        return "JSFixedFastJsonLoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
