package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.LogoutInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 登出信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLogoutInfo implements Bean {

    private static final long serialVersionUID = -2099235470423246703L;

    public static LogoutInfo toStackBean(WebInputLogoutInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LogoutInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey())
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputStringIdKey loginStateKey;

    public WebInputLogoutInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String toString() {
        return "WebInputLogoutInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
