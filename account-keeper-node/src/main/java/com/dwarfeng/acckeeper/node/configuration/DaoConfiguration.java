package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.impl.bean.entity.*;
import com.dwarfeng.acckeeper.impl.bean.key.HibernateProtectorVariableKey;
import com.dwarfeng.acckeeper.impl.bean.key.HibernateRecordKey;
import com.dwarfeng.acckeeper.impl.dao.preset.*;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonMapper;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;
    private final RedisTemplate<String, ?> redisTemplate;

    private final AccountPresetCriteriaMaker accountPresetCriteriaMaker;
    private final LoginStatePresetEntityFilter loginStatePresetEntityFilter;
    private final LoginHistoryPresetCriteriaMaker loginHistoryPresetCriteriaMaker;
    private final ProtectorInfoPresetCriteriaMaker protectorInfoPresetCriteriaMaker;
    private final ProtectorSupportPresetCriteriaMaker protectorSupportPresetCriteriaMaker;
    private final ProtectorVariablePresetCriteriaMaker protectorVariablePresetCriteriaMaker;
    private final LoginParamRecordPresetCriteriaMaker loginParamRecordPresetCriteriaMaker;
    private final ProtectDetailRecordPresetCriteriaMaker protectDetailRecordPresetCriteriaMaker;

    @Value("${redis.dbkey.login_state}")
    private String loginStateDbKey;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            RedisTemplate<String, ?> redisTemplate,
            AccountPresetCriteriaMaker accountPresetCriteriaMaker,
            LoginStatePresetEntityFilter loginStatePresetEntityFilter,
            LoginHistoryPresetCriteriaMaker loginHistoryPresetCriteriaMaker,
            ProtectorInfoPresetCriteriaMaker protectorInfoPresetCriteriaMaker,
            ProtectorSupportPresetCriteriaMaker protectorSupportPresetCriteriaMaker,
            ProtectorVariablePresetCriteriaMaker protectorVariablePresetCriteriaMaker,
            LoginParamRecordPresetCriteriaMaker loginParamRecordPresetCriteriaMaker,
            ProtectDetailRecordPresetCriteriaMaker protectDetailRecordPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.redisTemplate = redisTemplate;
        this.accountPresetCriteriaMaker = accountPresetCriteriaMaker;
        this.loginStatePresetEntityFilter = loginStatePresetEntityFilter;
        this.loginHistoryPresetCriteriaMaker = loginHistoryPresetCriteriaMaker;
        this.protectorInfoPresetCriteriaMaker = protectorInfoPresetCriteriaMaker;
        this.protectorSupportPresetCriteriaMaker = protectorSupportPresetCriteriaMaker;
        this.protectorVariablePresetCriteriaMaker = protectorVariablePresetCriteriaMaker;
        this.loginParamRecordPresetCriteriaMaker = loginParamRecordPresetCriteriaMaker;
        this.protectDetailRecordPresetCriteriaMaker = protectDetailRecordPresetCriteriaMaker;
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
    @SuppressWarnings("unchecked")
    public RedisBatchBaseDao<LongIdKey, LoginState, FastJsonLoginState> loginStateRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonLoginState>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(LoginState.class, FastJsonLoginState.class, FastJsonMapper.class),
                loginStateDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<LongIdKey, LoginState, FastJsonLoginState> loginStateRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonLoginState>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(LoginState.class, FastJsonLoginState.class, FastJsonMapper.class),
                loginStateDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisPresetLookupDao<LongIdKey, LoginState, FastJsonLoginState> loginStateRedisPresetLookupDao() {
        return new RedisPresetLookupDao<>(
                (RedisTemplate<String, FastJsonLoginState>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(LoginState.class, FastJsonLoginState.class, FastJsonMapper.class),
                loginStatePresetEntityFilter,
                loginStateDbKey
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
}
