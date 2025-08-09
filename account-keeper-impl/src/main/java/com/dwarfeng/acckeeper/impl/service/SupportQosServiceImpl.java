package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.handler.SupportHandler;
import com.dwarfeng.acckeeper.stack.service.SupportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class SupportQosServiceImpl implements SupportQosService {

    private final SupportHandler supportHandler;

    private final ServiceExceptionMapper sem;

    public SupportQosServiceImpl(SupportHandler supportHandler, ServiceExceptionMapper sem) {
        this.supportHandler = supportHandler;
        this.sem = sem;
    }

    @Override
    public void resetProtector() throws ServiceException {
        try {
            supportHandler.resetProtector();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置保护器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
