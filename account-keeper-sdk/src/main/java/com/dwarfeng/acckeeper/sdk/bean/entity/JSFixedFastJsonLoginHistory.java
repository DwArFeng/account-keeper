package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 登录历史。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonLoginHistory implements Bean {

    private static final long serialVersionUID = 560033093765964356L;

    public static JSFixedFastJsonLoginHistory of(LoginHistory loginHistory) {
        if (Objects.isNull(loginHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonLoginHistory(
                    JSFixedFastJsonLongIdKey.of(loginHistory.getKey()),
                    loginHistory.getAccountId(), loginHistory.getHappenedDate(), loginHistory.getResponseCode(),
                    loginHistory.getMessage(), loginHistory.getAlarmLevel(), loginHistory.getLoginRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

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

    @JSONField(name = "login_remark", ordinal = 7)
    private String loginRemark;

    public JSFixedFastJsonLoginHistory() {
    }

    public JSFixedFastJsonLoginHistory(
            JSFixedFastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String message,
            Integer alarmLevel, String loginRemark
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.loginRemark = loginRemark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
        return "JSFixedFastJsonLoginHistory{" +
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
