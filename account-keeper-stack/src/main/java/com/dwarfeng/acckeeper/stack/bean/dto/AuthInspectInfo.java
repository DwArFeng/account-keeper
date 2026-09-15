package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 授权查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class AuthInspectInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -5070040565737931512L;

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
