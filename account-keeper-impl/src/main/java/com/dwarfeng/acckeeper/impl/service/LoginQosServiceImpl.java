package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.LoginHandler;
import com.dwarfeng.acckeeper.stack.service.LoginQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginQosServiceImpl implements LoginQosService {

    private final LoginHandler loginHandler;

    private final ServiceExceptionMapper sem;

    public LoginQosServiceImpl(LoginHandler loginHandler, ServiceExceptionMapper sem) {
        this.loginHandler = loginHandler;
        this.sem = sem;
    }

    @Override
    @Deprecated
    public LoginState login(LoginInfo loginInfo) throws ServiceException {
        try {
            return loginHandler.login(loginInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginState dynamicLogin(DynamicLoginInfo loginInfo) throws ServiceException {
        try {
            return loginHandler.dynamicLogin(loginInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("动态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginState staticLogin(StaticLoginInfo loginInfo) throws ServiceException {
        try {
            return loginHandler.staticLogin(loginInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("静态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LoginState> inspectLoginStateByKey(LongIdKey loginStateKey) throws ServiceException {
        try {
            return loginHandler.inspectLoginStateByKey(loginStateKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定的登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LoginState> inspectLoginStateByAccount(StringIdKey accountKey) throws ServiceException {
        try {
            return loginHandler.inspectLoginStateByAccount(accountKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定用户的所有登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<LoginState> inspectAllLoginState() throws ServiceException {
        try {
            return loginHandler.inspectAllLoginState();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询所有的登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void kickByLoginState(LongIdKey loginStateKey) throws ServiceException {
        try {
            loginHandler.kickByLoginState(loginStateKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("解除指定的登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void kickByAccount(StringIdKey accountKey) throws ServiceException {
        try {
            loginHandler.kickByAccount(accountKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("解除指定用户的所有登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void kickAll() throws ServiceException {
        try {
            loginHandler.kickAll();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("解除所有的登陆状态时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
