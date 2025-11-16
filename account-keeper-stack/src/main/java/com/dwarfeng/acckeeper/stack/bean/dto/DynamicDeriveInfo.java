package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 动态派生信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DynamicDeriveInfo implements Dto {

    private static final long serialVersionUID = 8723618224234116047L;

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
