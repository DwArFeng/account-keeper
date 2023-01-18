package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.impl.bean.key.HibernateProtectorVariableKey;
import com.dwarfeng.acckeeper.impl.bean.key.HibernateRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    HibernateProtectorVariableKey protectorVariableKeyToHibernate(ProtectorVariableKey protectorVariableKey);

    @InheritInverseConfiguration
    ProtectorVariableKey protectorVariableKeyFromHibernate(HibernateProtectorVariableKey hibernateProtectorVariableKey);

    HibernateRecordKey recordKeyToHibernate(RecordKey recordKey);

    @InheritInverseConfiguration
    RecordKey recordKeyFromHibernate(HibernateRecordKey hibernateRecordKey);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "protectorInfo", ignore = true)
    @Mapping(target = "checkerInfo", ignore = true)
    HibernateAccount accountToHibernate(Account account);

    @InheritInverseConfiguration
    Account accountFromHibernate(HibernateAccount hibernateAccount);

    @Mapping(target = "protectDetailRecords", ignore = true)
    @Mapping(target = "notExistsAccountId", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "loginParamRecords", ignore = true)
    HibernateLoginHistory loginHistoryToHibernate(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromHibernate(HibernateLoginHistory hibernateLoginHistory);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "protectorVariables", ignore = true)
    @Mapping(target = "account", ignore = true)
    HibernateProtectorInfo protectorInfoToHibernate(ProtectorInfo protectorInfo);

    @InheritInverseConfiguration
    ProtectorInfo protectorInfoFromHibernate(HibernateProtectorInfo hibernateProtectorInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateProtectorSupport protectorSupportToHibernate(ProtectorSupport protectorSupport);

    @InheritInverseConfiguration
    ProtectorSupport protectorSupportFromHibernate(HibernateProtectorSupport hibernateProtectorSupport);

    @Mapping(target = "variableId", ignore = true)
    @Mapping(target = "protectorInfoId", ignore = true)
    @Mapping(target = "protectorInfo", ignore = true)
    HibernateProtectorVariable protectorVariableToHibernate(ProtectorVariable protectorVariable);

    @InheritInverseConfiguration
    ProtectorVariable protectorVariableFromHibernate(HibernateProtectorVariable hibernateProtectorVariable);

    @Mapping(target = "recordId", ignore = true)
    @Mapping(target = "loginHistoryId", ignore = true)
    @Mapping(target = "loginHistory", ignore = true)
    HibernateLoginParamRecord loginParamRecordToHibernate(LoginParamRecord loginParamRecord);

    @InheritInverseConfiguration
    LoginParamRecord loginParamRecordFromHibernate(HibernateLoginParamRecord hibernateLoginParamRecord);

    @Mapping(target = "recordId", ignore = true)
    @Mapping(target = "loginHistoryId", ignore = true)
    @Mapping(target = "loginHistory", ignore = true)
    HibernateProtectDetailRecord protectDetailRecordToHibernate(ProtectDetailRecord protectDetailRecord);

    @InheritInverseConfiguration
    ProtectDetailRecord protectDetailRecordFromHibernate(HibernateProtectDetailRecord hibernateProtectDetailRecord);

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "account", ignore = true)
    HibernateCheckerInfo checkerInfoToHibernate(CheckerInfo checkerInfo);

    @InheritInverseConfiguration
    CheckerInfo checkerInfoFromHibernate(HibernateCheckerInfo hibernateCheckerInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateCheckerSupport checkerSupportToHibernate(CheckerSupport checkerSupport);

    @InheritInverseConfiguration
    CheckerSupport checkerSupportFromHibernate(HibernateCheckerSupport hibernateCheckerSupport);
}
