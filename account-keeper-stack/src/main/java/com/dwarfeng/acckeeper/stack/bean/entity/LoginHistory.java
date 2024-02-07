package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 登录历史。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginHistory implements Entity<LongIdKey> {

    private static final long serialVersionUID = 6675486972375267544L;

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String message;
    private Integer alarmLevel;

    /**
     * 登录备注。
     *
     * @since 1.7.0
     */
    private String loginRemark;

    public LoginHistory() {
    }

    public LoginHistory(
            LongIdKey key, String accountId, Date happenedDate, int responseCode, String message, Integer alarmLevel,
            String loginRemark
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.loginRemark = loginRemark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getLoginRemark() {
        return loginRemark;
    }

    public void setLoginRemark(String loginRemark) {
        this.loginRemark = loginRemark;
    }

    @Override
    public String toString() {
        return "LoginHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", loginRemark='" + loginRemark + '\'' +
                '}';
    }
}
