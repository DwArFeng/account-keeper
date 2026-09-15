package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.TrustedStaticLoginInfo;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * WebInput 可信静态登录信息。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public class WebInputTrustedStaticLoginInfo implements Dto {

    @Serial
    private static final long serialVersionUID = -8615521848665602942L;

    public static TrustedStaticLoginInfo toStackBean(WebInputTrustedStaticLoginInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TrustedStaticLoginInfo(
                    WebInputStringIdKey.toStackBean(webInput.getAccountKey()),
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

    @JSONField(name = "expire_date")
    @NotNull
    @Future
    private Date expireDate;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "extra_params")
    private Map<String, String> extraParamMap;

    public WebInputTrustedStaticLoginInfo() {
    }

    public WebInputStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(WebInputStringIdKey accountKey) {
        this.accountKey = accountKey;
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
        return "WebInputTrustedStaticLoginInfo{" +
                "accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
