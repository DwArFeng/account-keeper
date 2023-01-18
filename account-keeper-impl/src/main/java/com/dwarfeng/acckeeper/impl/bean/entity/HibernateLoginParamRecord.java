package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.impl.bean.key.HibernateRecordKey;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateRecordKey.class)
@Table(name = "tbl_login_param_record")
public class HibernateLoginParamRecord implements Bean {

    private static final long serialVersionUID = 3797101400367142892L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "login_history_id", nullable = false)
    private Long loginHistoryId;

    @Id
    @Column(name = "record_id", length = Constraints.LENGTH_ID, nullable = false)
    private String recordId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateLoginHistory.class)
    @JoinColumns({ //
            @JoinColumn(name = "login_history_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateLoginHistory loginHistory;

    public HibernateLoginParamRecord() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateRecordKey getKey() {
        return new HibernateRecordKey(loginHistoryId, recordId);
    }

    public void setKey(HibernateRecordKey key) {
        if (Objects.isNull(key)) {
            this.loginHistoryId = null;
            this.recordId = null;
        } else {
            this.loginHistoryId = key.getLoginHistoryId();
            this.recordId = key.getRecordId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(Long loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HibernateLoginHistory getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(HibernateLoginHistory loginHistory) {
        this.loginHistory = loginHistory;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "loginHistoryId = " + loginHistoryId + ", " +
                "recordId = " + recordId + ", " +
                "value = " + value + ", " +
                "loginHistory = " + loginHistory + ")";
    }
}
