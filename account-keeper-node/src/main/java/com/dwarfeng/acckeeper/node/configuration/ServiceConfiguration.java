package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.impl.service.operation.AccountCrudOperation;
import com.dwarfeng.acckeeper.impl.service.operation.LoginHistoryCrudOperation;
import com.dwarfeng.acckeeper.impl.service.operation.ProtectorInfoCrudOperation;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.cache.*;
import com.dwarfeng.acckeeper.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;
    private final GenerateConfiguration generateConfiguration;

    private final AccountCrudOperation accountCrudOperation;
    private final AccountDao accountDao;
    private final LoginStateDao loginStateDao;
    private final LoginStateCache loginStateCache;
    private final LoginHistoryCrudOperation loginHistoryCrudOperation;
    private final LoginHistoryDao loginHistoryDao;
    private final ProtectorInfoCrudOperation protectorInfoCrudOperation;
    private final ProtectorInfoDao protectorInfoDao;
    private final ProtectorSupportDao protectorSupportDao;
    private final ProtectorSupportCache protectorSupportCache;
    private final ProtectorVariableDao protectorVariableDao;
    private final ProtectorVariableCache protectorVariableCache;
    private final LoginParamRecordDao loginParamRecordDao;
    private final LoginParamRecordCache loginParamRecordCache;
    private final ProtectDetailRecordDao protectDetailRecordDao;
    private final ProtectDetailRecordCache protectDetailRecordCache;
    private final DeriveHistoryDao deriveHistoryDao;
    private final DeriveHistoryCache deriveHistoryCache;

    @Value("${cache.timeout.entity.login_state}")
    private long loginStateTimeout;
    @Value("${cache.timeout.entity.protector_support}")
    private long protectorSupportTimeout;
    @Value("${cache.timeout.entity.protector_variable}")
    private long protectorVariableTimeout;
    @Value("${cache.timeout.entity.login_param_record}")
    private long loginParamRecordTimeout;
    @Value("${cache.timeout.entity.protect_detail_record}")
    private long protectDetailRecordTimeout;
    @Value("${cache.timeout.entity.derive_history}")
    private long deriveHistoryTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            GenerateConfiguration generateConfiguration,
            AccountCrudOperation accountCrudOperation,
            AccountDao accountDao,
            LoginStateDao loginStateDao,
            LoginStateCache loginStateCache,
            LoginHistoryCrudOperation loginHistoryCrudOperation,
            LoginHistoryDao loginHistoryDao,
            ProtectorInfoCrudOperation protectorInfoCrudOperation,
            ProtectorInfoDao protectorInfoDao,
            ProtectorSupportDao protectorSupportDao,
            ProtectorSupportCache protectorSupportCache,
            ProtectorVariableDao protectorVariableDao,
            ProtectorVariableCache protectorVariableCache,
            LoginParamRecordDao loginParamRecordDao,
            LoginParamRecordCache loginParamRecordCache,
            ProtectDetailRecordDao protectDetailRecordDao,
            ProtectDetailRecordCache protectDetailRecordCache,
            DeriveHistoryDao deriveHistoryDao,
            DeriveHistoryCache deriveHistoryCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.generateConfiguration = generateConfiguration;
        this.accountCrudOperation = accountCrudOperation;
        this.accountDao = accountDao;
        this.loginStateDao = loginStateDao;
        this.loginStateCache = loginStateCache;
        this.loginHistoryCrudOperation = loginHistoryCrudOperation;
        this.loginHistoryDao = loginHistoryDao;
        this.protectorInfoCrudOperation = protectorInfoCrudOperation;
        this.protectorInfoDao = protectorInfoDao;
        this.protectorSupportDao = protectorSupportDao;
        this.protectorSupportCache = protectorSupportCache;
        this.protectorVariableDao = protectorVariableDao;
        this.protectorVariableCache = protectorVariableCache;
        this.loginParamRecordDao = loginParamRecordDao;
        this.loginParamRecordCache = loginParamRecordCache;
        this.protectDetailRecordDao = protectDetailRecordDao;
        this.protectDetailRecordCache = protectDetailRecordCache;
        this.deriveHistoryDao = deriveHistoryDao;
        this.deriveHistoryCache = deriveHistoryCache;
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, Account> accountCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                accountCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Account> accountDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                accountDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Account> accountDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                accountDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, LoginState> loginStateGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginStateDao,
                loginStateCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                loginStateTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginState> loginStateDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginStateDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginState> loginStateDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginStateDao
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, LoginHistory> loginHistoryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginHistoryCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginHistory> loginHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginHistoryDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginHistory> loginHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginHistoryDao
        );
    }

    @Bean
    public CustomBatchCrudService<StringIdKey, ProtectorInfo> protectorInfoCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorInfoCrudOperation,
                new ExceptionKeyGenerator<>()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorInfo> protectorInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorInfoDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorInfo> protectorInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorInfoDao
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, ProtectorSupport> protectorSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorSupportDao,
                protectorSupportCache,
                new ExceptionKeyGenerator<>(),
                protectorSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorSupport> protectorSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorSupportDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorSupport> protectorSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorSupportDao
        );
    }

    @Bean
    public GeneralBatchCrudService<ProtectorVariableKey, ProtectorVariable> protectorVariableCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorVariableDao,
                protectorVariableCache,
                new ExceptionKeyGenerator<>(),
                protectorVariableTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectorVariable> protectorVariableDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorVariableDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectorVariable> protectorVariableDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectorVariableDao
        );
    }

    @Bean
    public GeneralBatchCrudService<RecordKey, LoginParamRecord> loginParamRecordCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginParamRecordDao,
                loginParamRecordCache,
                new ExceptionKeyGenerator<>(),
                loginParamRecordTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginParamRecord> loginParamRecordDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginParamRecordDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginParamRecord> loginParamRecordDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginParamRecordDao
        );
    }

    @Bean
    public GeneralBatchCrudService<RecordKey, ProtectDetailRecord> protectDetailRecordCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectDetailRecordDao,
                protectDetailRecordCache,
                new ExceptionKeyGenerator<>(),
                protectDetailRecordTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectDetailRecord> protectDetailRecordDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectDetailRecordDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectDetailRecord> protectDetailRecordDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectDetailRecordDao
        );
    }

    @Bean
    public GeneralBatchCrudService<LongIdKey, DeriveHistory> deriveHistoryGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                deriveHistoryDao,
                deriveHistoryCache,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                deriveHistoryTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DeriveHistory> deriveHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                deriveHistoryDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DeriveHistory> deriveHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                deriveHistoryDao
        );
    }
}
