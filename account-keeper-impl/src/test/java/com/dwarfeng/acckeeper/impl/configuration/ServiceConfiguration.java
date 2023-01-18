package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.impl.service.operation.AccountCrudOperation;
import com.dwarfeng.acckeeper.impl.service.operation.LoginHistoryCrudOperation;
import com.dwarfeng.acckeeper.impl.service.operation.ProtectorInfoCrudOperation;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.cache.*;
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
    private final CheckerInfoDao checkerInfoDao;
    private final CheckerInfoCache checkerInfoCache;
    private final CheckerSupportDao checkerSupportDao;
    private final CheckerSupportCache checkerSupportCache;

    @Value("${cache.timeout.entity.protector_support}")
    private long protectorSupportTimeout;
    @Value("${cache.timeout.entity.protector_variable}")
    private long protectorVariableTimeout;
    @Value("${cache.timeout.entity.login_param_record}")
    private long loginParamRecordTimeout;
    @Value("${cache.timeout.entity.protect_detail_record}")
    private long protectDetailRecordTimeout;
    @Value("${cache.timeout.entity.checker_info}")
    private long checkerInfoTimeout;
    @Value("${cache.timeout.entity.checker_support}")
    private long checkerSupportTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            KeyFetcher<LongIdKey> longIdKeyFetcher,
            AccountCrudOperation accountCrudOperation, AccountDao accountDao,
            LoginStateDao loginStateDao,
            LoginHistoryCrudOperation loginHistoryCrudOperation, LoginHistoryDao loginHistoryDao,
            ProtectorInfoCrudOperation protectorInfoCrudOperation, ProtectorInfoDao protectorInfoDao,
            ProtectorSupportDao protectorSupportDao, ProtectorSupportCache protectorSupportCache,
            ProtectorVariableDao protectorVariableDao, ProtectorVariableCache protectorVariableCache,
            LoginParamRecordDao loginParamRecordDao, LoginParamRecordCache loginParamRecordCache,
            ProtectDetailRecordDao protectDetailRecordDao, ProtectDetailRecordCache protectDetailRecordCache,
            CheckerInfoDao checkerInfoDao, CheckerInfoCache checkerInfoCache,
            CheckerSupportDao checkerSupportDao, CheckerSupportCache checkerSupportCache
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.longIdKeyFetcher = longIdKeyFetcher;
        this.accountCrudOperation = accountCrudOperation;
        this.accountDao = accountDao;
        this.loginStateDao = loginStateDao;
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
        this.checkerInfoDao = checkerInfoDao;
        this.checkerInfoCache = checkerInfoCache;
        this.checkerSupportDao = checkerSupportDao;
        this.checkerSupportCache = checkerSupportCache;
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
    public CustomBatchCrudService<LongIdKey, LoginHistory> loginHistoryCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                loginHistoryCrudOperation,
                longIdKeyFetcher,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
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

    @Bean
    public GeneralBatchCrudService<RecordKey, LoginParamRecord> loginParamRecordCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                loginParamRecordDao,
                loginParamRecordCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                loginParamRecordTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<LoginParamRecord> loginParamRecordDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                loginParamRecordDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<LoginParamRecord> loginParamRecordDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                loginParamRecordDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<RecordKey, ProtectDetailRecord> protectDetailRecordCustomBatchCrudService() {
        return new GeneralBatchCrudService<>(
                protectDetailRecordDao,
                protectDetailRecordCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                protectDetailRecordTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<ProtectDetailRecord> protectDetailRecordDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                protectDetailRecordDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<ProtectDetailRecord> protectDetailRecordDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                protectDetailRecordDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, CheckerInfo> checkerInfoGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                checkerInfoDao,
                checkerInfoCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                checkerInfoTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CheckerInfo> checkerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                checkerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CheckerInfo> checkerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                checkerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, CheckerSupport> checkerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                checkerSupportDao,
                checkerSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                checkerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CheckerSupport> checkerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                checkerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CheckerSupport> checkerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                checkerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
