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

    private static final long serialVersionUID = -2189790662526204525L;

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

    @Column(name = "response_code")
    private int responseCode;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    @Column(name = "alarm_level")
    private Integer alarmLevel;

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
                "responseCode = " + responseCode + ", " +
                "message = " + message + ", " +
                "alarmLevel = " + alarmLevel + ")";
    }
}
