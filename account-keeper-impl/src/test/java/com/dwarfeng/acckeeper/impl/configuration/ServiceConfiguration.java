package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.impl.service.operation.AccountCrudOperation;
import com.dwarfeng.acckeeper.impl.service.operation.ProtectorInfoCrudOperation;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.cache.LoginHistoryCache;
import com.dwarfeng.acckeeper.stack.cache.ProtectorSupportCache;
import com.dwarfeng.acckeeper.stack.cache.ProtectorVariableCache;
import com.dwarfeng.acckeeper.stack.dao.*;
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
    private final ProtectorInfoCrudOperation protectorInfoCrudOperation;
    private final ProtectorInfoDao protectorInfoDao;
    private final ProtectorSupportDao protectorSupportDao;
    private final ProtectorSupportCache protectorSupportCache;
    private final ProtectorVariableDao protectorVariableDao;
    private final ProtectorVariableCache protectorVariableCache;

    @Value("${cache.timeout.entity.login_history}")
    private long loginHistoryTimeout;
    @Value("${cache.timeout.entity.protector_support}")
    private long protectorSupportTimeout;
    @Value("${cache.timeout.entity.protector_variable}")
    private long protectorVariableTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            KeyFetcher<LongIdKey> longIdKeyFetcher,
            AccountCrudOperation accountCrudOperation, AccountDao accountDao,
            LoginStateDao loginStateDao,
            LoginHistoryDao loginHistoryDao, LoginHistoryCache loginHistoryCache,
            ProtectorInfoCrudOperation protectorInfoCrudOperation, ProtectorInfoDao protectorInfoDao,
            ProtectorSupportDao protectorSupportDao, ProtectorSupportCache protectorSupportCache,
            ProtectorVariableDao protectorVariableDao, ProtectorVariableCache protectorVariableCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.longIdKeyFetcher = longIdKeyFetcher;
        this.accountCrudOperation = accountCrudOperation;
        this.accountDao = accountDao;
        this.loginStateDao = loginStateDao;
        this.loginHistoryDao = loginHistoryDao;
        this.loginHistoryCache = loginHistoryCache;
        this.protectorInfoCrudOperation = protectorInfoCrudOperation;
        this.protectorInfoDao = protectorInfoDao;
        this.protectorSupportDao = protectorSupportDao;
        this.protectorSupportCache = protectorSupportCache;
        this.protectorVariableDao = protectorVariableDao;
        this.protectorVariableCache = protectorVariableCache;
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

    @Bean
    public CustomBatchCrudService<StringIdKey, ProtectorInfo> protectorInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                protectorInfoCrudOperation,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorInfo> protectorInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                protectorInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorInfo> protectorInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                protectorInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ProtectorSupport> protectorSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                protectorSupportDao,
                protectorSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorSupport> protectorSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                protectorSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorSupport> protectorSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                protectorSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<ProtectorVariableKey, ProtectorVariable> protectorVariableCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                protectorVariableDao,
                protectorVariableCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorVariableTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorVariable> protectorVariableDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                protectorVariableDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorVariable> protectorVariableDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                protectorVariableDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
