package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Map;

/**
 * 动态登录信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class DynamicLoginInfo implements Dto {

    private static final long serialVersionUID = 8051290914672065800L;

    private StringIdKey accountKey;
    private String password;
    private String remark;
    private Map<String, String> extraParamMap;

    public DynamicLoginInfo() {
    }

    public DynamicLoginInfo(
            StringIdKey accountKey, String password, String remark, Map<String, String> extraParamMap
    ) {
        this.accountKey = accountKey;
        this.password = password;
        this.remark = remark;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getExtraParamMap() {
        return extraParamMap;
    }

    public void setExtraParamMap(Map<String, String> extraParamMap) {
        this.extraParamMap = extraParamMap;
    }

    @Override
    public String toString() {
        return "DynamicLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
