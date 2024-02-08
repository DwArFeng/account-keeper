package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.impl.bean.HibernateMapper;
import com.dwarfeng.acckeeper.impl.bean.entity.*;
import com.dwarfeng.acckeeper.impl.bean.key.HibernateProtectorVariableKey;
import com.dwarfeng.acckeeper.impl.bean.key.HibernateRecordKey;
import com.dwarfeng.acckeeper.impl.dao.preset.*;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;

    private final AccountPresetCriteriaMaker accountPresetCriteriaMaker;
    private final LoginStatePresetCriteriaMaker loginStatePresetCriteriaMaker;
    private final LoginHistoryPresetCriteriaMaker loginHistoryPresetCriteriaMaker;
    private final ProtectorInfoPresetCriteriaMaker protectorInfoPresetCriteriaMaker;
    private final ProtectorSupportPresetCriteriaMaker protectorSupportPresetCriteriaMaker;
    private final ProtectorVariablePresetCriteriaMaker protectorVariablePresetCriteriaMaker;
    private final LoginParamRecordPresetCriteriaMaker loginParamRecordPresetCriteriaMaker;
    private final ProtectDetailRecordPresetCriteriaMaker protectDetailRecordPresetCriteriaMaker;
    private final DeriveHistoryPresetCriteriaMaker deriveHistoryPresetCriteriaMaker;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            AccountPresetCriteriaMaker accountPresetCriteriaMaker,
            LoginStatePresetCriteriaMaker loginStatePresetCriteriaMaker,
            LoginHistoryPresetCriteriaMaker loginHistoryPresetCriteriaMaker,
            ProtectorInfoPresetCriteriaMaker protectorInfoPresetCriteriaMaker,
            ProtectorSupportPresetCriteriaMaker protectorSupportPresetCriteriaMaker,
            ProtectorVariablePresetCriteriaMaker protectorVariablePresetCriteriaMaker,
            LoginParamRecordPresetCriteriaMaker loginParamRecordPresetCriteriaMaker,
            ProtectDetailRecordPresetCriteriaMaker protectDetailRecordPresetCriteriaMaker,
            DeriveHistoryPresetCriteriaMaker deriveHistoryPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.accountPresetCriteriaMaker = accountPresetCriteriaMaker;
        this.loginStatePresetCriteriaMaker = loginStatePresetCriteriaMaker;
        this.loginHistoryPresetCriteriaMaker = loginHistoryPresetCriteriaMaker;
        this.protectorInfoPresetCriteriaMaker = protectorInfoPresetCriteriaMaker;
        this.protectorSupportPresetCriteriaMaker = protectorSupportPresetCriteriaMaker;
        this.protectorVariablePresetCriteriaMaker = protectorVariablePresetCriteriaMaker;
        this.loginParamRecordPresetCriteriaMaker = loginParamRecordPresetCriteriaMaker;
        this.protectDetailRecordPresetCriteriaMaker = protectDetailRecordPresetCriteriaMaker;
        this.deriveHistoryPresetCriteriaMaker = deriveHistoryPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Account, HibernateAccount>
    accountHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(Account.class, HibernateAccount.class, HibernateMapper.class),
                HibernateAccount.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<Account, HibernateAccount> accountHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Account.class, HibernateAccount.class, HibernateMapper.class),
                HibernateAccount.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Account, HibernateAccount> accountHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Account.class, HibernateAccount.class, HibernateMapper.class),
                HibernateAccount.class,
                accountPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, LoginState, HibernateLoginState>
    loginStateHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(LoginState.class, HibernateLoginState.class, HibernateMapper.class),
                HibernateLoginState.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<LoginState, HibernateLoginState> loginStateHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LoginState.class, HibernateLoginState.class, HibernateMapper.class),
                HibernateLoginState.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<LoginState, HibernateLoginState> loginStateHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LoginState.class, HibernateLoginState.class, HibernateMapper.class),
                HibernateLoginState.class,
                loginStatePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, LoginHistory, HibernateLoginHistory>
    loginHistoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(LoginHistory.class, HibernateLoginHistory.class, HibernateMapper.class),
                HibernateLoginHistory.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<LoginHistory, HibernateLoginHistory> loginHistoryHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LoginHistory.class, HibernateLoginHistory.class, HibernateMapper.class),
                HibernateLoginHistory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<LoginHistory, HibernateLoginHistory> loginHistoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LoginHistory.class, HibernateLoginHistory.class, HibernateMapper.class),
                HibernateLoginHistory.class,
                loginHistoryPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ProtectorInfo, HibernateProtectorInfo>
    protectorInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        ProtectorInfo.class, HibernateProtectorInfo.class, HibernateMapper.class
                ),
                HibernateProtectorInfo.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProtectorInfo, HibernateProtectorInfo> protectorInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorInfo.class, HibernateProtectorInfo.class, HibernateMapper.class
                ),
                HibernateProtectorInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProtectorInfo, HibernateProtectorInfo> protectorInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorInfo.class, HibernateProtectorInfo.class, HibernateMapper.class
                ),
                HibernateProtectorInfo.class,
                protectorInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, ProtectorSupport, HibernateProtectorSupport>
    protectorSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        ProtectorSupport.class, HibernateProtectorSupport.class, HibernateMapper.class
                ),
                HibernateProtectorSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProtectorSupport, HibernateProtectorSupport>
    protectorSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorSupport.class, HibernateProtectorSupport.class, HibernateMapper.class
                ),
                HibernateProtectorSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProtectorSupport, HibernateProtectorSupport>
    protectorSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorSupport.class, HibernateProtectorSupport.class, HibernateMapper.class
                ),
                HibernateProtectorSupport.class,
                protectorSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<ProtectorVariableKey, HibernateProtectorVariableKey, ProtectorVariable,
            HibernateProtectorVariable> protectorVariableHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorVariableKey.class, HibernateProtectorVariableKey.class, HibernateMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ProtectorVariable.class, HibernateProtectorVariable.class, HibernateMapper.class
                ),
                HibernateProtectorVariable.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProtectorVariable, HibernateProtectorVariable>
    protectorVariableHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorVariable.class, HibernateProtectorVariable.class, HibernateMapper.class
                ),
                HibernateProtectorVariable.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProtectorVariable, HibernateProtectorVariable>
    protectorVariableHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectorVariable.class, HibernateProtectorVariable.class, HibernateMapper.class
                ),
                HibernateProtectorVariable.class,
                protectorVariablePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<RecordKey, HibernateRecordKey, LoginParamRecord, HibernateLoginParamRecord>
    loginParamRecordHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        RecordKey.class, HibernateRecordKey.class, HibernateMapper.class
                ),
                new MapStructBeanTransformer<>(
                        LoginParamRecord.class, HibernateLoginParamRecord.class, HibernateMapper.class
                ),
                HibernateLoginParamRecord.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<LoginParamRecord, HibernateLoginParamRecord>
    loginParamRecordHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        LoginParamRecord.class, HibernateLoginParamRecord.class, HibernateMapper.class
                ),
                HibernateLoginParamRecord.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<LoginParamRecord, HibernateLoginParamRecord>
    loginParamRecordHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        LoginParamRecord.class, HibernateLoginParamRecord.class, HibernateMapper.class
                ),
                HibernateLoginParamRecord.class,
                loginParamRecordPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<RecordKey, HibernateRecordKey, ProtectDetailRecord, HibernateProtectDetailRecord>
    protectDetailRecordHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        RecordKey.class, HibernateRecordKey.class, HibernateMapper.class
                ),
                new MapStructBeanTransformer<>(
                        ProtectDetailRecord.class, HibernateProtectDetailRecord.class, HibernateMapper.class
                ),
                HibernateProtectDetailRecord.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<ProtectDetailRecord, HibernateProtectDetailRecord>
    protectDetailRecordHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectDetailRecord.class, HibernateProtectDetailRecord.class, HibernateMapper.class
                ),
                HibernateProtectDetailRecord.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<ProtectDetailRecord, HibernateProtectDetailRecord>
    protectDetailRecordHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        ProtectDetailRecord.class, HibernateProtectDetailRecord.class, HibernateMapper.class
                ),
                HibernateProtectDetailRecord.class,
                protectDetailRecordPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, DeriveHistory, HibernateDeriveHistory>
    deriveHistoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        DeriveHistory.class, HibernateDeriveHistory.class, HibernateMapper.class
                ),
                HibernateDeriveHistory.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<DeriveHistory, HibernateDeriveHistory> deriveHistoryHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        DeriveHistory.class, HibernateDeriveHistory.class, HibernateMapper.class
                ),
                HibernateDeriveHistory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DeriveHistory, HibernateDeriveHistory> deriveHistoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        DeriveHistory.class, HibernateDeriveHistory.class, HibernateMapper.class
                ),
                HibernateDeriveHistory.class,
                deriveHistoryPresetCriteriaMaker
        );
    }
}
