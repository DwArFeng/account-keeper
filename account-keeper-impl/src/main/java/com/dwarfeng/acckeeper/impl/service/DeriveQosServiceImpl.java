package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.DeriveHandler;
import com.dwarfeng.acckeeper.stack.service.DeriveQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class DeriveQosServiceImpl implements DeriveQosService {

    private final DeriveHandler deriveHandler;

    private final ServiceExceptionMapper sem;

    public DeriveQosServiceImpl(DeriveHandler deriveHandler, ServiceExceptionMapper sem) {
        this.deriveHandler = deriveHandler;
        this.sem = sem;
    }

    @Override
    public LoginState dynamicDerive(DynamicDeriveInfo deriveInfo) throws ServiceException {
        try {
            return deriveHandler.dynamicDerive(deriveInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("动态派生时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LoginState staticDerive(StaticDeriveInfo deriveInfo) throws ServiceException {
        try {
            return deriveHandler.staticDerive(deriveInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("静态派生时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
