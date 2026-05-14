package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.exception.LoginStateKeyConflictException;
import com.dwarfeng.acckeeper.stack.handler.DeriveHandler;
import com.dwarfeng.acckeeper.stack.handler.LoginStateKeyGenerateHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class DeriveHandlerImpl implements DeriveHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveHandlerImpl.class);

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;

    private final LoginStateKeyGenerateHandler loginStateKeyGenerateHandler;

    private final DeriveProcessor deriveProcessor;

    @Value("${com.dwarfeng.acckeeper.acckeeper.derive.dynamic.expire_duration}")
    private long dynamicDeriveExpireDuration;

    public DeriveHandlerImpl(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService,
            LoginStateKeyGenerateHandler loginStateKeyGenerateHandler,
            DeriveProcessor deriveProcessor
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
        this.loginStateKeyGenerateHandler = loginStateKeyGenerateHandler;
        this.deriveProcessor = deriveProcessor;
    }

    @Override
    @BehaviorAnalyse
    public DynamicDeriveResult dynamicDerive(DynamicDeriveInfo info) throws HandlerException {
        try {
            LoginState loginState = derive0(DeriveType.DYNAMIC, info, null);
            return new DynamicDeriveResult(
                    loginState.getKey(),
                    loginState.getAccountKey(),
                    loginState.getExpireDate(),
                    loginState.getGeneratedDate(),
                    loginState.getType(),
                    loginState.getRemark()
            );
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public StaticDeriveResult staticDerive(StaticDeriveInfo info) throws HandlerException {
        try {
            LoginState loginState = derive0(DeriveType.STATIC, null, info);
            return new StaticDeriveResult(
                    loginState.getKey(),
                    loginState.getAccountKey(),
                    loginState.getExpireDate(),
                    loginState.getGeneratedDate(),
                    loginState.getType(),
                    loginState.getRemark()
            );
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private LoginState derive0(
            DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo
    ) throws Exception {
        // 处理派生主逻辑。
        DeriveComplex deriveComplex = deriveProcessor.processDerive(deriveType, dynamicDeriveInfo, staticDeriveInfo);

        // 记录派生历史。
        deriveProcessor.processRecord(deriveComplex);

        // 如果响应中的异常字段不为 null，则抛出对应的异常。
        if (Objects.nonNull(deriveComplex.getException())) {
            throw deriveComplex.getException();
        }

        // 代码执行至此处，说明派生正常，可以为本次派生请求创建派生状态。
        // 根据账户实体构造派生状态实体。
        StringIdKey key = generateUniqueLoginStateKey();
        StringIdKey accountKey = deriveComplex.getAccountKey();
        Date currentDate = new Date();
        Date expireDate = parseExpireDate(deriveType, deriveComplex, staticDeriveInfo);
        String remark = parseRemark(deriveType, dynamicDeriveInfo, staticDeriveInfo);
        long serialVersion = deriveComplex.getSerialVersion();
        int type = parseLoginStateType(deriveType);
        LoginState loginState = new LoginState(key, accountKey, expireDate, serialVersion, currentDate, type, remark);
        // 插入派生实体。
        loginStateMaintainService.insertOrUpdate(loginState);

        // 自增账户的派生次数。
        Account account = deriveComplex.getAccount();
        account.setDeriveCount(account.getDeriveCount() + 1);
        accountMaintainService.update(account);

        // 返回结果。
        return loginState;
    }

    @SuppressWarnings("DuplicatedCode")
    private StringIdKey generateUniqueLoginStateKey() throws Exception {
        int attempt = 0;
        while (true) {
            attempt++;
            StringIdKey loginStateKey = loginStateKeyGenerateHandler.generate();

            boolean exists = loginStateMaintainService.exists(loginStateKey);

            if (!exists) {
                if (attempt > 1) {
                    // 超过 1 次尝试，视为异常情况，记录警告。
                    LOGGER.warn("生成登录状态主键共尝试了 {} 次, 生成的登录状态主键为 {}", attempt, loginStateKey);
                }
                return loginStateKey;
            } else {
                // 发生冲突，记录警告以便告警监控。
                LOGGER.warn("生成登录状态主键冲突, 第 {} 次尝试, 登录状态主键: {}", attempt, loginStateKey);
            }

            if (attempt >= Constants.LOGIN_STATE_KEY_GENERATE_MAX_ATTEMPTS) {
                // 超过最大尝试次数，记录警告并抛出异常。
                String message = "生成登录状态主键超过最大尝试次数 {}, 最后生成登录状态主键: {}, 仍然冲突";
                LOGGER.warn(message, Constants.LOGIN_STATE_KEY_GENERATE_MAX_ATTEMPTS, loginStateKey);
                throw new LoginStateKeyConflictException();
            }
        }
    }

    private Date parseExpireDate(
            DeriveType deriveType, DeriveComplex deriveComplex, StaticDeriveInfo staticDeriveInfo
    ) {
        switch (deriveType) {
            case DYNAMIC:
                return new Date(deriveComplex.getHappenedDate().getTime() + dynamicDeriveExpireDuration);
            case STATIC:
                return staticDeriveInfo.getExpireDate();
            default:
                throw new AssertionError("未知的派生类型: " + deriveType);
        }
    }

    private String parseRemark(
            DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo
    ) {
        switch (deriveType) {
            case DYNAMIC:
                return dynamicDeriveInfo.getRemark();
            case STATIC:
                return staticDeriveInfo.getRemark();
            default:
                throw new AssertionError("未知的派生类型: " + deriveType);
        }
    }

    private int parseLoginStateType(DeriveType deriveType) {
        switch (deriveType) {
            case DYNAMIC:
                return Constants.LOGIN_STATE_TYPE_DYNAMIC;
            case STATIC:
                return Constants.LOGIN_STATE_TYPE_STATIC;
            default:
                throw new AssertionError("未知的派生类型: " + deriveType);
        }
    }
}
