package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;
import com.dwarfeng.subgrade.data.sdk.bean.key.hibernate.HibernateLongIdKey;
import jakarta.persistence.*;

import java.io.Serial;
import java.time.Instant;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_derive_history")
public class HibernateDeriveHistory implements Bean {

    @Serial
    private static final long serialVersionUID = 477524972029669232L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 主属性字段

    @Column(name = "account_id", length = Constraints.LENGTH_ID_COMMON)
    private String accountId;

    @Column(name = "happened_date")
    private Instant happenedDate;

    @Column(name = "response_code")
    private int responseCode;

    @Column(name = "derive_remark", length = Constraints.LENGTH_REMARK)
    private String deriveRemark;

    public HibernateDeriveHistory() {
    }

    // endregion

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Instant getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Instant happenedDate) {
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

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "accountId = " + accountId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "responseCode = " + responseCode + ", " +
                "deriveRemark = " + deriveRemark + ")";
    }
}
