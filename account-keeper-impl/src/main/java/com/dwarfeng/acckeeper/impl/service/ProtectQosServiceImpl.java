package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.handler.ProtectLocalCacheHandler;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.service.ProtectQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class ProtectQosServiceImpl implements ProtectQosService {

    private final ProtectLocalCacheHandler protectLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    public ProtectQosServiceImpl(ProtectLocalCacheHandler protectLocalCacheHandler, ServiceExceptionMapper sem) {
        this.protectLocalCacheHandler = protectLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public Protector getProtector(StringIdKey accountKey) throws ServiceException {
        try {
            return protectLocalCacheHandler.get(accountKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定账户的保护器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            protectLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
