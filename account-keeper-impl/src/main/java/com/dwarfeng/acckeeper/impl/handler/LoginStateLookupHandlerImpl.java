package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.LoginStateLookupHandler;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class LoginStateLookupHandlerImpl implements LoginStateLookupHandler {

    private final LoginStateMaintainService loginStateMaintainService;

    public LoginStateLookupHandlerImpl(LoginStateMaintainService loginStateMaintainService) {
        this.loginStateMaintainService = loginStateMaintainService;
    }

    @Override
    @BehaviorAnalyse
    public LoginStateLookupResult lookup(LoginStateLookupInfo info) throws HandlerException {
        try {
            // 展开参数。
            StringIdKey loginStateKey = info.getLoginStateKey();
            StringIdKey accountKey = info.getAccountKey();

            List<LoginState> loginStates;

            // 如果 loginStateKey 不为 null，则查询指定的登录状态。
            if (Objects.nonNull(loginStateKey)) {
                LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);
                if (Objects.nonNull(loginState)) {
                    loginStates = Collections.singletonList(loginState);
                } else {
                    loginStates = new ArrayList<>();
                }
            }
            // 否则，如果 accountKey 不为 null，则查询指定账号下的所有登录状态。
            else if (Objects.nonNull(accountKey)) {
                loginStates = loginStateMaintainService.lookupAsList(
                        LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{accountKey}
                );
            }
            // 否则，查询所有登录状态。
            else {
                loginStates = loginStateMaintainService.lookupAsList();
            }

            return new LoginStateLookupResult(loginStates);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
