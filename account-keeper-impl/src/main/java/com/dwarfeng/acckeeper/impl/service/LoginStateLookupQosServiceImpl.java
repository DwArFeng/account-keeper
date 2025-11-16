package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.acckeeper.stack.handler.LoginStateLookupHandler;
import com.dwarfeng.acckeeper.stack.service.LoginStateLookupQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginStateLookupQosServiceImpl implements LoginStateLookupQosService {

    private final LoginStateLookupHandler loginStateLookupHandler;

    private final ServiceExceptionMapper sem;

    public LoginStateLookupQosServiceImpl(LoginStateLookupHandler loginStateLookupHandler, ServiceExceptionMapper sem) {
        this.loginStateLookupHandler = loginStateLookupHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public LoginStateLookupResult lookup(LoginStateLookupInfo info) throws ServiceException {
        try {
            return loginStateLookupHandler.lookup(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("登录状态查看时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
