package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 登录参数记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginParamRecord implements Entity<RecordKey> {

    private static final long serialVersionUID = -1927813145622201172L;

    private RecordKey key;
    private String value;

    public LoginParamRecord() {
    }

    public LoginParamRecord(RecordKey key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public RecordKey getKey() {
        return key;
    }

    @Override
    public void setKey(RecordKey key) {
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
        return "LoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
