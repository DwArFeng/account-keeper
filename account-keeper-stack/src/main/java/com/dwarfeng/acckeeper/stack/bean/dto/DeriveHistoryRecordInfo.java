package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;

import java.io.Serial;
import java.util.Date;

/**
 * 派生历史记录信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class DeriveHistoryRecordInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 6839133956791793109L;

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String deriveRemark;

    public DeriveHistoryRecordInfo() {
    }

    public DeriveHistoryRecordInfo(
            LongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
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
        return "DeriveHistoryRecordInfo{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                '}';
    }
}
