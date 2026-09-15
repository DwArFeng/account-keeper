package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
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
import com.dwarfeng.subgrade.aop.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.generation.KeyGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class AccessProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessProcessor.class);

    private final ApplicationContext ctx;

    private final AccountMaintainService accountMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;
    private final LoginParamRecordMaintainService loginParamRecordMaintainService;
    private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;
    private final ProtectorVariableMaintainService protectorVariableMaintainService;

    private final ProtectLocalCacheHandler protectLocalCacheHandler;
    private final PushHandler pushHandler;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final TransactionWrapper transactionWrapper;

    @Value("${com.dwarfeng.acckeeper.acckeeper.login.dynamic.expire_duration}")
    private long dynamicLoginExpireDuration;

    public AccessProcessor(
            ApplicationContext ctx,
            AccountMaintainService accountMaintainService,
            LoginHistoryMaintainService loginHistoryMaintainService,
            LoginParamRecordMaintainService loginParamRecordMaintainService,
            ProtectDetailRecordMaintainService protectDetailRecordMaintainService,
            ProtectorVariableMaintainService protectorVariableMaintainService,
            ProtectLocalCacheHandler protectLocalCacheHandler,
            PushHandler pushHandler,
            KeyGenerator<LongIdKey> keyGenerator,
            TransactionWrapper transactionWrapper
    ) {
        this.ctx = ctx;
        this.accountMaintainService = accountMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.loginParamRecordMaintainService = loginParamRecordMaintainService;
        this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
        this.protectorVariableMaintainService = protectorVariableMaintainService;
        this.protectLocalCacheHandler = protectLocalCacheHandler;
        this.pushHandler = pushHandler;
        this.keyGenerator = keyGenerator;
        this.transactionWrapper = transactionWrapper;
    }

    /**
     * 处理登录。
     *
     * <p>
     * 与 {@link #processTrustedLogin} 对称，进行密码校验，适用于常规账号密码登录场景。<br>
     * 将实际密码校验结果 passwordCorrect 传入保护器，保护器可基于密码正确性及其他逻辑决定是否放行。
     *
     * @param loginType        登录类型。
     * @param dynamicLoginInfo 动态登录信息，loginType 为 DYNAMIC 时非 null。
     * @param staticLoginInfo  静态登录信息，loginType 为 STATIC 时非 null。
     * @return 登录复合结果。
     * @throws Exception 处理过程中的异常。
     */
    // 为了确保代码的可读性，此处不对代码结构进行优化。
    @SuppressWarnings({"ConstantValue", "DuplicatedCode"})
    @BehaviorAnalyse
    public LoginComplex processLogin(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) throws Exception {
        // 定义变量。
        StringIdKey accountKey;
        String accountId;
        Date happenedDate;
        String message = null;
        Integer alarmLevel = null;
        Map<String, String> extraParams;
        Map<String, String> protectDetail = Collections.emptyMap();
        Date expireDate = null;
        long serialVersion = 0;
        String loginRemark = parseLoginRemark(loginType, dynamicLoginInfo, staticLoginInfo);
        Account account;

        // 获取当前时间。
        happenedDate = new Date();
        // 获取额外登录参数。
        extraParams = parseExtraParams(loginType, dynamicLoginInfo, staticLoginInfo);
        // 获取主键。
        accountKey = parseAccountKey(loginType, dynamicLoginInfo, staticLoginInfo);
        accountId = accountKey.getStringId();

        // 获取用户，并进行用户校验。
        account = accountMaintainService.getIfExists(accountKey);
        if (Objects.isNull(account)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new AccountNotExistsException(accountKey), account
            );
        }
        serialVersion = account.getSerialVersion();
        if (!account.isEnabled()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_DISABLED,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new AccountDisabledException(accountKey), account
            );
        }

        // 获取密码。
        String password = parsePassword(loginType, dynamicLoginInfo, staticLoginInfo);

        // 进行密码校验。
        boolean passwordCorrect = BCrypt.checkpw(password, account.getPassword());
        // 调用账号保护器进行保护。
        Protector protector = protectLocalCacheHandler.get(accountKey);
        if (Objects.isNull(protector)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_INFO_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new ProtectorInfoNotExistsException(accountKey), account
            );
        }
        // 转换为不含密码的内部类，降低密码泄露风险。
        Protector.DynamicLoginInfo protectorDynamicLoginInfo = loginType == LoginType.DYNAMIC
                ? toProtectorDynamicLoginInfo(dynamicLoginInfo) : null;
        Protector.StaticLoginInfo protectorStaticLoginInfo = loginType == LoginType.STATIC
                ? toProtectorStaticLoginInfo(staticLoginInfo) : null;
        Protector.Context protectorContext = ctx.getBean(
                ProtectorContextImpl.class, loginHistoryMaintainService, loginParamRecordMaintainService,
                protectDetailRecordMaintainService, protectorVariableMaintainService, account, passwordCorrect,
                loginType, protectorDynamicLoginInfo, protectorStaticLoginInfo
        );
        Protector.Response response = protector.execProtect(protectorContext);
        message = response.message();
        alarmLevel = response.alarmLevel();

        // 根据密码校验和账号保护的综合逻辑，分析不通过条件。
        if (!response.passed()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_PROHIBITED,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new ProtectorProhibitedException(accountKey), account
            );
        } else if (!passwordCorrect) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PASSWORD_INCORRECT,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new PasswordIncorrectException(accountKey), account
            );
        }

        // 获取超时日期。
        expireDate = parseExpireDate(loginType, happenedDate, staticLoginInfo);

        return new LoginComplex(
                accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PASSED,
                message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion, loginRemark, null, account
        );
    }

    /**
     * 处理可信登录。
     *
     * <p>
     * 与 {@link #processLogin} 对称，但跳过密码校验，适用于第三方登录系统集成场景。<br>
     * 将 passwordCorrect 固定为 true 传入保护器，保护器仍可基于其他逻辑决定是否放行。
     *
     * @param loginType               登录类型。
     * @param trustedDynamicLoginInfo 可信动态登录信息，loginType 为 DYNAMIC 时非 null。
     * @param trustedStaticLoginInfo  可信静态登录信息，loginType 为 STATIC 时非 null。
     * @return 登录复合结果。
     * @throws Exception 处理过程中的异常。
     * @since 2.1.0
     */
    // 为了确保代码的可读性，此处不对代码结构进行优化。
    @SuppressWarnings({"ConstantValue", "DuplicatedCode"})
    @BehaviorAnalyse
    public LoginComplex processTrustedLogin(
            LoginType loginType, TrustedDynamicLoginInfo trustedDynamicLoginInfo,
            TrustedStaticLoginInfo trustedStaticLoginInfo
    ) throws Exception {
        StringIdKey accountKey;
        String accountId;
        Date happenedDate;
        String message = null;
        Integer alarmLevel = null;
        Map<String, String> extraParams;
        Map<String, String> protectDetail = Collections.emptyMap();
        Date expireDate = null;
        long serialVersion = 0;
        String loginRemark = parseTrustedLoginRemark(loginType, trustedDynamicLoginInfo, trustedStaticLoginInfo);
        Account account;

        happenedDate = new Date();
        extraParams = parseTrustedExtraParams(loginType, trustedDynamicLoginInfo, trustedStaticLoginInfo);
        accountKey = parseTrustedAccountKey(loginType, trustedDynamicLoginInfo, trustedStaticLoginInfo);
        accountId = accountKey.getStringId();

        account = accountMaintainService.getIfExists(accountKey);
        if (Objects.isNull(account)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new AccountNotExistsException(accountKey), account
            );
        }
        serialVersion = account.getSerialVersion();
        if (!account.isEnabled()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_ACCOUNT_DISABLED,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new AccountDisabledException(accountKey), account
            );
        }

        Protector protector = protectLocalCacheHandler.get(accountKey);
        if (Objects.isNull(protector)) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_INFO_NOT_EXISTS,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new ProtectorInfoNotExistsException(accountKey), account
            );
        }

        Protector.DynamicLoginInfo protectorDynamicLoginInfo = loginType == LoginType.DYNAMIC
                ? toProtectorDynamicLoginInfo(trustedDynamicLoginInfo) : null;
        Protector.StaticLoginInfo protectorStaticLoginInfo = loginType == LoginType.STATIC
                ? toProtectorStaticLoginInfo(trustedStaticLoginInfo) : null;
        Protector.Context protectorContext = ctx.getBean(
                ProtectorContextImpl.class, loginHistoryMaintainService, loginParamRecordMaintainService,
                protectDetailRecordMaintainService, protectorVariableMaintainService, account, true,
                loginType, protectorDynamicLoginInfo, protectorStaticLoginInfo
        );
        Protector.Response response = protector.execProtect(protectorContext);
        message = response.message();
        alarmLevel = response.alarmLevel();

        if (!response.passed()) {
            return new LoginComplex(
                    accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PROTECTOR_PROHIBITED,
                    message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion,
                    loginRemark, new ProtectorProhibitedException(accountKey), account
            );
        }

        expireDate = parseTrustedExpireDate(loginType, happenedDate, trustedStaticLoginInfo);

        return new LoginComplex(
                accountKey, accountId, happenedDate, Constants.LOGIN_RESPONSE_CODE_PASSED,
                message, alarmLevel, extraParams, protectDetail, expireDate, serialVersion, loginRemark, null, account
        );
    }

    private String parseTrustedLoginRemark(
            LoginType loginType, TrustedDynamicLoginInfo trustedDynamicLoginInfo,
            TrustedStaticLoginInfo trustedStaticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> trustedDynamicLoginInfo.getRemark();
            case STATIC -> trustedStaticLoginInfo.getRemark();
        };
    }

    @SuppressWarnings("DuplicatedCode")
    private Map<String, String> parseTrustedExtraParams(
            LoginType loginType, TrustedDynamicLoginInfo trustedDynamicLoginInfo,
            TrustedStaticLoginInfo trustedStaticLoginInfo
    ) {
        Map<String, String> result = switch (loginType) {
            case DYNAMIC -> trustedDynamicLoginInfo.getExtraParamMap();
            case STATIC -> trustedStaticLoginInfo.getExtraParamMap();
        };
        if (Objects.isNull(result)) {
            return Collections.emptyMap();
        }
        return result;
    }

    private StringIdKey parseTrustedAccountKey(
            LoginType loginType, TrustedDynamicLoginInfo trustedDynamicLoginInfo,
            TrustedStaticLoginInfo trustedStaticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> trustedDynamicLoginInfo.getAccountKey();
            case STATIC -> trustedStaticLoginInfo.getAccountKey();
        };
    }

    private Date parseTrustedExpireDate(
            LoginType loginType, Date happenedDate, TrustedStaticLoginInfo trustedStaticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> new Date(happenedDate.getTime() + dynamicLoginExpireDuration);
            case STATIC -> Optional.ofNullable(trustedStaticLoginInfo.getExpireDate()).orElse(new Date());
        };
    }

    private Protector.DynamicLoginInfo toProtectorDynamicLoginInfo(TrustedDynamicLoginInfo dto) {
        Map<String, String> extraParamMap = Objects.isNull(dto.getExtraParamMap())
                ? Collections.emptyMap() : dto.getExtraParamMap();
        return new Protector.DynamicLoginInfo(dto.getAccountKey(), dto.getRemark(), extraParamMap);
    }

    private Protector.StaticLoginInfo toProtectorStaticLoginInfo(TrustedStaticLoginInfo dto) {
        Map<String, String> extraParamMap = Objects.isNull(dto.getExtraParamMap())
                ? Collections.emptyMap() : dto.getExtraParamMap();
        return new Protector.StaticLoginInfo(
                dto.getAccountKey(), dto.getExpireDate(), dto.getRemark(), extraParamMap
        );
    }

    private String parseLoginRemark(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> dynamicLoginInfo.getRemark();
            case STATIC -> staticLoginInfo.getRemark();
        };
    }

    @SuppressWarnings("DuplicatedCode")
    private Map<String, String> parseExtraParams(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) {
        Map<String, String> result = switch (loginType) {
            case DYNAMIC -> dynamicLoginInfo.getExtraParamMap();
            case STATIC -> staticLoginInfo.getExtraParamMap();
        };
        if (Objects.isNull(result)) {
            return Collections.emptyMap();
        }
        return result;
    }

    private StringIdKey parseAccountKey(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> dynamicLoginInfo.getAccountKey();
            case STATIC -> staticLoginInfo.getAccountKey();
        };
    }

    private String parsePassword(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> dynamicLoginInfo.getPassword();
            case STATIC -> staticLoginInfo.getPassword();
        };
    }

    private Date parseExpireDate(
            LoginType loginType, Date happenedDate, StaticLoginInfo staticLoginInfo
    ) {
        return switch (loginType) {
            case DYNAMIC -> new Date(happenedDate.getTime() + dynamicLoginExpireDuration);
            case STATIC -> Optional.ofNullable(staticLoginInfo.getExpireDate()).orElse(new Date());
        };
    }

    private Protector.DynamicLoginInfo toProtectorDynamicLoginInfo(DynamicLoginInfo dto) {
        Map<String, String> extraParamMap = Objects.isNull(dto.getExtraParamMap())
                ? Collections.emptyMap() : dto.getExtraParamMap();
        return new Protector.DynamicLoginInfo(dto.getAccountKey(), dto.getRemark(), extraParamMap);
    }

    private Protector.StaticLoginInfo toProtectorStaticLoginInfo(StaticLoginInfo dto) {
        Map<String, String> extraParamMap = Objects.isNull(dto.getExtraParamMap())
                ? Collections.emptyMap() : dto.getExtraParamMap();
        return new Protector.StaticLoginInfo(
                dto.getAccountKey(), dto.getExpireDate(), dto.getRemark(), extraParamMap
        );
    }

    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void processRecord(LoginComplex loginComplex) throws Exception {
        // 构建实体。
        LongIdKey loginHistoryKey = keyGenerator.generate();
        LoginHistory loginHistory = new LoginHistory(
                loginHistoryKey, loginComplex.accountId(), loginComplex.happenedDate(),
                loginComplex.responseCode(), loginComplex.message(), loginComplex.alarmLevel(),
                loginComplex.loginRemark()
        );
        List<LoginParamRecord> loginParamRecords = new ArrayList<>();
        for (Map.Entry<String, String> entry : loginComplex.extraParams().entrySet()) {
            LoginParamRecord loginParamRecord = new LoginParamRecord(
                    new RecordKey(loginHistoryKey.getLongId(), entry.getKey()), entry.getValue()
            );
            loginParamRecords.add(loginParamRecord);
        }
        List<ProtectDetailRecord> protectDetailRecords = new ArrayList<>();
        for (Map.Entry<String, String> entry : loginComplex.extraParams().entrySet()) {
            ProtectDetailRecord protectDetailRecord = new ProtectDetailRecord(
                    new RecordKey(loginHistoryKey.getLongId(), entry.getKey()), entry.getValue()
            );
            protectDetailRecords.add(protectDetailRecord);
        }

        // 插入数据。
        transactionWrapper.insertHistoryEntities(loginHistory, loginParamRecords, protectDetailRecords);

        // 构建 LoginRecord，并进行事件推送。
        LoginHistoryRecordInfo loginHistoryRecordInfo = historyToRecord(
                loginHistory, loginParamRecords, protectDetailRecords
        );
        try {
            pushHandler.loginHistoryRecorded(loginHistoryRecordInfo);
        } catch (Exception e) {
            LOGGER.warn(
                    ImplMessages.message(ImplMessageKey.LOG_LOGIN_HISTORY_RECORDED_PUSH_FAILED), e
            );
        }
    }

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
        String loginRemark = loginHistory.getLoginRemark();

        for (LoginParamRecord loginParamRecord : loginParamRecords) {
            extraParamMap.put(loginParamRecord.getKey().getRecordId(), loginParamRecord.getValue());
        }
        for (ProtectDetailRecord protectDetailRecord : protectDetailRecords) {
            protectDetailMap.put(protectDetailRecord.getKey().getRecordId(), protectDetailRecord.getValue());
        }

        return new LoginHistoryRecordInfo(
                loginHistoryKey, accountId, happenedDate, responseCode, message, alarmLevel, extraParamMap,
                protectDetailMap, loginRemark
        );
    }

    @Component
    public static class TransactionWrapper {

        private final LoginHistoryMaintainService loginHistoryMaintainService;
        private final LoginParamRecordMaintainService loginParamRecordMaintainService;
        private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;

        public TransactionWrapper(
                LoginHistoryMaintainService loginHistoryMaintainService,
                LoginParamRecordMaintainService loginParamRecordMaintainService,
                ProtectDetailRecordMaintainService protectDetailRecordMaintainService
        ) {
            this.loginHistoryMaintainService = loginHistoryMaintainService;
            this.loginParamRecordMaintainService = loginParamRecordMaintainService;
            this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
        }

        @Transactional(
                transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class,
                propagation = Propagation.REQUIRES_NEW
        )
        public void insertHistoryEntities(
                LoginHistory loginHistory, List<LoginParamRecord> loginParamRecords,
                List<ProtectDetailRecord> protectDetailRecords
        ) throws Exception {
            loginHistoryMaintainService.insert(loginHistory);
            loginParamRecordMaintainService.batchInsert(loginParamRecords);
            protectDetailRecordMaintainService.batchInsert(protectDetailRecords);
        }
    }
}
