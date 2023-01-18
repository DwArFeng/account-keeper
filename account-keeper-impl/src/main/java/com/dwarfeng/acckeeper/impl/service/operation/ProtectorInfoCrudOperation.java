package com.dwarfeng.acckeeper.impl.service.operation;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.cache.ProtectorInfoCache;
import com.dwarfeng.acckeeper.stack.cache.ProtectorVariableCache;
import com.dwarfeng.acckeeper.stack.dao.ProtectorInfoDao;
import com.dwarfeng.acckeeper.stack.dao.ProtectorVariableDao;
import com.dwarfeng.acckeeper.stack.service.ProtectorVariableMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProtectorInfoCrudOperation implements BatchCrudOperation<StringIdKey, ProtectorInfo> {

    private final ProtectorInfoDao protectorInfoDao;
    private final ProtectorInfoCache protectorInfoCache;

    private final ProtectorVariableDao protectorVariableDao;
    private final ProtectorVariableCache protectorVariableCache;

    @Value("${cache.timeout.entity.protector_info}")
    private long protectorInfoTimeout;

    public ProtectorInfoCrudOperation(
            ProtectorInfoDao protectorInfoDao, ProtectorInfoCache protectorInfoCache,
            ProtectorVariableDao protectorVariableDao, ProtectorVariableCache protectorVariableCache
    ) {
        this.protectorInfoDao = protectorInfoDao;
        this.protectorInfoCache = protectorInfoCache;
        this.protectorVariableDao = protectorVariableDao;
        this.protectorVariableCache = protectorVariableCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return protectorInfoCache.exists(key) || protectorInfoDao.exists(key);
    }

    @Override
    public ProtectorInfo get(StringIdKey key) throws Exception {
        if (protectorInfoCache.exists(key)) {
            return protectorInfoCache.get(key);
        } else {
            if (!protectorInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ProtectorInfo protectorInfo = protectorInfoDao.get(key);
            protectorInfoCache.push(protectorInfo, protectorInfoTimeout);
            return protectorInfo;
        }
    }

    @Override
    public StringIdKey insert(ProtectorInfo protectorInfo) throws Exception {
        protectorInfoCache.push(protectorInfo, protectorInfoTimeout);
        return protectorInfoDao.insert(protectorInfo);
    }

    @Override
    public void update(ProtectorInfo protectorInfo) throws Exception {
        protectorInfoCache.push(protectorInfo, protectorInfoTimeout);
        protectorInfoDao.update(protectorInfo);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 删除与账户相关的保护器变量。
        List<ProtectorVariableKey> protectorVariableKeys = protectorVariableDao.lookup(
                ProtectorVariableMaintainService.CHILD_FOR_PROTECTOR_INFO, new Object[]{key}
        ).stream().map(ProtectorVariable::getKey).collect(Collectors.toList());
        protectorVariableCache.batchDelete(protectorVariableKeys);
        protectorVariableDao.batchDelete(protectorVariableKeys);

        // 删除保护器信息本身。
        protectorInfoDao.delete(key);
        protectorInfoCache.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return protectorInfoCache.allExists(keys) || protectorInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return protectorInfoCache.nonExists(keys) && protectorInfoCache.nonExists(keys);
    }

    @Override
    public List<ProtectorInfo> batchGet(List<StringIdKey> keys) throws Exception {
        if (protectorInfoCache.allExists(keys)) {
            return protectorInfoCache.batchGet(keys);
        } else {
            if (!protectorInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ProtectorInfo> protectorInfos = protectorInfoDao.batchGet(keys);
            protectorInfoCache.batchPush(protectorInfos, protectorInfoTimeout);
            return protectorInfos;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<ProtectorInfo> protectorInfos) throws Exception {
        protectorInfoCache.batchPush(protectorInfos, protectorInfoTimeout);
        return protectorInfoDao.batchInsert(protectorInfos);
    }

    @Override
    public void batchUpdate(List<ProtectorInfo> protectorInfos) throws Exception {
        protectorInfoCache.batchPush(protectorInfos, protectorInfoTimeout);
        protectorInfoDao.batchUpdate(protectorInfos);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}
