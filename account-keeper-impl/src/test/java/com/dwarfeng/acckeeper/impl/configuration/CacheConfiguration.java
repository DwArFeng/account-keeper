package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.sdk.bean.FastJsonMapper;
import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.sdk.bean.key.formatter.ProtectorVariableStringKeyFormatter;
import com.dwarfeng.acckeeper.sdk.bean.key.formatter.RecordStringKeyFormatter;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
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
    @Value("${cache.prefix.entity.protector_info}")
    private String protectorInfoPrefix;
    @Value("${cache.prefix.entity.protector_support}")
    private String protectorSupportPrefix;
    @Value("${cache.prefix.entity.protector_variable}")
    private String protectorVariablePrefix;
    @Value("${cache.prefix.entity.login_param_record}")
    private String loginParamRecordPrefix;
    @Value("${cache.prefix.entity.protect_detail_record}")
    private String protectDetailRecordPrefix;

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

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ProtectorInfo, FastJsonProtectorInfo>
    protectorInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProtectorInfo>) template,
                new StringIdStringKeyFormatter(protectorInfoPrefix),
                new MapStructBeanTransformer<>(ProtectorInfo.class, FastJsonProtectorInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, ProtectorSupport, FastJsonProtectorSupport>
    protectorSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProtectorSupport>) template,
                new StringIdStringKeyFormatter(protectorSupportPrefix),
                new MapStructBeanTransformer<>(
                        ProtectorSupport.class, FastJsonProtectorSupport.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<ProtectorVariableKey, ProtectorVariable, FastJsonProtectorVariable>
    protectorVariableRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProtectorVariable>) template,
                new ProtectorVariableStringKeyFormatter(protectorVariablePrefix),
                new MapStructBeanTransformer<>(
                        ProtectorVariable.class, FastJsonProtectorVariable.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<RecordKey, LoginParamRecord, FastJsonLoginParamRecord>
    loginParamRecordCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonLoginParamRecord>) template,
                new RecordStringKeyFormatter(loginParamRecordPrefix),
                new MapStructBeanTransformer<>(
                        LoginParamRecord.class, FastJsonLoginParamRecord.class, FastJsonMapper.class
                )
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<RecordKey, ProtectDetailRecord, FastJsonProtectDetailRecord>
    protectDetailRecordCacheDelegate() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonProtectDetailRecord>) template,
                new RecordStringKeyFormatter(protectDetailRecordPrefix),
                new MapStructBeanTransformer<>(
                        ProtectDetailRecord.class, FastJsonProtectDetailRecord.class, FastJsonMapper.class
                )
        );
    }
}
