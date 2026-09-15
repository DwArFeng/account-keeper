package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.util.Objects;

/**
 * WebInput 保护器信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class WebInputProtectorInfo implements Bean {

    @Serial
    private static final long serialVersionUID = 6968439712148870650L;

    public static ProtectorInfo toStackBean(WebInputProtectorInfo webInputProtectorInfo) {
        if (Objects.isNull(webInputProtectorInfo)) {
            return null;
        } else {
            return new ProtectorInfo(
                    WebInputStringIdKey.toStackBean(webInputProtectorInfo.getKey()),
                    webInputProtectorInfo.getType(), webInputProtectorInfo.getParam(),
                    webInputProtectorInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputStringIdKey key;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "param")
    private String param;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputProtectorInfo() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputProtectorInfo{" +
                "key=" + key +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
