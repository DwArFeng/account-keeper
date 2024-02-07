package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;
import java.util.Map;

public final class LoginComplex {

    private final StringIdKey accountKey;
    private final String accountId;
    private final Date happenedDate;
    private final int responseCode;
    private final String message;
    private final Integer alarmLevel;
    private final Map<String, String> extraParams;
    private final Map<String, String> protectDetail;
    private final Date expireDate;
    private final long serialVersion;
    private final String loginRemark;
    private final HandlerException exception;
    private final Account account;

    public LoginComplex(
            StringIdKey accountKey, String accountId, Date happenedDate, int responseCode, String message,
            Integer alarmLevel, Map<String, String> extraParams, Map<String, String> protectDetail, Date expireDate,
            long serialVersion, String loginRemark, HandlerException exception, Account account
    ) {
        this.accountKey = accountKey;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.extraParams = extraParams;
        this.protectDetail = protectDetail;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
        this.loginRemark = loginRemark;
        this.exception = exception;
        this.account = account;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public Map<String, String> getProtectDetail() {
        return protectDetail;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public String getLoginRemark() {
        return loginRemark;
    }

    public HandlerException getException() {
        return exception;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "LoginComplex{" +
                "accountKey=" + accountKey +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", extraParams=" + extraParams +
                ", protectDetail=" + protectDetail +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", loginRemark='" + loginRemark + '\'' +
                ", exception=" + exception +
                ", account=" + account +
                '}';
    }
}
