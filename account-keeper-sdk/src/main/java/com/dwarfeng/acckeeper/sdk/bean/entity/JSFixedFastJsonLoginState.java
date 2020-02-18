package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.lang.NonNull;

public class JSFixedFastJsonLoginState implements Bean {

    private static final long serialVersionUID = 6905371931123253056L;

    public JSFixedFastJsonLoginState(JSFixedFastJsonLongIdKey key, FastJsonStringIdKey accountKey, long expireDate, long serialVersion) {
        this.key = key;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "expire_date", ordinal = 3)
    private long expireDate;

    @JSONField(name = "serial_version", ordinal = 4, serializeUsing = ToStringSerializer.class)
    private long serialVersion;

    public JSFixedFastJsonLoginState() {
    }

    public static JSFixedFastJsonLoginState of(@NonNull LoginState loginState) {
        return new JSFixedFastJsonLoginState(
                JSFixedFastJsonLongIdKey.of(loginState.getKey()),
                FastJsonStringIdKey.of(loginState.getAccountKey()),
                loginState.getExpireDate(),
                loginState.getSerialVersion()
        );
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

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonLoginState{" +
                "key=" + key +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                '}';
    }
}
