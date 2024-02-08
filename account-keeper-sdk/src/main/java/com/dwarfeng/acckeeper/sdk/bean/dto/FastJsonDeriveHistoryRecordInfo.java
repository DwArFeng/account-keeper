package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 派生历史记录信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class FastJsonDeriveHistoryRecordInfo implements Dto {

    private static final long serialVersionUID = -8710760304987040192L;

    public static FastJsonDeriveHistoryRecordInfo of(DeriveHistoryRecordInfo deriveHistoryRecordInfo) {
        if (Objects.isNull(deriveHistoryRecordInfo)) {
            return null;
        } else {
            return new FastJsonDeriveHistoryRecordInfo(
                    FastJsonLongIdKey.of(deriveHistoryRecordInfo.getKey()),
                    deriveHistoryRecordInfo.getAccountId(),
                    deriveHistoryRecordInfo.getHappenedDate(),
                    deriveHistoryRecordInfo.getResponseCode(),
                    deriveHistoryRecordInfo.getDeriveRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "account_id", ordinal = 2)
    private String accountId;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "response_code", ordinal = 4)
    private int responseCode;

    @JSONField(name = "derive_remark", ordinal = 5)
    private String deriveRemark;

    public FastJsonDeriveHistoryRecordInfo() {
    }

    public FastJsonDeriveHistoryRecordInfo(
            FastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDeriveRemark() {
        return deriveRemark;
    }

    public void setDeriveRemark(String deriveRemark) {
        this.deriveRemark = deriveRemark;
    }

    @Override
    public String toString() {
        return "FastJsonDeriveHistoryRecordInfo{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                '}';
    }
}
