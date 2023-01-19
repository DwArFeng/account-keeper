package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_account")
public class HibernateAccount implements Bean {

    private static final long serialVersionUID = -6894440316070438450L;

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

    @Column(name = "login_count", nullable = false)
    private int loginCount;

    @Column(name = "password_update_count", nullable = false)
    private int passwordUpdateCount;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = HibernateProtectorInfo.class, mappedBy = "account")
    private HibernateProtectorInfo protectorInfo;

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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getPasswordUpdateCount() {
        return passwordUpdateCount;
    }

    public void setPasswordUpdateCount(int passwordUpdateCount) {
        this.passwordUpdateCount = passwordUpdateCount;
    }

    public HibernateProtectorInfo getProtectorInfo() {
        return protectorInfo;
    }

    public void setProtectorInfo(HibernateProtectorInfo protectorInfo) {
        this.protectorInfo = protectorInfo;
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
                "loginCount = " + loginCount + ", " +
                "passwordUpdateCount = " + passwordUpdateCount + ")";
    }
}
