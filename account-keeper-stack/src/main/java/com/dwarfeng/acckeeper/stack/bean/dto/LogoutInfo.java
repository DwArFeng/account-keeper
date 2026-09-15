package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 登出信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LogoutInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 4821890849096634743L;

    private StringIdKey loginStateKey;

    public LogoutInfo() {
    }

    public LogoutInfo(StringIdKey loginStateKey) {
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
        return "LogoutInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
