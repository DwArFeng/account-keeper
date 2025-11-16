package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 登出信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LogoutInfo implements Dto {

    private static final long serialVersionUID = 6808787984890671070L;

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
