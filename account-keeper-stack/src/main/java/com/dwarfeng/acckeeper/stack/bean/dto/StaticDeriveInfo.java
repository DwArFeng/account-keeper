package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 静态派生信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class StaticDeriveInfo implements Dto {

    private static final long serialVersionUID = 5574065167380433930L;

    private LongIdKey loginStateKey;
    private Date expireDate;
    private String remark;

    public StaticDeriveInfo() {
    }

    public StaticDeriveInfo(LongIdKey loginStateKey, Date expireDate, String remark) {
        this.loginStateKey = loginStateKey;
        this.expireDate = expireDate;
        this.remark = remark;
    }

    public LongIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(LongIdKey loginStateKey) {
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
