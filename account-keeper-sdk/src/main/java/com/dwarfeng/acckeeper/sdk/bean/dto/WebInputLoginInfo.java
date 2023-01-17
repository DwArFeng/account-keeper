package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 登录信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class WebInputLoginInfo implements Dto {

    private static final long serialVersionUID = 3355147425243452105L;

    public static LoginInfo toStackBean(WebInputLoginInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LoginInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()), webInput.getPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "password")
    @NotEmpty
    @NotNull
    private String password;

    public WebInputLoginInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WebInputLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                '}';
    }
}
