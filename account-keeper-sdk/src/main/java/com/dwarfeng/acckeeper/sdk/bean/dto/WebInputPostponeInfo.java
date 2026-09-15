package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.PostponeInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 延期信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputPostponeInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 1638086171642526096L;

    public static PostponeInfo toStackBean(WebInputPostponeInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PostponeInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey())
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputStringIdKey loginStateKey;

    public WebInputPostponeInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String toString() {
        return "WebInputPostponeInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
