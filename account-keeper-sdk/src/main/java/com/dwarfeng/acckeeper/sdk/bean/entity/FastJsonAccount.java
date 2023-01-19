package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.lang.NonNull;

import java.util.Date;

public class FastJsonAccount implements Bean {

    private static final long serialVersionUID = 1782876032660642485L;

    public static FastJsonAccount of(@NonNull Account account) {
        return new FastJsonAccount(
                FastJsonStringIdKey.of(account.getKey()),
                account.getPassword(), account.isEnabled(), account.getRemark(), account.getSerialVersion(),
                account.getDisplayName(), account.getRegisteredDate(), account.getLoginCount()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "password", ordinal = 2)
    private String password;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "serial_version", ordinal = 5)
    private long serialVersion;

    @JSONField(name = "display_name", ordinal = 6)
    private String displayName;

    @JSONField(name = "registered_date", ordinal = 7)
    private Date registeredDate;

    @JSONField(name = "login_count", ordinal = 8)
    private int loginCount;

    @JSONField(name = "password_update_count", ordinal = 9)
    private int passwordUpdateCount;

    public FastJsonAccount() {
    }

    public FastJsonAccount(
            FastJsonStringIdKey key, String password, boolean enabled, String remark, long serialVersion,
            String displayName, Date registeredDate, int loginCount
    ) {
        this.key = key;
        this.password = password;
        this.enabled = enabled;
        this.remark = remark;
        this.serialVersion = serialVersion;
        this.displayName = displayName;
        this.registeredDate = registeredDate;
        this.loginCount = loginCount;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
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

    @Override
    public String toString() {
        return "FastJsonAccount{" +
                "key=" + key +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", serialVersion=" + serialVersion +
                ", displayName='" + displayName + '\'' +
                ", registeredDate=" + registeredDate +
                ", loginCount=" + loginCount +
                ", passwordUpdateCount=" + passwordUpdateCount +
                '}';
    }
}
