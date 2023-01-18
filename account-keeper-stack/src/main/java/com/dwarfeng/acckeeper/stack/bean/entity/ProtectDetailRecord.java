package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 保护详细信息记录。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectDetailRecord implements Entity<RecordKey> {

    private static final long serialVersionUID = -5473193524956094009L;

    private RecordKey key;
    private String value;

    public ProtectDetailRecord() {
    }

    public ProtectDetailRecord(RecordKey key, String value) {
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
        return "ProtectDetailRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
