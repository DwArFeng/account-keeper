package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 登录信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class LoginInfo implements Dto {

    private static final long serialVersionUID = 5887544068143322835L;

    private StringIdKey accountKey;
    private String password;

    /**
     * @since 1.6.0
     */
    private String ipAddress;

    public LoginInfo() {
    }

    public LoginInfo(StringIdKey accountKey, String password, String ipAddress) {
        this.accountKey = accountKey;
        this.password = password;
        this.ipAddress = ipAddress;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
