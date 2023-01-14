package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount;
import com.dwarfeng.acckeeper.impl.bean.entity.HibernateMapper;
import com.dwarfeng.acckeeper.impl.dao.preset.AccountPresetCriteriaMaker;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonAccountLoginInfo;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonMapper;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.AccountLoginInfo;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
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

    @Value("${redis.dbkey.account_login_info}")
    private String accountLoginInfoDbKey;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate, RedisTemplate<String, ?> redisTemplate,
            AccountPresetCriteriaMaker accountPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.redisTemplate = redisTemplate;
        this.accountPresetCriteriaMaker = accountPresetCriteriaMaker;
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
    public RedisBatchBaseDao<StringIdKey, AccountLoginInfo, FastJsonAccountLoginInfo>
    accountLoginInfoHibernateBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonAccountLoginInfo>) redisTemplate,
                new StringIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        AccountLoginInfo.class, FastJsonAccountLoginInfo.class, FastJsonMapper.class
                ),
                accountLoginInfoDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<StringIdKey, AccountLoginInfo, FastJsonAccountLoginInfo>
    accountLoginInfoRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonAccountLoginInfo>) redisTemplate,
                new StringIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        AccountLoginInfo.class, FastJsonAccountLoginInfo.class, FastJsonMapper.class
                ),
                accountLoginInfoDbKey
        );
    }
}
