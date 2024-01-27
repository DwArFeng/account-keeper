package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.handler.CleanHandler;
import com.dwarfeng.acckeeper.stack.service.CleanQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class CleanQosServiceImpl implements CleanQosService {

    private final CleanHandler cleanHandler;

    private final ServiceExceptionMapper sem;

    public CleanQosServiceImpl(CleanHandler cleanHandler, ServiceExceptionMapper sem) {
        this.cleanHandler = cleanHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return cleanHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取清理服务是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            cleanHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动清理服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            cleanHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止清理服务时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
