package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 动态派生信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DynamicDeriveInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 7240593616411988960L;

    private StringIdKey loginStateKey;
    private String remark;

    public DynamicDeriveInfo() {
    }

    public DynamicDeriveInfo(StringIdKey loginStateKey, String remark) {
        this.loginStateKey = loginStateKey;
        this.remark = remark;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
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
