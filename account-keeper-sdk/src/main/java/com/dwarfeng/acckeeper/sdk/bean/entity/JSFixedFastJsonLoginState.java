package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.lang.NonNull;

import java.util.Date;

public class JSFixedFastJsonLoginState implements Bean {

    private static final long serialVersionUID = -6834816955382548741L;

    public static JSFixedFastJsonLoginState of(@NonNull LoginState loginState) {
        return new JSFixedFastJsonLoginState(
                JSFixedFastJsonLongIdKey.of(loginState.getKey()),
                FastJsonStringIdKey.of(loginState.getAccountKey()),
                loginState.getExpireDate(),
                loginState.getSerialVersion()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    @JSONField(name = "serial_version", ordinal = 4, serializeUsing = ToStringSerializer.class)
    private long serialVersion;

    @JSONField(name = "generated_date", ordinal = 5)
    private Date generatedDate;

    @JSONField(name = "type", ordinal = 6)
    private int type;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    public JSFixedFastJsonLoginState() {
    }

    public JSFixedFastJsonLoginState(
            JSFixedFastJsonLongIdKey key, FastJsonStringIdKey accountKey, Date expireDate, long serialVersion
    ) {
        this.key = key;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
        return "JSFixedFastJsonLoginState{" +
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
