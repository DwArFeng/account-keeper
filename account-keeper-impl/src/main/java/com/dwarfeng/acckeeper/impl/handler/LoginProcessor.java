package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.acckeeper.stack.handler.ProtectLocalCacheHandler;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.acckeeper.stack.service.*;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
class LoginProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginProcessor.class);

    private final ApplicationContext ctx;

    private final AccountMaintainService accountMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;
    private final LoginParamRecordMaintainService loginParamRecordMaintainService;
    private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;
    private final ProtectorVariableMaintainService protectorVariableMaintainService;

    private final ProtectLocalCacheHandler protectLocalCacheHandler;
    private final PushHandler pushHandler;

    private final KeyFetcher<LongIdKey> keyFetcher;

    public LoginProcessor(
            ApplicationContext ctx,
            AccountMaintainService accountMaintainService,
            LoginHistoryMaintainService loginHistoryMaintainService,
            LoginParamRecordMaintainService loginParamRecordMaintainService,
            ProtectDetailRecordMaintainService protectDetailRecordMaintainService,
            ProtectorVariableMaintainService protectorVariableMaintainService,
            ProtectLocalCacheHandler protectLocalCacheHandler,
            PushHandler pushHandler,
            KeyFetcher<LongIdKey> keyFetcher
    ) {
        this.ctx = ctx;
        this.accountMaintainService = accountMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.loginParamRecordMaintainService = loginParamRecordMaintainService;
        this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
        this.protectorVariableMaintainService = protectorVariableMaintainService;
        this.protectLocalCacheHandler = protectLocalCacheHandler;
        this.pushHandler = pushHandler;
        this.keyFetcher = keyFetcher;
    }

    // 为了确保代码的可读性，此处不对代码结构进行优化。
    @SuppressWarnings("ConstantValue")
    @BehaviorAnalyse
    public LoginComplex processLogin(LoginInfo loginInfo) throws Exception {
        // 定义变量。
        StringIdKey accountKey;
        String accountId;
        Date happenedDate;
        String message = null;
        Integer alarmLevel = null;
        Map<String, String> extraParams = Collections.emptyMap();
        Map<String, String> protectDetail = Collections.emptyMap();
        long serialVersion = 0;
        Account account;

        // 获取当前时间。
        happenedDate = new Date();
        // 获取额外登陆参数。
        if (Objects.nonNull(loginInfo.getExtraParamMap())) {
            extraParams = loginInfo.getExtraParamMap();
        }

        // 获取主键。
        accountKey = loginInfo.getAccountKey();
        accountId = accountKey.getStringId();

        // 获取用户，并进行用户校验。
        account = accountMaintainService.getIfExists(accountKey);
        if (Objects.isNull(account)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, serialVersion,
                    new AccountNotExistsException(accountKey), account
            );
        }
        serialVersion = account.getSerialVersion();
        if (!account.isEnabled()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_DISABLED,
                    message, alarmLevel, extraParams, protectDetail, serialVersion,
                    new AccountDisabledException(accountKey), account
            );
        }

        // 进行密码校验。
        boolean passwordCorrect = BCrypt.checkpw(loginInfo.getPassword(), account.getPassword());
        // 调用账号保护器进行保护。
        Protector protector = protectLocalCacheHandler.get(accountKey);
        if (Objects.isNull(protector)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_INFO_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, serialVersion,
                    new ProtectorInfoNotExistsException(accountKey), account
            );
        }
        Protector.Context protectorContext = ctx.getBean(
                ProtectorContextImpl.class, loginHistoryMaintainService, loginParamRecordMaintainService,
                protectDetailRecordMaintainService, protectorVariableMaintainService, account, loginInfo,
                passwordCorrect
        );
        Protector.Response response = protector.execProtect(protectorContext);
        message = response.getMessage();
        alarmLevel = response.getAlarmLevel();

        // 根据密码校验和账号保护的综合逻辑，分析不通过条件。
        if (!response.isPassed()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_PROHIBITED,
                    message, alarmLevel, extraParams, protectDetail, serialVersion,
                    new ProtectorProhibitedException(accountKey), account
            );
        } else if (!passwordCorrect) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PASSWORD_INCORRECT,
                    message, alarmLevel, extraParams, protectDetail, serialVersion,
                    new PasswordIncorrectException(accountKey), account
            );
        }

        return new LoginComplex(
                accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PASSED,
                message, alarmLevel, extraParams, protectDetail, serialVersion,
                null, account
        );
    }

    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void processRecord(LoginComplex loginComplex) throws Exception {
        // 构建实体。
        LongIdKey loginHistoryKey = keyFetcher.fetchKey();
        LoginHistory loginHistory = new LoginHistory(
                loginHistoryKey, loginComplex.getAccountId(), loginComplex.getHappenedDate(),
                loginComplex.getResponseCode(), loginComplex.getMessage(), loginComplex.getAlarmLevel()
        );
        List<LoginParamRecord> loginParamRecords = new ArrayList<>();
        for (Map.Entry<String, String> entry : loginComplex.getExtraParams().entrySet()) {
            LoginParamRecord loginParamRecord = new LoginParamRecord(
                    new RecordKey(loginHistoryKey.getLongId(), entry.getKey()), entry.getValue()
            );
            loginParamRecords.add(loginParamRecord);
        }
        List<ProtectDetailRecord> protectDetailRecords = new ArrayList<>();
        for (Map.Entry<String, String> entry : loginComplex.getExtraParams().entrySet()) {
            ProtectDetailRecord protectDetailRecord = new ProtectDetailRecord(
                    new RecordKey(loginHistoryKey.getLongId(), entry.getKey()), entry.getValue()
            );
            protectDetailRecords.add(protectDetailRecord);
        }

        // 批量插入。
        loginHistoryMaintainService.insert(loginHistory);
        loginParamRecordMaintainService.batchInsert(loginParamRecords);
        protectDetailRecordMaintainService.batchInsert(protectDetailRecords);

        // 构建 LoginRecord，并进行事件推送。
        LoginHistoryRecordInfo loginHistoryRecordInfo = historyToRecord(
                loginHistory, loginParamRecords, protectDetailRecords
        );
        try {
            pushHandler.loginHistoryRecorded(loginHistoryRecordInfo);
        } catch (Exception e) {
            LOGGER.warn("推送登录历史被记录事件时发生异常, 事件将不会被推送, 异常信息如下: ", e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private LoginHistoryRecordInfo historyToRecord(
            LoginHistory loginHistory,
            List<LoginParamRecord> loginParamRecords, List<ProtectDetailRecord> protectDetailRecords
    ) {
        LongIdKey loginHistoryKey = loginHistory.getKey();
        String accountId = loginHistory.getAccountId();
        Date happenedDate = loginHistory.getHappenedDate();
        int responseCode = loginHistory.getResponseCode();
        String message = loginHistory.getMessage();
        Integer alarmLevel = loginHistory.getAlarmLevel();
        Map<String, String> extraParamMap = new HashMap<>();
        Map<String, String> protectDetailMap = new HashMap<>();

        for (LoginParamRecord loginParamRecord : loginParamRecords) {
            extraParamMap.put(loginParamRecord.getKey().getRecordId(), loginParamRecord.getValue());
        }
        for (ProtectDetailRecord protectDetailRecord : protectDetailRecords) {
            protectDetailMap.put(protectDetailRecord.getKey().getRecordId(), protectDetailRecord.getValue());
        }

        return new LoginHistoryRecordInfo(
                loginHistoryKey,
                accountId, happenedDate, responseCode, message, alarmLevel, extraParamMap, protectDetailMap
        );
    }
}
