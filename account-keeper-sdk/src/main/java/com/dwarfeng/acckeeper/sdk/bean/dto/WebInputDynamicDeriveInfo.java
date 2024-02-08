package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 动态派生信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputDynamicDeriveInfo implements Dto {

    private static final long serialVersionUID = -539133524453563860L;

    public static DynamicDeriveInfo toStackBean(WebInputDynamicDeriveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new DynamicDeriveInfo(
                    WebInputLongIdKey.toStackBean(webInput.getLoginStateKey()),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputLongIdKey loginStateKey;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputDynamicDeriveInfo() {
    }

    public WebInputLongIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputLongIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputDynamicDeriveInfo{" +
                "loginStateKey=" + loginStateKey +
                ", remark='" + remark + '\'' +
                '}';
    }
}
