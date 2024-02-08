package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;

public final class DeriveComplex {

    private final LongIdKey loginStateKey;
    private final Long loginStateId;
    private final Date happenedDate;
    private final int responseCode;
    private final StringIdKey accountKey;
    private final String accountId;
    private final Date expireDate;
    private final Long serialVersion;
    private final String deriveRemark;
    private final HandlerException exception;
    private final Account account;

    public DeriveComplex(
            LongIdKey loginStateKey, Long loginStateId, Date happenedDate, int responseCode, StringIdKey accountKey,
            String accountId, Date expireDate, Long serialVersion, String deriveRemark, HandlerException exception,
            Account account
    ) {
        this.loginStateKey = loginStateKey;
        this.loginStateId = loginStateId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.accountKey = accountKey;
        this.accountId = accountId;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
        this.deriveRemark = deriveRemark;
        this.exception = exception;
        this.account = account;
    }

    public LongIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public Long getLoginStateId() {
        return loginStateId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public Long getSerialVersion() {
        return serialVersion;
    }

    public String getDeriveRemark() {
        return deriveRemark;
    }

    public HandlerException getException() {
        return exception;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "DeriveComplex{" +
                "loginStateKey=" + loginStateKey +
                ", loginStateId=" + loginStateId +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", accountKey=" + accountKey +
                ", accountId='" + accountId + '\'' +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", deriveRemark='" + deriveRemark + '\'' +
                ", exception=" + exception +
                ", account=" + account +
                '}';
    }
}
