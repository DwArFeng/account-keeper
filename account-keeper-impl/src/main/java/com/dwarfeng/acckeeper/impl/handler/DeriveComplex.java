package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record DeriveComplex(
        StringIdKey loginStateKey, String loginStateId, Date happenedDate, int responseCode, StringIdKey accountKey,
        String accountId, Date expireDate, Long serialVersion, String deriveRemark, HandlerException exception,
        Account account
) {

    @Override
    public @NotNull String toString() {
        return "DeriveComplex{" +
                "loginStateKey=" + loginStateKey +
                ", loginStateId=" + loginStateId +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", accountKey=" + accountKey +
                ", accountId='" + accountId + '\'' +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", deriveRemark='" + deriveRemark + '\'' +
                ", exception=" + exception +
                ", account=" + account +
                '}';
    }
}
