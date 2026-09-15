package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordResetInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * 密码重置信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class WebInputPasswordResetInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -6440884465502289139L;

    public static PasswordResetInfo toStackBean(WebInputPasswordResetInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PasswordResetInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()), webInput.getNewPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "new_password")
    @NotEmpty
    @NotNull
    private String newPassword;

    public WebInputPasswordResetInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "WebInputPasswordResetInfo{" +
                "accountKey=" + accountKey +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
