package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_login_history")
public class HibernateLoginHistory implements Bean {

    private static final long serialVersionUID = -5898451848589805939L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "account_id", length = Constraints.LENGTH_ID)
    private String accountId;

    @Column(name = "happened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "ip_address", length = Constraints.LENGTH_IP_ADDRESS)
    private String ipAddress;

    @Column(name = "location", length = Constraints.LENGTH_LOCATION)
    private String location;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "response_code")
    private int responseCode;

    @Column(name = "not_exists_account_id", length = Constraints.LENGTH_ID)
    private String notExistsAccountId;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateLoginParamRecord.class, mappedBy = "loginHistory")
    private Set<HibernateLoginParamRecord> loginParamRecords = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateProtectDetailRecord.class, mappedBy = "loginHistory")
    private Set<HibernateProtectDetailRecord> protectDetailRecords = new HashSet<>();

    public HibernateLoginHistory() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
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

    public String getNotExistsAccountId() {
        return notExistsAccountId;
    }

    public void setNotExistsAccountId(String notExistsAccountId) {
        this.notExistsAccountId = notExistsAccountId;
    }

    public Set<HibernateLoginParamRecord> getLoginParamRecords() {
        return loginParamRecords;
    }

    public void setLoginParamRecords(Set<HibernateLoginParamRecord> loginParamRecords) {
        this.loginParamRecords = loginParamRecords;
    }

    public Set<HibernateProtectDetailRecord> getProtectDetailRecords() {
        return protectDetailRecords;
    }

    public void setProtectDetailRecords(Set<HibernateProtectDetailRecord> protectDetailRecords) {
        this.protectDetailRecords = protectDetailRecords;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "accountId = " + accountId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "ipAddress = " + ipAddress + ", " +
                "location = " + location + ", " +
                "latitude = " + latitude + ", " +
                "longitude = " + longitude + ", " +
                "responseCode = " + responseCode + ", " +
                "notExistsAccountId = " + notExistsAccountId + ")";
    }
}
