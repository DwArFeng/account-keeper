package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 动态登录结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DynamicLoginResult implements Dto {

    private static final long serialVersionUID = -8354831125834458123L;

    private StringIdKey loginStateKey;
    private StringIdKey accountKey;
    private Date expireDate;
    private Date generatedDate;
    private int type;
    private String remark;

    public DynamicLoginResult() {
    }

    public DynamicLoginResult(
            StringIdKey loginStateKey, StringIdKey accountKey, Date expireDate, Date generatedDate, int type,
            String remark
    ) {
        this.loginStateKey = loginStateKey;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.generatedDate = generatedDate;
        this.type = type;
        this.remark = remark;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DynamicLoginResult{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", generatedDate=" + generatedDate +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
