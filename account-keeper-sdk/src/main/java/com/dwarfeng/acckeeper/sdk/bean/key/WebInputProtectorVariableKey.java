package com.dwarfeng.acckeeper.sdk.bean.key;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 保护器变量主键。
 *
 * @author DwArFeng
 * @since 2.1.1
 */
public class WebInputProtectorVariableKey implements Bean {

    @Serial
    private static final long serialVersionUID = -6047015546370393266L;

    public static ProtectorVariableKey toStackBean(WebInputProtectorVariableKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ProtectorVariableKey(
                    webInput.getProtectorInfoId(),
                    webInput.getVariableId()
            );
        }
    }

    @JSONField(name = "protector_info_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID_COMMON)
    private String protectorInfoId;

    @JSONField(name = "variable_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_ID_COMMON)
    private String variableId;

    public WebInputProtectorVariableKey() {
    }

    public String getProtectorInfoId() {
        return protectorInfoId;
    }

    public void setProtectorInfoId(String protectorInfoId) {
        this.protectorInfoId = protectorInfoId;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        WebInputProtectorVariableKey that = (WebInputProtectorVariableKey) o;
        return Objects.equals(protectorInfoId, that.protectorInfoId) && Objects.equals(variableId, that.variableId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(protectorInfoId);
        result = 31 * result + Objects.hashCode(variableId);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputProtectorVariableKey{" +
                "protectorInfoId='" + protectorInfoId + '\'' +
                ", variableId='" + variableId + '\'' +
                '}';
    }
}
