package com.dwarfeng.acckeeper.impl.cache;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.cache.ProtectorVariableCache;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProtectorVariableCacheImpl implements ProtectorVariableCache {

    private final RedisBatchBaseCache<ProtectorVariableKey, ProtectorVariable, FastJsonProtectorVariable>
            protectorVariableBatchBaseDelegate;

    public ProtectorVariableCacheImpl(
            RedisBatchBaseCache<ProtectorVariableKey, ProtectorVariable, FastJsonProtectorVariable>
                    protectorVariableBatchBaseDelegate
    ) {
        this.protectorVariableBatchBaseDelegate = protectorVariableBatchBaseDelegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(ProtectorVariableKey key) throws CacheException {
        return protectorVariableBatchBaseDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public ProtectorVariable get(ProtectorVariableKey key) throws CacheException {
        return protectorVariableBatchBaseDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void push(ProtectorVariable value, long timeout) throws CacheException {
        protectorVariableBatchBaseDelegate.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(ProtectorVariableKey key) throws CacheException {
        protectorVariableBatchBaseDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        protectorVariableBatchBaseDelegate.clear();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<ProtectorVariableKey> keys) throws CacheException {
        return protectorVariableBatchBaseDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<ProtectorVariableKey> keys) throws CacheException {
        return protectorVariableBatchBaseDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ProtectorVariable> batchGet(@SkipRecord List<ProtectorVariableKey> keys) throws CacheException {
        return protectorVariableBatchBaseDelegate.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchPush(@SkipRecord List<ProtectorVariable> entities, long timeout) throws CacheException {
        protectorVariableBatchBaseDelegate.batchPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<ProtectorVariableKey> keys) throws CacheException {
        protectorVariableBatchBaseDelegate.batchDelete(keys);
    }
}
