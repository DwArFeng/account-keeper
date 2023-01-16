package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

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

    private static final long serialVersionUID = 4060913062033577226L;

    public static LoginInfo toStackBean(WebInputLoginInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LoginInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()), webInput.getPassword(),
                    webInput.getIpAddress()
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

    @JSONField(name = "ip_address")
    @Length(max = Constraints.LENGTH_IP_ADDRESS)
    private String ipAddress;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "WebInputLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
