package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginAccountInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FastJson 登陆账户信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonLoginAccountInfo implements Bean {

    private static final long serialVersionUID = -7823870287562039525L;

    public static FastJsonLoginAccountInfo of(LoginAccountInfo loginAccountInfo) {
        if (Objects.isNull(loginAccountInfo)) {
            return null;
        } else {
            return new FastJsonLoginAccountInfo(
                    FastJsonStringIdKey.of(loginAccountInfo.getKey()),
                    loginAccountInfo.getStateKeys().stream().map(FastJsonLongIdKey::of).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "state_keys", ordinal = 2)
    private List<FastJsonLongIdKey> stateKeys;

    public FastJsonLoginAccountInfo() {
    }

    public FastJsonLoginAccountInfo(FastJsonStringIdKey key, List<FastJsonLongIdKey> stateKeys) {
        this.key = key;
        this.stateKeys = stateKeys;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public List<FastJsonLongIdKey> getStateKeys() {
        return stateKeys;
    }

    public void setStateKeys(List<FastJsonLongIdKey> stateKeys) {
        this.stateKeys = stateKeys;
    }

    @Override
    public String toString() {
        return "FastJsonLoginAccountInfo{" +
                "key=" + key +
                ", stateKeys=" + stateKeys +
                '}';
    }
}
