package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 密码更新信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class PasswordUpdateInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -3454747538583425103L;

    private StringIdKey accountKey;
    private String oldPassword;
    private String newPassword;

    public PasswordUpdateInfo() {
    }

    public PasswordUpdateInfo(StringIdKey accountKey, String oldPassword, String newPassword) {
        this.accountKey = accountKey;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordUpdateInfo{" +
                "accountKey=" + accountKey +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
