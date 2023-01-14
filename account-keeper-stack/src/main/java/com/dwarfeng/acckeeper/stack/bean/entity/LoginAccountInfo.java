package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;

/**
 * 登陆账户信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class LoginAccountInfo implements Entity<StringIdKey> {

    private static final long serialVersionUID = 4079244778592976456L;

    private StringIdKey key;
    private List<LongIdKey> stateKeys;

    public LoginAccountInfo() {
    }

    public LoginAccountInfo(StringIdKey key, List<LongIdKey> stateKeys) {
        this.key = key;
        this.stateKeys = stateKeys;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public List<LongIdKey> getStateKeys() {
        return stateKeys;
    }

    public void setStateKeys(List<LongIdKey> stateKeys) {
        this.stateKeys = stateKeys;
    }

    @Override
    public String toString() {
        return "LoginAccountInfo{" +
                "key=" + key +
                ", stateKeys=" + stateKeys +
                '}';
    }
}
