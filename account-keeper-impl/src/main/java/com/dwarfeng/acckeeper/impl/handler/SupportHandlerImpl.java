package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.handler.ProtectorSupporter;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.acckeeper.stack.handler.SupportHandler;
import com.dwarfeng.acckeeper.stack.service.ProtectorSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final ProtectorSupportMaintainService protectorSupportMaintainService;

    private final List<ProtectorSupporter> protectorSupporters;

    public SupportHandlerImpl(
            ProtectorSupportMaintainService protectorSupportMaintainService,
            List<ProtectorSupporter> protectorSupporters
    ) {
        this.protectorSupportMaintainService = protectorSupportMaintainService;
        this.protectorSupporters = Optional.ofNullable(protectorSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetProtector() throws HandlerException {
        try {
            doResetProtector();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetProtector() throws Exception {
        List<StringIdKey> protectorKeys = protectorSupportMaintainService.lookupAsList().stream()
                .map(ProtectorSupport::getKey).collect(Collectors.toList());
        protectorSupportMaintainService.batchDelete(protectorKeys);
        List<ProtectorSupport> protectorSupports = protectorSupporters.stream().map(supporter -> new ProtectorSupport(
                new StringIdKey(supporter.provideType()),
                supporter.provideLabel(),
                supporter.provideDescription(),
                supporter.provideExampleParam()
        )).collect(Collectors.toList());
        protectorSupportMaintainService.batchInsert(protectorSupports);
    }
}
