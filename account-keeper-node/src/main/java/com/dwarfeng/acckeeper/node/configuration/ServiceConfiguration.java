package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.impl.service.operation.AccountCrudOperation;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.cache.LoginHistoryCache;
import com.dwarfeng.acckeeper.stack.dao.AccountDao;
import com.dwarfeng.acckeeper.stack.dao.LoginHistoryDao;
import com.dwarfeng.acckeeper.stack.dao.LoginStateDao;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.*;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final KeyFetcher<LongIdKey> longIdKeyFetcher;

    private final AccountCrudOperation accountCrudOperation;
    private final AccountDao accountDao;
    private final LoginStateDao loginStateDao;
    private final LoginHistoryDao loginHistoryDao;
    private final LoginHistoryCache loginHistoryCache;

    @Value("${cache.timeout.entity.login_history}")
    private long loginHistoryTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            KeyFetcher<LongIdKey> longIdKeyFetcher,
            AccountCrudOperation accountCrudOperation, AccountDao accountDao,
            LoginStateDao loginStateDao,
            LoginHistoryDao loginHistoryDao, LoginHistoryCache loginHistoryCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.longIdKeyFetcher = longIdKeyFetcher;
        this.accountCrudOperation = accountCrudOperation;
        this.accountDao = accountDao;
        this.loginStateDao = loginStateDao;
        this.loginHistoryDao = loginHistoryDao;
        this.loginHistoryCache = loginHistoryCache;
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, Account> accountCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                accountCrudOperation,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
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
    public DaoOnlyBatchCrudService<LongIdKey, LoginState> loginStateDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                loginStateDao,
                longIdKeyFetcher,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginState> loginStateDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                loginStateDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginState> loginStateDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                loginStateDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, LoginHistory> loginHistoryCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                loginHistoryDao,
                loginHistoryCache,
                longIdKeyFetcher,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginHistoryTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginHistory> loginHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                loginHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginHistory> loginHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                loginHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
