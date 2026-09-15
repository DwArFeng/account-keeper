package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;

import java.io.Serial;

/**
 * 延期信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PostponeInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 1487025128425298619L;

    private StringIdKey loginStateKey;

    public PostponeInfo() {
    }

    public PostponeInfo(StringIdKey loginStateKey) {
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
        return "PostponeInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
