package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class DeriveProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeriveProcessor.class);

    private final LoginStateMaintainService loginStateMaintainService;
    private final AccountMaintainService accountMaintainService;

    private final PushHandler pushHandler;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final TransactionWrapper transactionWrapper;

    public DeriveProcessor(
            LoginStateMaintainService loginStateMaintainService,
            AccountMaintainService accountMaintainService,
            PushHandler pushHandler,
            KeyGenerator<LongIdKey> keyGenerator,
            TransactionWrapper transactionWrapper
    ) {
        this.loginStateMaintainService = loginStateMaintainService;
        this.accountMaintainService = accountMaintainService;
        this.pushHandler = pushHandler;
        this.keyGenerator = keyGenerator;
        this.transactionWrapper = transactionWrapper;
    }

    // 为了确保代码的可读性，此处不对代码结构进行优化。
    @SuppressWarnings("ConstantValue")
    @BehaviorAnalyse
    public DeriveComplex processDerive(
            DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo
    ) throws Exception {
        // 定义变量。
        LongIdKey loginStateKey;
        Long loginStateId;
        StringIdKey accountKey;
        String accountId;
        Date happenedDate;
        Date expireDate = null;
        long serialVersion = 0;
        String deriveRemark = parseDeriveRemark(deriveType, dynamicDeriveInfo, staticDeriveInfo);
        Account account = null;

        // 获取当前时间。
        happenedDate = new Date();
        // 获取登录状态主键。
        loginStateKey = parseLoginStateKey(deriveType, dynamicDeriveInfo, staticDeriveInfo);
        loginStateId = loginStateKey.getLongId();

        // 获取登录状态，进行登录状态校验。
        LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);
        if (Objects.isNull(loginState)) {
            return new DeriveComplex(
                    loginStateKey, loginStateId, happenedDate, Constants.DERIVE_RESPONSE_CODE_LOGIN_STATE_NOT_EXISTS,
                    null, null, expireDate, serialVersion, deriveRemark,
                    new LoginStateNotExistsException(loginStateKey), account
            );
        }

        // 登录状态超时校验。
        if (Objects.nonNull(loginState.getExpireDate()) && loginState.getExpireDate().before(happenedDate)) {
            return new DeriveComplex(
                    loginStateKey, loginStateId, happenedDate, Constants.DERIVE_RESPONSE_CODE_LOGIN_STATE_EXPIRED,
                    null, null, expireDate, serialVersion, deriveRemark,
                    new LoginStateExpiredException(loginStateKey), account
            );
        }

        // 获取主键。
        accountKey = loginState.getAccountKey();
        accountId = Optional.ofNullable(accountKey).map(StringIdKey::getStringId).orElse(null);

        // 获取用户，并进行用户校验。
        if (Objects.nonNull(accountKey)) {
            account = accountMaintainService.getIfExists(accountKey);
        }
        if (Objects.isNull(account)) {
            return new DeriveComplex(
                    loginStateKey, loginStateId, happenedDate, Constants.DERIVE_RESPONSE_CODE_ACCOUNT_NOT_EXISTS,
                    accountKey, accountId, expireDate, serialVersion, deriveRemark,
                    new AccountNotExistsException(accountKey), account
            );
        }
        long accountSerialVersion = account.getSerialVersion();
        if (!account.isEnabled()) {
            return new DeriveComplex(
                    loginStateKey, loginStateId, happenedDate, Constants.DERIVE_RESPONSE_CODE_ACCOUNT_DISABLED,
                    accountKey, accountId, expireDate, serialVersion, deriveRemark,
                    new AccountDisabledException(accountKey), account
            );
        }

        // 序列版本校验。
        long loginStateSerialVersion = loginState.getSerialVersion();
        if (!Objects.equals(accountSerialVersion, loginStateSerialVersion)) {
            return new DeriveComplex(
                    loginStateKey, loginStateId, happenedDate,
                    Constants.DERIVE_RESPONSE_CODE_SERIAL_VERSION_INCONSISTENT,
                    accountKey, accountId, expireDate, serialVersion, deriveRemark,
                    new SerialVersionInconsistentException(loginStateKey), account
            );
        }

        // 返回正常的登录结果。
        return new DeriveComplex(
                loginStateKey, loginStateId, happenedDate, Constants.DERIVE_RESPONSE_CODE_PASSED,
                accountKey, accountId, expireDate, serialVersion, deriveRemark, null, account
        );
    }

    private LongIdKey parseLoginStateKey(
            DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo
    ) {
        switch (deriveType) {
            case DYNAMIC:
                return dynamicDeriveInfo.getLoginStateKey();
            case STATIC:
                return staticDeriveInfo.getLoginStateKey();
            default:
                throw new IllegalArgumentException("非法的派生类型: " + deriveType);
        }
    }

    private String parseDeriveRemark(
            DeriveType deriveType, DynamicDeriveInfo dynamicDeriveInfo, StaticDeriveInfo staticDeriveInfo
    ) {
        switch (deriveType) {
            case DYNAMIC:
                return dynamicDeriveInfo.getRemark();
            case STATIC:
                return staticDeriveInfo.getRemark();
            default:
                throw new IllegalArgumentException("非法的派生类型: " + deriveType);
        }
    }

    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void processRecord(DeriveComplex deriveComplex) throws Exception {
        // 构建实体。
        LongIdKey deriveHistoryKey = keyGenerator.generate();
        DeriveHistory deriveHistory = new DeriveHistory(
                deriveHistoryKey, deriveComplex.getAccountId(), deriveComplex.getHappenedDate(),
                deriveComplex.getResponseCode(), deriveComplex.getDeriveRemark()
        );

        // 插入数据。
        transactionWrapper.insertHistoryEntities(deriveHistory);

        // 构建 DeriveRecord，并进行事件推送。
        DeriveHistoryRecordInfo deriveHistoryRecordInfo = historyToRecord(deriveHistory);
        try {
            pushHandler.deriveHistoryRecorded(deriveHistoryRecordInfo);
        } catch (Exception e) {
            LOGGER.warn("推送登录历史被记录事件时发生异常, 事件将不会被推送, 异常信息如下: ", e);
        }
    }

    private DeriveHistoryRecordInfo historyToRecord(DeriveHistory deriveHistory) {
        LongIdKey deriveHistoryKey = deriveHistory.getKey();
        String accountId = deriveHistory.getAccountId();
        Date happenedDate = deriveHistory.getHappenedDate();
        int responseCode = deriveHistory.getResponseCode();
        String deriveRemark = deriveHistory.getDeriveRemark();
        return new DeriveHistoryRecordInfo(deriveHistoryKey, accountId, happenedDate, responseCode, deriveRemark);
    }

    @Component
    public static class TransactionWrapper {

        private final DeriveHistoryMaintainService deriveHistoryMaintainService;

        public TransactionWrapper(
                DeriveHistoryMaintainService deriveHistoryMaintainService
        ) {
            this.deriveHistoryMaintainService = deriveHistoryMaintainService;
        }

        public void insertHistoryEntities(DeriveHistory deriveHistory) throws Exception {
            deriveHistoryMaintainService.insert(deriveHistory);
        }
    }
}
