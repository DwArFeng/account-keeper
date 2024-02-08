package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * 静态派生信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputStaticDeriveInfo implements Dto {

    private static final long serialVersionUID = -1283528708505401844L;

    public static StaticDeriveInfo toStackBean(WebInputStaticDeriveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new StaticDeriveInfo(
                    WebInputLongIdKey.toStackBean(webInput.getLoginStateKey()),
                    webInput.getExpireDate(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputLongIdKey loginStateKey;

    @JSONField(name = "expire_date")
    @NotNull
    @Future
    private Date expireDate;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputStaticDeriveInfo() {
    }

    public WebInputLongIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputLongIdKey loginStateKey) {
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
