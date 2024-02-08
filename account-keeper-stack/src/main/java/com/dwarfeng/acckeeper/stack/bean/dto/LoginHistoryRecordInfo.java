package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Map;

/**
 * 登录历史记录信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginHistoryRecordInfo implements Dto {

    private static final long serialVersionUID = -1456406253019267890L;

    private LongIdKey loginHistoryKey;
    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String message;
    private Integer alarmLevel;
    private Map<String, String> extraParamMap;
    private Map<String, String> protectDetailMap;

    /**
     * 登录备注。
     *
     * @since 1.7.0
     */
    private String loginRemark;

    public LoginHistoryRecordInfo() {
    }

    public LoginHistoryRecordInfo(
            LongIdKey loginHistoryKey, String accountId, Date happenedDate, int responseCode, String message,
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

    public LongIdKey getLoginHistoryKey() {
        return loginHistoryKey;
    }

    public void setLoginHistoryKey(LongIdKey loginHistoryKey) {
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
        return "LoginHistoryRecordInfo{" +
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
