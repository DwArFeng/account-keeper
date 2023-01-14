package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginAccountInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 登陆账户信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class JSFixedFastJsonLoginAccountInfo implements Bean {

    private static final long serialVersionUID = 4068579611450922591L;

    public static JSFixedFastJsonLoginAccountInfo of(LoginAccountInfo loginAccountInfo) {
        if (Objects.isNull(loginAccountInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonLoginAccountInfo(
                    FastJsonStringIdKey.of(loginAccountInfo.getKey()),
                    loginAccountInfo.getStateKeys().stream().map(JSFixedFastJsonLongIdKey::of)
                            .collect(Collectors.toList()),
                    loginAccountInfo.getStateCount()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "state_keys", ordinal = 2)
    private List<JSFixedFastJsonLongIdKey> stateKeys;

    @JSONField(name = "state_count", ordinal = 3)
    private int stateCount;

    public JSFixedFastJsonLoginAccountInfo() {
    }

    public JSFixedFastJsonLoginAccountInfo(FastJsonStringIdKey key, List<JSFixedFastJsonLongIdKey> stateKeys, int stateCount) {
        this.key = key;
        this.stateKeys = stateKeys;
        this.stateCount = stateCount;
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

    public int getStateCount() {
        return stateCount;
    }

    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonLoginAccountInfo{" +
                "key=" + key +
                ", stateKeys=" + stateKeys +
                ", stateCount=" + stateCount +
                '}';
    }
}
