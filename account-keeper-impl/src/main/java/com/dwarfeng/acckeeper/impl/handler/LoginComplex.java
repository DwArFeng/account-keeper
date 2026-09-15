package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;

public record LoginComplex(
        StringIdKey accountKey, String accountId, Date happenedDate, int responseCode, String message,
        Integer alarmLevel, Map<String, String> extraParams, Map<String, String> protectDetail, Date expireDate,
        long serialVersion, String loginRemark, HandlerException exception, Account account
) {

    @Override
    public @NotNull String toString() {
        return "LoginComplex{" +
                "accountKey=" + accountKey +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", extraParams=" + extraParams +
                ", protectDetail=" + protectDetail +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", loginRemark='" + loginRemark + '\'' +
                ", exception=" + exception +
                ", account=" + account +
                '}';
    }
}
