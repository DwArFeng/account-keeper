package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.DeriveHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class DeriveHandlerImpl implements DeriveHandler {

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final DeriveProcessor deriveProcessor;

    @Value("${acckeeper.derive.dynamic.expire_duration}")
    private long dynamicDeriveExpireDuration;

    public DeriveHandlerImpl(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService,
            KeyGenerator<LongIdKey> keyGenerator,
            DeriveProcessor deriveProcessor
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
        this.keyGenerator = keyGenerator;
        this.deriveProcessor = deriveProcessor;
    }

    @Override
    @BehaviorAnalyse
    public LoginState dynamicDerive(DynamicDeriveInfo deriveInfo) throws HandlerException {
        try {
            return derive0(DeriveType.DYNAMIC, deriveInfo, null);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public LoginState staticDerive(StaticDeriveInfo deriveInfo) throws HandlerException {
        try {
            return derive0(DeriveType.STATIC, null, deriveInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private LoginState derive0(DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo)
            throws Exception {
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
        StringIdKey accountKey = deriveComplex.getAccountKey();
        Date currentDate = new Date();
        Date expireDate = parseExpireDate(deriveType, deriveComplex, staticDeriveInfo);
        String remark = parseRemark(deriveType, dynamicDeriveInfo, staticDeriveInfo);
        long serialVersion = deriveComplex.getSerialVersion();
        int type = parseLoginStateType(deriveType);
        LoginState loginState = new LoginState(
                keyGenerator.generate(), accountKey, expireDate, serialVersion, currentDate, type, remark
        );
        // 插入派生实体。
        loginStateMaintainService.insertOrUpdate(loginState);

        // 自增账户的派生次数。
        Account account = deriveComplex.getAccount();
        account.setDeriveCount(account.getDeriveCount() + 1);
        accountMaintainService.update(account);

        // 返回结果。
        return loginState;
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
