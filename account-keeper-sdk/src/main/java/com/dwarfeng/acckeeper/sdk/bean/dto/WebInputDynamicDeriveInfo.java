package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 动态派生信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputDynamicDeriveInfo implements Bean {

    @Serial
    private static final long serialVersionUID = -3866450136452231326L;

    public static DynamicDeriveInfo toStackBean(WebInputDynamicDeriveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new DynamicDeriveInfo(
                    WebInputStringIdKey.toStackBean(webInput.getLoginStateKey()),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "login_state_key")
    @NotNull
    @Valid
    private WebInputStringIdKey loginStateKey;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputDynamicDeriveInfo() {
    }

    public WebInputStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(WebInputStringIdKey loginStateKey) {
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
