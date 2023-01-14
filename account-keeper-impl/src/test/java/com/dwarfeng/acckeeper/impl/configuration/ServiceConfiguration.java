package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.AccountLoginInfo;
import com.dwarfeng.acckeeper.stack.cache.AccountCache;
import com.dwarfeng.acckeeper.stack.dao.AccountDao;
import com.dwarfeng.acckeeper.stack.dao.AccountLoginInfoDao;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final AccountCache accountCache;
    private final AccountDao accountDao;
    private final AccountLoginInfoDao accountLoginInfoDao;

    @Value("${cache.timeout.entity.account}")
    private long accountTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            AccountCache accountCache, AccountDao accountDao,
            AccountLoginInfoDao accountLoginInfoDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.accountCache = accountCache;
        this.accountDao = accountDao;
        this.accountLoginInfoDao = accountLoginInfoDao;
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, Account> accountGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                accountDao,
                accountCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                accountTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Account> accountDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                accountDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Account> accountDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                accountDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<StringIdKey, AccountLoginInfo> accountLoginInfoDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                accountLoginInfoDao,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AccountLoginInfo> accountLoginInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                accountLoginInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
