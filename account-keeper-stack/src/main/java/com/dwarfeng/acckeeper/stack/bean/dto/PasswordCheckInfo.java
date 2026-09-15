package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 密码检查信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class PasswordCheckInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -6791639770623519833L;

    private StringIdKey accountKey;
    private String password;

    public PasswordCheckInfo() {
    }

    public PasswordCheckInfo(StringIdKey accountKey, String password) {
        this.accountKey = accountKey;
        this.password = password;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordCheckInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                '}';
    }
}
