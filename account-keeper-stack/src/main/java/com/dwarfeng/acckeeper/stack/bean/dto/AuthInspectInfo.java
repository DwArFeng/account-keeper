package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 授权查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class AuthInspectInfo implements Dto {

    private static final long serialVersionUID = -5822328197648452542L;

    private StringIdKey loginStateKey;

    public AuthInspectInfo() {
    }

    public AuthInspectInfo(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String toString() {
        return "AuthInspectInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
