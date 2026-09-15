package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 账户注册信息。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class WebInputAccountRegisterInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -4808878042553786679L;

    public static AccountRegisterInfo toStackBean(WebInputAccountRegisterInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AccountRegisterInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()), webInput.getDisplayName(),
                    webInput.isEnabled(), webInput.getRemark(), webInput.getPassword()
            );
        }
    }

    @JSONField(name = "account_key")
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "display_name")
    @Length(max = Constraints.LENGTH_LABEL)
    private String displayName;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "password")
    @NotNull
    @NotEmpty
    private String password;

    public WebInputAccountRegisterInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WebInputAccountRegisterInfo{" +
                "accountKey=" + accountKey +
                ", displayName='" + displayName + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
