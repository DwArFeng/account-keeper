package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount;
import com.dwarfeng.acckeeper.impl.bean.entity.HibernateMapper;
import com.dwarfeng.acckeeper.impl.dao.preset.AccountPresetCriteriaMaker;
import com.dwarfeng.acckeeper.impl.dao.preset.LoginStatePresetEntityFilter;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonMapper;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.*;
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

    @Value("${redis.dbkey.login_state}")
    private String loginStateDbKey;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            RedisTemplate<String, ?> redisTemplate,
            AccountPresetCriteriaMaker accountPresetCriteriaMaker,
            LoginStatePresetEntityFilter loginStatePresetEntityFilter
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.redisTemplate = redisTemplate;
        this.accountPresetCriteriaMaker = accountPresetCriteriaMaker;
        this.loginStatePresetEntityFilter = loginStatePresetEntityFilter;
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
}
