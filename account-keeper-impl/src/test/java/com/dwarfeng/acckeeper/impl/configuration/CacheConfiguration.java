package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonAccount;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginHistory;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonMapper;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${cache.prefix.entity.account}")
    private String accountPrefix;
    @Value("${cache.prefix.entity.login_state}")
    private String loginStatePrefix;
    @Value("${cache.prefix.entity.login_history}")
    private String loginHistoryPrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, Account, FastJsonAccount> accountCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonAccount>) template,
                new StringIdStringKeyFormatter(accountPrefix),
                new MapStructBeanTransformer<>(Account.class, FastJsonAccount.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, LoginState, FastJsonLoginState> loginStateRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonLoginState>) template,
                new LongIdStringKeyFormatter(loginStatePrefix),
                new MapStructBeanTransformer<>(LoginState.class, FastJsonLoginState.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, LoginHistory, FastJsonLoginHistory> loginHistoryCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonLoginHistory>) template,
                new LongIdStringKeyFormatter(loginHistoryPrefix),
                new MapStructBeanTransformer<>(LoginHistory.class, FastJsonLoginHistory.class, FastJsonMapper.class)
        );
    }
}
