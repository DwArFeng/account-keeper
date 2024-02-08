package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 动态派生信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class DynamicDeriveInfo implements Dto {

    private static final long serialVersionUID = 227752969395855365L;

    private LongIdKey loginStateKey;
    private String remark;

    public DynamicDeriveInfo() {
    }

    public DynamicDeriveInfo(LongIdKey loginStateKey, String remark) {
        this.loginStateKey = loginStateKey;
        this.remark = remark;
    }

    public LongIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(LongIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DynamicDeriveInfo{" +
                "loginStateKey=" + loginStateKey +
                ", remark='" + remark + '\'' +
                '}';
    }
}
