package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 登录历史。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonLoginHistory implements Bean {

    private static final long serialVersionUID = 1820203658523243716L;

    public static FastJsonLoginHistory of(LoginHistory loginHistory) {
        if (Objects.isNull(loginHistory)) {
            return null;
        } else {
            return new FastJsonLoginHistory(
                    FastJsonLongIdKey.of(loginHistory.getKey()),
                    loginHistory.getAccountId(), loginHistory.getHappenedDate(), loginHistory.getResponseCode(),
                    loginHistory.getMessage(), loginHistory.getAlarmLevel()
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

    @JSONField(name = "message", ordinal = 5)
    private String message;

    @JSONField(name = "alarm_level", ordinal = 6)
    private Integer alarmLevel;

    public FastJsonLoginHistory() {
    }

    public FastJsonLoginHistory(
            FastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String message,
            Integer alarmLevel
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
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

    @Override
    public String toString() {
        return "FastJsonLoginHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                '}';
    }
}
