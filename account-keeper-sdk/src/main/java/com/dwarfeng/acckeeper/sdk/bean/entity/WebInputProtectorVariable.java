package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.key.WebInputProtectorVariableKey;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 保护器变量。
 *
 * @author DwArFeng
 * @since 2.1.1
 */
public class WebInputProtectorVariable implements Bean {

    @Serial
    private static final long serialVersionUID = -2455743524825582290L;

    public static ProtectorVariable toStackBean(WebInputProtectorVariable webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ProtectorVariable(
                    WebInputProtectorVariableKey.toStackBean(webInput.getKey()),
                    webInput.getValue(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputProtectorVariableKey key;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputProtectorVariable() {
    }

    public WebInputProtectorVariableKey getKey() {
        return key;
    }

    public void setKey(WebInputProtectorVariableKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputProtectorVariable{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
