package com.dwarfeng.acckeeper.impl.bean;

import com.dwarfeng.acckeeper.impl.bean.entity.*;
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
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>impl</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    // -----------------------------------------------------------Acckeeper Key-----------------------------------------------------------
    HibernateProtectorVariableKey protectorVariableKeyToHibernate(ProtectorVariableKey protectorVariableKey);

    @InheritInverseConfiguration
    ProtectorVariableKey protectorVariableKeyFromHibernate(HibernateProtectorVariableKey hibernateProtectorVariableKey);

    HibernateRecordKey recordKeyToHibernate(RecordKey recordKey);

    @InheritInverseConfiguration
    RecordKey recordKeyFromHibernate(HibernateRecordKey hibernateRecordKey);

    // -----------------------------------------------------------Acckeeper Entity-----------------------------------------------------------
    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "protectorInfo", ignore = true)
    @Mapping(target = "loginStates", ignore = true)
    HibernateAccount accountToHibernate(Account account);

    @InheritInverseConfiguration
    Account accountFromHibernate(HibernateAccount hibernateAccount);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "accountStringId", ignore = true)
    @Mapping(target = "account", ignore = true)
    HibernateLoginState loginStateToHibernate(LoginState loginState);

    @InheritInverseConfiguration
    LoginState loginStateFromHibernate(HibernateLoginState hibernateLoginState);

    @Mapping(target = "protectDetailRecords", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "loginParamRecords", ignore = true)
    HibernateLoginHistory loginHistoryToHibernate(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromHibernate(HibernateLoginHistory hibernateLoginHistory);

    @Mapping(target = "modifiedDatamark", ignore = true)
    @Mapping(target = "createdDatamark", ignore = true)
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

    @Mapping(target = "longId", ignore = true)
    HibernateDeriveHistory deriveHistoryToHibernate(DeriveHistory deriveHistory);

    @InheritInverseConfiguration
    DeriveHistory deriveHistoryFromHibernate(HibernateDeriveHistory hibernateDeriveHistory);
}
