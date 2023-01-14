package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;

/**
 * 账户登陆信息。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class AccountLoginInfo implements Entity<StringIdKey> {

    private static final long serialVersionUID = -6305074797592266863L;

    private StringIdKey key;
    private List<LongIdKey> stateKeys;

    public AccountLoginInfo() {
    }

    public AccountLoginInfo(StringIdKey key, List<LongIdKey> stateKeys) {
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
        return "AccountLoginInfo{" +
                "key=" + key +
                ", stateKeys=" + stateKeys +
                '}';
    }
}
