package com.dwarfeng.acckeeper.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 记录实体主键。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonRecordKey implements Key {

    private static final long serialVersionUID = 3605828720963173360L;

    public static JSFixedFastJsonRecordKey of(RecordKey recordKey) {
        if (Objects.isNull(recordKey)) {
            return null;
        } else {
            return new JSFixedFastJsonRecordKey(
                    recordKey.getLoginHistoryId(), recordKey.getRecordId()
            );
        }
    }

    @JSONField(name = "login_history_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long loginHistoryId;

    @JSONField(name = "record_id", ordinal = 2)
    private String recordId;

    public JSFixedFastJsonRecordKey() {
    }

    public JSFixedFastJsonRecordKey(Long loginHistoryId, String recordId) {
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

        JSFixedFastJsonRecordKey that = (JSFixedFastJsonRecordKey) o;

        if (!Objects.equals(loginHistoryId, that.loginHistoryId))
            return false;
        return Objects.equals(recordId, that.recordId);
    }

    @Override
    public int hashCode() {
        int result = loginHistoryId != null ? loginHistoryId.hashCode() : 0;
        result = 31 * result + (recordId != null ? recordId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonRecordKey{" +
                "loginHistoryId=" + loginHistoryId +
                ", recordId='" + recordId + '\'' +
                '}';
    }
}
