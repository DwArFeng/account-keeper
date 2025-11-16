package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 静态派生信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class StaticDeriveInfo implements Dto {

    private static final long serialVersionUID = 8392704538469567924L;

    private StringIdKey loginStateKey;
    private Date expireDate;
    private String remark;

    public StaticDeriveInfo() {
    }

    public StaticDeriveInfo(StringIdKey loginStateKey, Date expireDate, String remark) {
        this.loginStateKey = loginStateKey;
        this.expireDate = expireDate;
        this.remark = remark;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
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

    @Override
    public String toString() {
        return "StaticDeriveInfo{" +
                "loginStateKey=" + loginStateKey +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
