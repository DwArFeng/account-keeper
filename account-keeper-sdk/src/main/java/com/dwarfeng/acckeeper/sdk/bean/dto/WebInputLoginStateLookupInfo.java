package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import java.util.Objects;

/**
 * WebInput 登录状态查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLoginStateLookupInfo implements Dto {

    private static final long serialVersionUID = 4957257882887978641L;

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
