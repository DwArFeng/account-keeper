package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordUpdateInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 密码更新信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class WebInputPasswordUpdateInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 3431829851195191782L;

    public static PasswordUpdateInfo toStackBean(WebInputPasswordUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PasswordUpdateInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()), webInput.getOldPassword(),
                    webInput.getNewPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "old_password")
    @NotEmpty
    @NotNull
    private String oldPassword;

    @JSONField(name = "new_password")
    @NotEmpty
    @NotNull
    private String newPassword;

    public WebInputPasswordUpdateInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "WebInputPasswordUpdateInfo{" +
                "accountKey=" + accountKey +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
