package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 账户更新信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class AccountUpdateInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -4597375653478140593L;

    private StringIdKey accountKey;
    private String displayName;
    private boolean enabled;
    private String remark;

    public AccountUpdateInfo() {
    }

    public AccountUpdateInfo(StringIdKey accountKey, String displayName, boolean enabled, String remark) {
        this.accountKey = accountKey;
        this.displayName = displayName;
        this.enabled = enabled;
        this.remark = remark;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "AccountUpdateInfo{" +
                "accountKey=" + accountKey +
                ", displayName='" + displayName + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
