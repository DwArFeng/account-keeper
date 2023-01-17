package com.dwarfeng.acckeeper.impl.service.operation;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.cache.AccountCache;
import com.dwarfeng.acckeeper.stack.cache.LoginHistoryCache;
import com.dwarfeng.acckeeper.stack.dao.AccountDao;
import com.dwarfeng.acckeeper.stack.dao.LoginHistoryDao;
import com.dwarfeng.acckeeper.stack.dao.LoginStateDao;
import com.dwarfeng.acckeeper.stack.dao.ProtectorInfoDao;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountCrudOperation implements BatchCrudOperation<StringIdKey, Account> {

    private final AccountDao accountDao;
    private final AccountCache accountCache;

    private final LoginStateDao loginStateDao;

    private final LoginHistoryDao loginHistoryDao;
    private final LoginHistoryCache loginHistoryCache;

    private final ProtectorInfoCrudOperation protectInfoCrudOperation;
    private final ProtectorInfoDao protectorInfoDao;

    @Value("${cache.timeout.entity.account}")
    private long accountTimeout;

    public AccountCrudOperation(
            AccountDao accountDao, AccountCache accountCache,
            LoginStateDao loginStateDao,
            LoginHistoryDao loginHistoryDao, LoginHistoryCache loginHistoryCache,
            ProtectorInfoCrudOperation protectInfoCrudOperation, ProtectorInfoDao protectorInfoDao
    ) {
        this.accountDao = accountDao;
        this.accountCache = accountCache;
        this.loginStateDao = loginStateDao;
        this.loginHistoryDao = loginHistoryDao;
        this.loginHistoryCache = loginHistoryCache;
        this.protectInfoCrudOperation = protectInfoCrudOperation;
        this.protectorInfoDao = protectorInfoDao;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return accountCache.exists(key) || accountDao.exists(key);
    }

    @Override
    public Account get(StringIdKey key) throws Exception {
        if (accountCache.exists(key)) {
            return accountCache.get(key);
        } else {
            if (!accountDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Account account = accountDao.get(key);
            accountCache.push(account, accountTimeout);
            return account;
        }
    }

    @Override
    public StringIdKey insert(Account account) throws Exception {
        accountCache.push(account, accountTimeout);
        return accountDao.insert(account);
    }

    @Override
    public void update(Account account) throws Exception {
        accountCache.push(account, accountTimeout);
        accountDao.update(account);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 删除与账户相关的登陆状态。
        List<LongIdKey> loginStateKeys = loginStateDao.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{key}
        ).stream().map(LoginState::getKey).collect(Collectors.toList());
        loginStateDao.batchDelete(loginStateKeys);

        // 解除与账户相关的登陆历史的关联。
        List<LoginHistory> loginHistories = loginHistoryDao.lookup(
                LoginHistoryMaintainService.CHILD_FOR_ACCOUNT, new Object[]{key}
        );
        loginHistories.forEach(l -> l.setAccountKey(null));
        loginHistoryCache.batchDelete(loginHistories.stream().map(LoginHistory::getKey).collect(Collectors.toList()));
        loginHistoryDao.batchUpdate(loginHistories);

        // 删除与账户相关的保护器信息。
        if (protectorInfoDao.exists(key)) {
            protectInfoCrudOperation.delete(key);
        }

        // 删除报警设置本身。
        accountDao.delete(key);
        accountCache.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return accountCache.allExists(keys) || accountDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return accountCache.nonExists(keys) && accountCache.nonExists(keys);
    }

    @Override
    public List<Account> batchGet(List<StringIdKey> keys) throws Exception {
        if (accountCache.allExists(keys)) {
            return accountCache.batchGet(keys);
        } else {
            if (!accountDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Account> accounts = accountDao.batchGet(keys);
            accountCache.batchPush(accounts, accountTimeout);
            return accounts;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<Account> accounts) throws Exception {
        accountCache.batchPush(accounts, accountTimeout);
        return accountDao.batchInsert(accounts);
    }

    @Override
    public void batchUpdate(List<Account> accounts) throws Exception {
        accountCache.batchPush(accounts, accountTimeout);
        accountDao.batchUpdate(accounts);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}
