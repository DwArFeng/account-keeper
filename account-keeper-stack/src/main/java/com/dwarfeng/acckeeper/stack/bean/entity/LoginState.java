package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 登录状态。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class LoginState implements Entity<LongIdKey> {

    private static final long serialVersionUID = 8777916898130775988L;

    private LongIdKey key;
    private StringIdKey accountKey;
    private Date expireDate;
    private long serialVersion;

    /**
     * 生成日期。
     *
     * @since 1.7.0
     */
    private Date generatedDate;

    /**
     * 类型。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>正常</li>
     *     <li>长期</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     *
     * @since 1.7.0
     */
    private int type;

    /**
     * 备注。
     *
     * @since 1.7.0
     */
    private String remark;

    public LoginState() {
    }

    public LoginState(
            LongIdKey key, StringIdKey accountKey, Date expireDate, long serialVersion, Date generatedDate,
            int type, String remark
    ) {
        this.key = key;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
        this.generatedDate = generatedDate;
        this.type = type;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
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
        return "LoginState{" +
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
