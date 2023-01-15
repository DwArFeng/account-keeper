package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_login_history")
public class HibernateLoginHistory implements Bean {

    private static final long serialVersionUID = 2737043418679462581L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "account_id", length = Constraints.LENGTH_ID)
    private String accountStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
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

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateAccount.class)
    @JoinColumns({ //
            @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateAccount account;

    public HibernateLoginHistory() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStringIdKey getAccountKey() {
        return Optional.ofNullable(accountStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setAccountKey(HibernateStringIdKey key) {
        this.accountStringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getAccountStringId() {
        return accountStringId;
    }

    public void setAccountStringId(String accountStringId) {
        this.accountStringId = accountStringId;
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

    public HibernateAccount getAccount() {
        return account;
    }

    public void setAccount(HibernateAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "accountStringId = " + accountStringId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "ipAddress = " + ipAddress + ", " +
                "location = " + location + ", " +
                "latitude = " + latitude + ", " +
                "longitude = " + longitude + ", " +
                "responseCode = " + responseCode + ", " +
                "account = " + account + ")";
    }
}
