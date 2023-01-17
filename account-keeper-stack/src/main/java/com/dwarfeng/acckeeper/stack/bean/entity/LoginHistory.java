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

    private static final long serialVersionUID = 3434522669881705657L;

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;
    private String ipAddress;
    private String location;
    private Double latitude;
    private Double longitude;
    private int responseCode;

    public LoginHistory() {
    }

    public LoginHistory(
            LongIdKey key, String accountId, Date happenedDate, String ipAddress, String location, Double latitude,
            Double longitude, int responseCode
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.ipAddress = ipAddress;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.responseCode = responseCode;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "LoginHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", ipAddress='" + ipAddress + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", responseCode=" + responseCode +
                '}';
    }
}
