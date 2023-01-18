package com.dwarfeng.acckeeper.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 记录实体主键。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class RecordKey implements Key {

    private static final long serialVersionUID = -6002675707014724775L;

    private Long loginHistoryId;
    private String recordId;

    public RecordKey() {
    }

    public RecordKey(Long loginHistoryId, String recordId) {
        this.loginHistoryId = loginHistoryId;
        this.recordId = recordId;
    }

    public Long getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Long loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordKey recordKey = (RecordKey) o;

        if (!Objects.equals(loginHistoryId, recordKey.loginHistoryId))
            return false;
        return Objects.equals(recordId, recordKey.recordId);
    }

    @Override
    public int hashCode() {
        int result = loginHistoryId != null ? loginHistoryId.hashCode() : 0;
        result = 31 * result + (recordId != null ? recordId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RecordKey{" +
                "loginHistoryId=" + loginHistoryId +
                ", recordId='" + recordId + '\'' +
                '}';
    }
}
