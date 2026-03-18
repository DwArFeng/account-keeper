package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.handler.AccessHandler;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessHandler accessHandler;

    private final ServiceExceptionMapper sem;

    public AccessServiceImpl(AccessHandler accessHandler, ServiceExceptionMapper sem) {
        this.accessHandler = accessHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public AuthInspectResult authInspect(AuthInspectInfo info) throws ServiceException {
        try {
            return accessHandler.authInspect(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("授权查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws ServiceException {
        try {
            return accessHandler.dynamicLogin(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("动态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StaticLoginResult staticLogin(StaticLoginInfo info) throws ServiceException {
        try {
            return accessHandler.staticLogin(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("静态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public DynamicLoginResult trustedDynamicLogin(TrustedDynamicLoginInfo info) throws ServiceException {
        try {
            return accessHandler.trustedDynamicLogin(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("可信动态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public StaticLoginResult trustedStaticLogin(TrustedStaticLoginInfo info) throws ServiceException {
        try {
            return accessHandler.trustedStaticLogin(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("可信静态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void logout(LogoutInfo info) throws ServiceException {
        try {
            accessHandler.logout(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("登出时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public PostponeResult postpone(PostponeInfo info) throws ServiceException {
        try {
            return accessHandler.postpone(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("延期时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void kick(KickInfo info) throws ServiceException {
        try {
            accessHandler.kick(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("踢出时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
