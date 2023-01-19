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

    private static final long serialVersionUID = 877270220586583732L;

    private StringIdKey accountKey;
    private String password;
    private Map<String, String> extraParamMap;

    public LoginInfo() {
    }

    public LoginInfo(StringIdKey accountKey, String password, Map<String, String> extraParamMap) {
        this.accountKey = accountKey;
        this.password = password;
        this.extraParamMap = extraParamMap;
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

    public Map<String, String> getExtraParamMap() {
        return extraParamMap;
    }

    public void setExtraParamMap(Map<String, String> extraParamMap) {
        this.extraParamMap = extraParamMap;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
