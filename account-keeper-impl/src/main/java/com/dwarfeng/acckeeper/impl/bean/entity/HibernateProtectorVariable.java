package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.impl.bean.key.HibernateProtectorVariableKey;
import com.dwarfeng.acckeeper.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateProtectorVariableKey.class)
@Table(name = "tbl_protector_variable")
public class HibernateProtectorVariable implements Bean {

    private static final long serialVersionUID = -6545402618566570939L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "protector_info_id", length = Constraints.LENGTH_ID, nullable = false)
    private String protectorInfoId;

    @Id
    @Column(name = "variable_id", length = Constraints.LENGTH_ID, nullable = false)
    private String variableId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateProtectorInfo.class)
    @JoinColumns({ //
            @JoinColumn(name = "protector_info_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateProtectorInfo protectorInfo;

    public HibernateProtectorVariable() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateProtectorVariableKey getKey() {
        return new HibernateProtectorVariableKey(protectorInfoId, variableId);
    }

    public void setKey(HibernateProtectorVariableKey key) {
        if (Objects.isNull(key)) {
            this.protectorInfoId = null;
            this.variableId = null;
        } else {
            this.protectorInfoId = key.getProtectorInfoId();
            this.variableId = key.getVariableId();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getProtectorInfoId() {
        return protectorInfoId;
    }

    public void setProtectorInfoId(String protectorInfoId) {
        this.protectorInfoId = protectorInfoId;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateProtectorInfo getProtectorInfo() {
        return protectorInfo;
    }

    public void setProtectorInfo(HibernateProtectorInfo protectorInfo) {
        this.protectorInfo = protectorInfo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "protectorInfoId = " + protectorInfoId + ", " +
                "variableId = " + variableId + ", " +
                "value = " + value + ", " +
                "remark = " + remark + ", " +
                "protectorInfo = " + protectorInfo + ")";
    }
}
