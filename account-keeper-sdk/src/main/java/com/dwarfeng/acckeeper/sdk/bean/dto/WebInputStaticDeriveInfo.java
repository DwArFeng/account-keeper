package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 静态派生信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputStaticDeriveInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 7740899079276743696L;

    public static StaticDeriveInfo toStackBean(WebInputStaticDeriveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new StaticDeriveInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey()),
                    webInput.getExpireDate(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputStringIdKey loginStateKey;

    @JSONField(name = "expire_date")
    @NotNull
    @Future
    private Date expireDate;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputStaticDeriveInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
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

    @Override
    public String toString() {
        return "WebInputStaticDeriveInfo{" +
                "loginStateKey=" + loginStateKey +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
