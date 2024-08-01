package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.handler.ProtectLocalCacheHandler;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.handler.ProtectorHandler;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProtectLocalCacheHandlerImpl implements ProtectLocalCacheHandler {

    private final GeneralLocalCacheHandler<StringIdKey, Protector> handler;

    public ProtectLocalCacheHandlerImpl(ProtectorFetcher protectorFetcher) {
        this.handler = new GeneralLocalCacheHandler<>(protectorFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(StringIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public Protector get(StringIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(StringIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class ProtectorFetcher implements Fetcher<StringIdKey, Protector> {

        private final ProtectorInfoMaintainService protectorInfoMaintainService;

        private final ProtectorHandler protectorHandler;

        public ProtectorFetcher(ProtectorInfoMaintainService protectorInfoMaintainService, ProtectorHandler protectorHandler) {
            this.protectorInfoMaintainService = protectorInfoMaintainService;
            this.protectorHandler = protectorHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(StringIdKey key) throws Exception {
            return protectorInfoMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public Protector fetch(StringIdKey key) throws Exception {
            ProtectorInfo protectorInfo = protectorInfoMaintainService.get(key);
            String type = protectorInfo.getType();
            return protectorHandler.make(type, protectorInfo.getParam());
        }
    }
}
