package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Map;

/**
 * 静态登录信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class StaticLoginInfo implements Dto {

    private static final long serialVersionUID = -7654855085347168601L;

    private StringIdKey accountKey;
    private String password;
    private Date expireDate;
    private String remark;
    private Map<String, String> extraParamMap;

    public StaticLoginInfo() {
    }

    public StaticLoginInfo(
            StringIdKey accountKey, String password, Date expireDate, String remark, Map<String, String> extraParamMap
    ) {
        this.accountKey = accountKey;
        this.password = password;
        this.expireDate = expireDate;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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
        return "StaticLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
