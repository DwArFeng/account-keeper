package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 登录历史。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginHistory implements Entity<LongIdKey> {

    private static final long serialVersionUID = -885919859579588145L;

    private LongIdKey key;
    private StringIdKey accountKey;
    private Date happenedDate;
    private String ipAddress;
    private String location;
    private Double latitude;
    private Double longitude;
    private int responseCode;
    private String notExistsAccountId;

    public LoginHistory() {
    }

    public LoginHistory(
            LongIdKey key, StringIdKey accountKey, Date happenedDate, String ipAddress, String location,
            Double latitude, Double longitude, int responseCode, String notExistsAccountId
    ) {
        this.key = key;
        this.accountKey = accountKey;
        this.happenedDate = happenedDate;
        this.ipAddress = ipAddress;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.responseCode = responseCode;
        this.notExistsAccountId = notExistsAccountId;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
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

    public String getNotExistsAccountId() {
        return notExistsAccountId;
    }

    public void setNotExistsAccountId(String notExistsAccountId) {
        this.notExistsAccountId = notExistsAccountId;
    }

    @Override
    public String toString() {
        return "LoginHistory{" +
                "key=" + key +
                ", accountKey=" + accountKey +
                ", happenedDate=" + happenedDate +
                ", ipAddress='" + ipAddress + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", responseCode=" + responseCode +
                ", notExistsAccountId='" + notExistsAccountId + '\'' +
                '}';
    }
}
