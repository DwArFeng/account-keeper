package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.AuthInspectInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 授权查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputAuthInspectInfo implements Bean {

    private static final long serialVersionUID = -7280494252030231840L;

    public static AuthInspectInfo toStackBean(WebInputAuthInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuthInspectInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey())
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputStringIdKey loginStateKey;

    public WebInputAuthInspectInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    @Override
    public String toString() {
        return "WebInputAuthInspectInfo{" +
                "loginStateKey=" + loginStateKey +
                '}';
    }
}
