package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 登录状态。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FastJsonLoginState implements Bean {

    private static final long serialVersionUID = -5531750506393067569L;

    public static FastJsonLoginState of(LoginState loginState) {
        if (Objects.isNull(loginState)) {
            return null;
        } else {
            return new FastJsonLoginState(
                    FastJsonLongIdKey.of(loginState.getKey()),
                    FastJsonStringIdKey.of(loginState.getAccountKey()),
                    loginState.getExpireDate(),
                    loginState.getSerialVersion(),
                    loginState.getGeneratedDate(),
                    loginState.getType(),
                    loginState.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    @JSONField(name = "serial_version", ordinal = 4)
    private long serialVersion;

    @JSONField(name = "generated_date", ordinal = 5)
    private Date generatedDate;

    @JSONField(name = "type", ordinal = 6)
    private int type;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    public FastJsonLoginState() {
    }

    public FastJsonLoginState(
            FastJsonLongIdKey key, FastJsonStringIdKey accountKey, Date expireDate, long serialVersion,
            Date generatedDate, int type, String remark
    ) {
        this.key = key;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
        this.generatedDate = generatedDate;
        this.type = type;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
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

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
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
        return "FastJsonLoginState{" +
                "key=" + key +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", generatedDate=" + generatedDate +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
