package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 静态登录结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonStaticLoginResult implements Bean {

    private static final long serialVersionUID = -2752331359063265354L;

    public static FastJsonStaticLoginResult of(StaticLoginResult staticLoginResult) {
        if (Objects.isNull(staticLoginResult)) {
            return null;
        } else {
            return new FastJsonStaticLoginResult(
                    FastJsonStringIdKey.of(staticLoginResult.getLoginStateKey()),
                    FastJsonStringIdKey.of(staticLoginResult.getAccountKey()),
                    staticLoginResult.getExpireDate(),
                    staticLoginResult.getGeneratedDate(),
                    staticLoginResult.getType(),
                    staticLoginResult.getRemark()
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

    public FastJsonStaticLoginResult() {
    }

    public FastJsonStaticLoginResult(
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
        return "FastJsonStaticLoginResult{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", generatedDate=" + generatedDate +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
