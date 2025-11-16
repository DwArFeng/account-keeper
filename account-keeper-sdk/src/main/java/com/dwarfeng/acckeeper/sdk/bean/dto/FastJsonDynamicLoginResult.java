package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 动态登录结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDynamicLoginResult implements Bean {

    private static final long serialVersionUID = -8120331166824516702L;

    public static FastJsonDynamicLoginResult of(DynamicLoginResult dynamicLoginResult) {
        if (Objects.isNull(dynamicLoginResult)) {
            return null;
        } else {
            return new FastJsonDynamicLoginResult(
                    FastJsonStringIdKey.of(dynamicLoginResult.getLoginStateKey()),
                    FastJsonStringIdKey.of(dynamicLoginResult.getAccountKey()),
                    dynamicLoginResult.getExpireDate(),
                    dynamicLoginResult.getGeneratedDate(),
                    dynamicLoginResult.getType(),
                    dynamicLoginResult.getRemark()
            );
        }
    }

    @JSONField(name = "login_state_key", ordinal = 1)
    private FastJsonStringIdKey loginStateKey;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    @JSONField(name = "generated_date", ordinal = 4)
    private Date generatedDate;

    @JSONField(name = "type", ordinal = 5)
    private int type;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public FastJsonDynamicLoginResult() {
    }

    public FastJsonDynamicLoginResult(
            FastJsonStringIdKey loginStateKey, FastJsonStringIdKey accountKey, Date expireDate, Date generatedDate,
            int type, String remark
    ) {
        this.loginStateKey = loginStateKey;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.generatedDate = generatedDate;
        this.type = type;
        this.remark = remark;
    }

    public FastJsonStringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(FastJsonStringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public FastJsonStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(FastJsonStringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonDynamicLoginResult{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", generatedDate=" + generatedDate +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
