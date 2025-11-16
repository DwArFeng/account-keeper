package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.KickInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import java.util.Objects;

/**
 * WebInput 踢出信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputKickInfo implements Bean {

    private static final long serialVersionUID = 3304900673222023129L;

    public static KickInfo toStackBean(WebInputKickInfo webInputKickInfo) {
        if (Objects.isNull(webInputKickInfo)) {
            return null;
        } else {
            return new KickInfo(
                    WebInputStringIdKey.toStackBean(webInputKickInfo.getLoginStateKey()),
                    WebInputStringIdKey.toStackBean(webInputKickInfo.getAccountKey())
            );
        }
    }

    @JSONField(name = "login_state_key")
    @Valid
    private WebInputStringIdKey loginStateKey;

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    public WebInputKickInfo() {
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
        return "WebInputKickInfo{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
