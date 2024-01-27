package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.handler.AccountOperateHandler;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountOperateServiceImpl implements AccountOperateService {

    private final AccountOperateHandler accountOperateHandler;

    private final ServiceExceptionMapper sem;

    public AccountOperateServiceImpl(AccountOperateHandler accountOperateHandler, ServiceExceptionMapper sem) {
        this.accountOperateHandler = accountOperateHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException {
        try {
            accountOperateHandler.register(accountRegisterInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("注册账户时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException {
        try {
            accountOperateHandler.update(accountUpdateInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新账户时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey accountKey) throws ServiceException {
        try {
            accountOperateHandler.delete(accountKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除账户时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws ServiceException {
        try {
            return accountOperateHandler.checkPassword(passwordCheckInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断账户密码是否正确时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException {
        try {
            accountOperateHandler.updatePassword(passwordUpdateInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("更新账户密码时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException {
        try {
            accountOperateHandler.resetPassword(passwordResetInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("重置账户密码时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void invalid(StringIdKey accountKey) throws ServiceException {
        try {
            accountOperateHandler.invalid(accountKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("使账户之前的登录信息无效时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
