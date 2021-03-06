package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonAccount;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    @Autowired
    private RedisTemplate<String, ?> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.prefix.entity.account}")
    private String accountPrefix;
    @Value("${cache.prefix.entity.login_state}")
    private String loginStatePrefix;

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Account, FastJsonAccount> accountCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAccount>) template,
                new StringIdStringKeyFormatter(accountPrefix),
                new DozerBeanTransformer<>(Account.class, FastJsonAccount.class, mapper)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, LoginState, FastJsonLoginState> loginStateRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonLoginState>) template,
                new LongIdStringKeyFormatter(loginStatePrefix),
                new DozerBeanTransformer<>(LoginState.class, FastJsonLoginState.class, mapper)
        );
    }
}
