package com.dwarfeng.acckeeper.impl.bean.entity;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
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

    @Mapping(target = "stringId", ignore = true)
    @Mapping(target = "loginHistories", ignore = true)
    HibernateAccount accountToHibernate(Account account);

    @InheritInverseConfiguration
    Account accountFromHibernate(HibernateAccount hibernateAccount);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "accountStringId", ignore = true)
    @Mapping(target = "account", ignore = true)
    HibernateLoginHistory loginHistoryToHibernate(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromHibernate(HibernateLoginHistory hibernateLoginHistory);
}
