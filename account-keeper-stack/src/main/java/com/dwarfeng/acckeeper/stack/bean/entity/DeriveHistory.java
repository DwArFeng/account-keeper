package com.dwarfeng.acckeeper.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 派生历史。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class DeriveHistory implements Entity<LongIdKey> {

    private static final long serialVersionUID = -2792443703164136046L;

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;

    /**
     * 响应代码。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>通过</li>
     *     <li>登录状态不存在</li>
     *     <li>账户不存在</li>
     *     <li>账户被禁用</li>
     *     <li>序列版本不一致</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int responseCode;
    private String deriveRemark;

    public DeriveHistory() {
    }

    public DeriveHistory(LongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDeriveRemark() {
        return deriveRemark;
    }

    public void setDeriveRemark(String deriveRemark) {
        this.deriveRemark = deriveRemark;
    }

    @Override
    public String toString() {
        return "DeriveHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                '}';
    }
}
