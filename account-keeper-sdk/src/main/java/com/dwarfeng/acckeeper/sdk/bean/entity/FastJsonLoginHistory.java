package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
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

    private static final long serialVersionUID = 4308533106665802379L;

    public static FastJsonLoginHistory of(LoginHistory loginHistory) {
        if (Objects.isNull(loginHistory)) {
            return null;
        } else {
            return new FastJsonLoginHistory(
                    FastJsonLongIdKey.of(loginHistory.getKey()),
                    FastJsonStringIdKey.of(loginHistory.getAccountKey()),
                    loginHistory.getHappenedDate(), loginHistory.getIpAddress(), loginHistory.getLocation(),
                    loginHistory.getLatitude(), loginHistory.getLongitude(), loginHistory.getResponseCode(),
                    loginHistory.getNotExistsAccountId()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "ip_address", ordinal = 4)
    private String ipAddress;

    @JSONField(name = "location", ordinal = 5)
    private String location;

    @JSONField(name = "latitude", ordinal = 6)
    private Double latitude;

    @JSONField(name = "longitude", ordinal = 7)
    private Double longitude;

    @JSONField(name = "response_code", ordinal = 8)
    private int responseCode;

    @JSONField(name = "not_exists_account_id", ordinal = 9)
    private String notExistsAccountId;

    public FastJsonLoginHistory() {
    }

    public FastJsonLoginHistory(
            FastJsonLongIdKey key, FastJsonStringIdKey accountKey, Date happenedDate, String ipAddress,
            String location, Double latitude, Double longitude, int responseCode, String notExistsAccountId
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

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(FastJsonStringIdKey accountKey) {
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
        return "FastJsonLoginHistory{" +
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
