package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordCheckInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 密码检查信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class WebInputPasswordCheckInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 4209831811773560805L;

    public static PasswordCheckInfo toStackBean(WebInputPasswordCheckInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PasswordCheckInfo(
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

    public WebInputPasswordCheckInfo() {
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
        return "WebInputPasswordCheckInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                '}';
    }
}
