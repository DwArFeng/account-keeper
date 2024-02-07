package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_login_state")
public class HibernateLoginState implements Bean {

    private static final long serialVersionUID = -6247985308318109744L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "account_id", length = Constraints.LENGTH_ID)
    private String accountStringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

    @Column(name = "serial_version")
    private long serialVersion;

    @Column(name = "generated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedDate;

    @Column(name = "type")
    private int type;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateAccount.class)
    @JoinColumns({ //
            @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateAccount account;

    public HibernateLoginState() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStringIdKey getAccountKey() {
        return Optional.ofNullable(accountStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setAccountKey(HibernateStringIdKey key) {
        this.accountStringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getAccountStringId() {
        return accountStringId;
    }

    public void setAccountStringId(String accountStringId) {
        this.accountStringId = accountStringId;
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

    public HibernateAccount getAccount() {
        return account;
    }

    public void setAccount(HibernateAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "accountStringId = " + accountStringId + ", " +
                "expireDate = " + expireDate + ", " +
                "serialVersion = " + serialVersion + ", " +
                "generatedDate = " + generatedDate + ", " +
                "type = " + type + ", " +
                "remark = " + remark + ", " +
                "account = " + account + ")";
    }
}
