package com.dwarfeng.acckeeper.impl.cache;

import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonCheckerInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.CheckerInfo;
import com.dwarfeng.acckeeper.stack.cache.CheckerInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CheckerInfoCacheImpl implements CheckerInfoCache {

    private final RedisBatchBaseCache<StringIdKey, CheckerInfo, FastJsonCheckerInfo>
            checkerInfoBatchBaseDelegate;

    public CheckerInfoCacheImpl(
            RedisBatchBaseCache<StringIdKey, CheckerInfo, FastJsonCheckerInfo> checkerInfoBatchBaseDelegate
    ) {
        this.checkerInfoBatchBaseDelegate = checkerInfoBatchBaseDelegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(StringIdKey key) throws CacheException {
        return checkerInfoBatchBaseDelegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public CheckerInfo get(StringIdKey key) throws CacheException {
        return checkerInfoBatchBaseDelegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void push(CheckerInfo value, long timeout) throws CacheException {
        checkerInfoBatchBaseDelegate.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey key) throws CacheException {
        checkerInfoBatchBaseDelegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        checkerInfoBatchBaseDelegate.clear();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<StringIdKey> keys) throws CacheException {
        return checkerInfoBatchBaseDelegate.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<StringIdKey> keys) throws CacheException {
        return checkerInfoBatchBaseDelegate.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<CheckerInfo> batchGet(@SkipRecord List<StringIdKey> keys) throws CacheException {
        return checkerInfoBatchBaseDelegate.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchPush(@SkipRecord List<CheckerInfo> entities, long timeout) throws CacheException {
        checkerInfoBatchBaseDelegate.batchPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<StringIdKey> keys) throws CacheException {
        checkerInfoBatchBaseDelegate.batchDelete(keys);
    }
}
