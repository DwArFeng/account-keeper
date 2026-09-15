package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * WebInput 静态登录信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputStaticLoginInfo implements Dto {

    @Serial
    private static final long serialVersionUID = 6083323491817319400L;

    public static StaticLoginInfo toStackBean(WebInputStaticLoginInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new StaticLoginInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()),
                    webInput.getPassword(),
                    webInput.getExpireDate(),
                    webInput.getRemark(),
                    webInput.getExtraParamMap()
            );
        }
    }

    @JSONField(name = "account_key")
    @NotNull
    @Valid
    private WebInputStringIdKey accountKey;

    @JSONField(name = "password")
    @NotNull
    @NotEmpty
    private String password;

    @JSONField(name = "expire_date")
    @NotNull
    @Future
    private Date expireDate;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "extra_params")
    private Map<String, String> extraParamMap;

    public WebInputStaticLoginInfo() {
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getExtraParamMap() {
        return extraParamMap;
    }

    public void setExtraParamMap(Map<String, String> extraParamMap) {
        this.extraParamMap = extraParamMap;
    }

    @Override
    public String toString() {
        return "WebInputStaticLoginInfo{" +
                "accountKey=" + accountKey +
                ", password='" + password + '\'' +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
