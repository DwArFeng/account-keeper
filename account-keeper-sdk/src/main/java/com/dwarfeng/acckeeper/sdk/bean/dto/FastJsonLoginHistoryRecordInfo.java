package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * FastJson 登录历史记录信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonLoginHistoryRecordInfo implements Dto {

    private static final long serialVersionUID = -1132174023173355395L;

    public static FastJsonLoginHistoryRecordInfo of(LoginHistoryRecordInfo loginHistoryRecordInfo) {
        if (Objects.isNull(loginHistoryRecordInfo)) {
            return null;
        } else {
            return new FastJsonLoginHistoryRecordInfo(
                    FastJsonLongIdKey.of(loginHistoryRecordInfo.getLoginHistoryKey()),
                    loginHistoryRecordInfo.getAccountId(),
                    loginHistoryRecordInfo.getHappenedDate(),
                    loginHistoryRecordInfo.getResponseCode(),
                    loginHistoryRecordInfo.getMessage(),
                    loginHistoryRecordInfo.getAlarmLevel(),
                    loginHistoryRecordInfo.getExtraParamMap(),
                    loginHistoryRecordInfo.getProtectDetailMap(),
                    loginHistoryRecordInfo.getLoginRemark()
            );
        }
    }

    @JSONField(name = "login_history_key", ordinal = 1)
    private FastJsonLongIdKey loginHistoryKey;

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

    @JSONField(name = "extra_params", ordinal = 7)
    private Map<String, String> extraParamMap;

    @JSONField(name = "protect_details", ordinal = 8)
    private Map<String, String> protectDetailMap;

    @JSONField(name = "login_remark", ordinal = 9)
    private String loginRemark;

    public FastJsonLoginHistoryRecordInfo() {
    }

    public FastJsonLoginHistoryRecordInfo(
            FastJsonLongIdKey loginHistoryKey, String accountId, Date happenedDate, int responseCode, String message,
            Integer alarmLevel, Map<String, String> extraParamMap, Map<String, String> protectDetailMap,
            String loginRemark
    ) {
        this.loginHistoryKey = loginHistoryKey;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.extraParamMap = extraParamMap;
        this.protectDetailMap = protectDetailMap;
        this.loginRemark = loginRemark;
    }

    public FastJsonLongIdKey getLoginHistoryKey() {
        return loginHistoryKey;
    }

    public void setLoginHistoryKey(FastJsonLongIdKey loginHistoryKey) {
        this.loginHistoryKey = loginHistoryKey;
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

    public Map<String, String> getExtraParamMap() {
        return extraParamMap;
    }

    public void setExtraParamMap(Map<String, String> extraParamMap) {
        this.extraParamMap = extraParamMap;
    }

    public Map<String, String> getProtectDetailMap() {
        return protectDetailMap;
    }

    public void setProtectDetailMap(Map<String, String> protectDetailMap) {
        this.protectDetailMap = protectDetailMap;
    }

    public String getLoginRemark() {
        return loginRemark;
    }

    public void setLoginRemark(String loginRemark) {
        this.loginRemark = loginRemark;
    }

    @Override
    public String toString() {
        return "FastJsonLoginHistoryRecordInfo{" +
                "loginHistoryKey=" + loginHistoryKey +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", extraParamMap=" + extraParamMap +
                ", protectDetailMap=" + protectDetailMap +
                ", loginRemark='" + loginRemark + '\'' +
                '}';
    }
}
