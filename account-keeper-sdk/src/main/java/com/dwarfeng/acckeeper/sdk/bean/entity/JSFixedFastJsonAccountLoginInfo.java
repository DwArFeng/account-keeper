package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.AccountLoginInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 账户登陆信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonAccountLoginInfo implements Bean {

    private static final long serialVersionUID = 3845315371817881122L;

    public static JSFixedFastJsonAccountLoginInfo of(AccountLoginInfo accountLoginInfo) {
        if (Objects.isNull(accountLoginInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonAccountLoginInfo(
                    FastJsonStringIdKey.of(accountLoginInfo.getKey()),
                    accountLoginInfo.getStateKeys().stream().map(JSFixedFastJsonLongIdKey::of)
                            .collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "state_keys", ordinal = 2)
    private List<JSFixedFastJsonLongIdKey> stateKeys;

    public JSFixedFastJsonAccountLoginInfo() {
    }

    public JSFixedFastJsonAccountLoginInfo(FastJsonStringIdKey key, List<JSFixedFastJsonLongIdKey> stateKeys) {
        this.key = key;
        this.stateKeys = stateKeys;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public List<JSFixedFastJsonLongIdKey> getStateKeys() {
        return stateKeys;
    }

    public void setStateKeys(List<JSFixedFastJsonLongIdKey> stateKeys) {
        this.stateKeys = stateKeys;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonAccountLoginInfo{" +
                "key=" + key +
                ", stateKeys=" + stateKeys +
                '}';
    }
}
