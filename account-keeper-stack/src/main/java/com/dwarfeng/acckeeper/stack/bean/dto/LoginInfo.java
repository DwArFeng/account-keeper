package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Map;

/**
 * 登录信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class LoginInfo implements Dto {

    private static final long serialVersionUID = 6692597398353480845L;

    private StringIdKey accountKey;
    private String password;
    private Map<String, String> extraParams;

    public LoginInfo() {
    }

    public LoginInfo(StringIdKey accountKey, String password, Map<String, String> extraParams) {
        this.accountKey = accountKey;
        this.password = password;
        this.extraParams = extraParams;
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

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", extraParams=" + extraParams +
                '}';
    }
}
