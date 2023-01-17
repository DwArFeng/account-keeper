package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_account")
public class HibernateAccount implements Bean {

    private static final long serialVersionUID = 5067032688873624426L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", length = Constraints.LENGTH_ID, nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "password", columnDefinition = "CHAR(" + Constraints.LENGTH_PASSWORD + ")", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    @Column(name = "serial_version", nullable = false)
    private long serialVersion;

    @Column(name = "display_name", length = Constraints.LENGTH_LABEL)
    private String displayName;

    @Column(name = "registered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = HibernateProtectorInfo.class, mappedBy = "account")
    private HibernateProtectorInfo protectorInfo;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateLoginHistory.class, mappedBy = "account")
    private Set<HibernateLoginHistory> loginHistories = new HashSet<>();

    public HibernateAccount() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey uuidKey) {
        this.stringId = Optional.ofNullable(uuidKey).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public HibernateProtectorInfo getProtectorInfo() {
        return protectorInfo;
    }

    public void setProtectorInfo(HibernateProtectorInfo protectorInfo) {
        this.protectorInfo = protectorInfo;
    }

    public Set<HibernateLoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(Set<HibernateLoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "password = " + password + ", " +
                "enabled = " + enabled + ", " +
                "remark = " + remark + ", " +
                "serialVersion = " + serialVersion + ", " +
                "displayName = " + displayName + ", " +
                "registeredDate = " + registeredDate + ", " +
                "protectorInfo = " + protectorInfo + ")";
    }
}
