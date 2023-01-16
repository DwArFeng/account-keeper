package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Map;

/**
 * 登陆响应。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginResponse implements Dto {

    private static final long serialVersionUID = 8382298249187947285L;

    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String message;
    private Integer alarmLevel;
    private Map<String, String> extraParams;
    private Map<String, String> protectDetail;

    public LoginResponse() {
    }

    public LoginResponse(
            String accountId, Date happenedDate, int responseCode, String message, Integer alarmLevel,
            Map<String, String> extraParams, Map<String, String> protectDetail
    ) {
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.extraParams = extraParams;
        this.protectDetail = protectDetail;
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

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    public Map<String, String> getProtectDetail() {
        return protectDetail;
    }

    public void setProtectDetail(Map<String, String> protectDetail) {
        this.protectDetail = protectDetail;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", extraParams=" + extraParams +
                ", protectDetail=" + protectDetail +
                '}';
    }
}
