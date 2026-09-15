package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 登录状态查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLoginStateLookupInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -3950984402673367994L;

    public static LoginStateLookupInfo toStackBean(WebInputLoginStateLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LoginStateLookupInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey()),
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey())
            );
        }
    }

    @JSONField(name = "login_state_key")
    @Valid
    private WebInputStringIdKey loginStateKey;

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    public WebInputLoginStateLookupInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    @Override
    public String toString() {
        return "WebInputLoginStateLookupInfo{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
