package com.dwarfeng.acckeeper.impl.service.operation;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.cache.LoginHistoryCache;
import com.dwarfeng.acckeeper.stack.cache.LoginParamRecordCache;
import com.dwarfeng.acckeeper.stack.cache.ProtectDetailRecordCache;
import com.dwarfeng.acckeeper.stack.dao.LoginHistoryDao;
import com.dwarfeng.acckeeper.stack.dao.LoginParamRecordDao;
import com.dwarfeng.acckeeper.stack.dao.ProtectDetailRecordDao;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectDetailRecordMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoginHistoryCrudOperation implements BatchCrudOperation<LongIdKey, LoginHistory> {

    private final LoginHistoryDao loginHistoryDao;
    private final LoginHistoryCache loginHistoryCache;

    private final LoginParamRecordDao loginParamRecordDao;
    private final LoginParamRecordCache loginParamRecordCache;

    private final ProtectDetailRecordDao protectDetailRecordDao;
    private final ProtectDetailRecordCache protectDetailRecordCache;

    @Value("${cache.timeout.entity.login_history}")
    private long loginHistoryTimeout;

    public LoginHistoryCrudOperation(
            LoginHistoryDao loginHistoryDao, LoginHistoryCache loginHistoryCache,
            LoginParamRecordDao loginParamRecordDao, LoginParamRecordCache loginParamRecordCache,
            ProtectDetailRecordDao protectDetailRecordDao, ProtectDetailRecordCache protectDetailRecordCache
    ) {
        this.loginHistoryDao = loginHistoryDao;
        this.loginHistoryCache = loginHistoryCache;
        this.loginParamRecordDao = loginParamRecordDao;
        this.loginParamRecordCache = loginParamRecordCache;
        this.protectDetailRecordDao = protectDetailRecordDao;
        this.protectDetailRecordCache = protectDetailRecordCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return loginHistoryCache.exists(key) || loginHistoryDao.exists(key);
    }

    @Override
    public LoginHistory get(LongIdKey key) throws Exception {
        if (loginHistoryCache.exists(key)) {
            return loginHistoryCache.get(key);
        } else {
            if (!loginHistoryDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            LoginHistory loginHistory = loginHistoryDao.get(key);
            loginHistoryCache.push(loginHistory, loginHistoryTimeout);
            return loginHistory;
        }
    }

    @Override
    public LongIdKey insert(LoginHistory loginHistory) throws Exception {
        loginHistoryCache.push(loginHistory, loginHistoryTimeout);
        return loginHistoryDao.insert(loginHistory);
    }

    @Override
    public void update(LoginHistory loginHistory) throws Exception {
        loginHistoryCache.push(loginHistory, loginHistoryTimeout);
        loginHistoryDao.update(loginHistory);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与账户相关的登陆参数记录。
        List<RecordKey> loginParamRecordKeys = loginParamRecordDao.lookup(
                LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY, new Object[]{key}
        ).stream().map(LoginParamRecord::getKey).collect(Collectors.toList());
        loginParamRecordCache.batchDelete(loginParamRecordKeys);
        loginParamRecordDao.batchDelete(loginParamRecordKeys);

        // 删除与账户相关的保护详细信息记录。
        List<RecordKey> protectDetailRecordKeys = protectDetailRecordDao.lookup(
                ProtectDetailRecordMaintainService.CHILD_FOR_LOGIN_HISTORY, new Object[]{key}
        ).stream().map(ProtectDetailRecord::getKey).collect(Collectors.toList());
        protectDetailRecordCache.batchDelete(protectDetailRecordKeys);
        protectDetailRecordDao.batchDelete(protectDetailRecordKeys);

        // 删除报警设置本身。
        loginHistoryDao.delete(key);
        loginHistoryCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return loginHistoryCache.allExists(keys) || loginHistoryDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return loginHistoryCache.nonExists(keys) && loginHistoryCache.nonExists(keys);
    }

    @Override
    public List<LoginHistory> batchGet(List<LongIdKey> keys) throws Exception {
        if (loginHistoryCache.allExists(keys)) {
            return loginHistoryCache.batchGet(keys);
        } else {
            if (!loginHistoryDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<LoginHistory> loginHistories = loginHistoryDao.batchGet(keys);
            loginHistoryCache.batchPush(loginHistories, loginHistoryTimeout);
            return loginHistories;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<LoginHistory> loginHistorys) throws Exception {
        loginHistoryCache.batchPush(loginHistorys, loginHistoryTimeout);
        return loginHistoryDao.batchInsert(loginHistorys);
    }

    @Override
    public void batchUpdate(List<LoginHistory> loginHistorys) throws Exception {
        loginHistoryCache.batchPush(loginHistorys, loginHistoryTimeout);
        loginHistoryDao.batchUpdate(loginHistorys);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
