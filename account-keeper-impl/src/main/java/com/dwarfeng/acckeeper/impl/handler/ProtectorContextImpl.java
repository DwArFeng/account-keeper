package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectDetailRecordMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectorVariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class ProtectorContextImpl implements Protector.Context {

    private final LoginHistoryMaintainService loginHistoryMaintainService;
    private final LoginParamRecordMaintainService loginParamRecordMaintainService;
    private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;
    private final ProtectorVariableMaintainService protectorVariableMaintainService;

    private final Account account;
    private final boolean passwordCorrect;

    private final LoginType loginType;
    private final DynamicLoginInfo dynamicLoginInfo;
    private final StaticLoginInfo staticLoginInfo;

    public ProtectorContextImpl(
            LoginHistoryMaintainService loginHistoryMaintainService,
            LoginParamRecordMaintainService loginParamRecordMaintainService,
            ProtectDetailRecordMaintainService protectDetailRecordMaintainService,
            ProtectorVariableMaintainService protectorVariableMaintainService,
            Account account,
            boolean passwordCorrect,
            LoginType loginType,
            DynamicLoginInfo dynamicLoginInfo,
            StaticLoginInfo staticLoginInfo
    ) {
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.loginParamRecordMaintainService = loginParamRecordMaintainService;
        this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
        this.protectorVariableMaintainService = protectorVariableMaintainService;
        this.account = account;
        this.loginType = loginType;
        this.dynamicLoginInfo = dynamicLoginInfo;
        this.staticLoginInfo = staticLoginInfo;
        this.passwordCorrect = passwordCorrect;
    }

    @Override
    public Protector.AccountMeta getAccountMeta() {
        return new Protector.AccountMeta(
                account.getSerialVersion(), account.getRegisteredDate(), account.getLoginCount(),
                account.getPasswordUpdateCount(), account.getDeriveCount()
        );
    }

    @Override
    @Deprecated
    public LoginInfo getLoginInfo() {
        StringIdKey accountKey;
        String password;
        Map<String, String> extraParamMap;
        switch (loginType) {
            case DYNAMIC:
                accountKey = dynamicLoginInfo.getAccountKey();
                password = dynamicLoginInfo.getPassword();
                extraParamMap = dynamicLoginInfo.getExtraParamMap();
                break;
            case STATIC:
                accountKey = staticLoginInfo.getAccountKey();
                password = staticLoginInfo.getPassword();
                extraParamMap = staticLoginInfo.getExtraParamMap();
                break;
            default:
                throw new AssertionError("未知的登录类型: " + loginType);
        }
        return new LoginInfo(accountKey, password, extraParamMap);
    }

    @Override
    public boolean isDynamicLogin() {
        return loginType == LoginType.DYNAMIC;
    }

    @Override
    public DynamicLoginInfo getDynamicLoginInfo() {
        return dynamicLoginInfo;
    }

    @Override
    public boolean isStaticLogin() {
        return loginType == LoginType.STATIC;
    }

    @Override
    public StaticLoginInfo getStaticLoginInfo() {
        return staticLoginInfo;
    }

    @Override
    public List<Protector.LoginRecord> inspectRecord(Protector.RecordInspectParam param)
            throws ProtectorException {
        try {
            String accountId = account.getKey().getStringId();

            // 获取查询参数。
            Date startDate = Optional.ofNullable(param.getStartDate()).orElse(new Date(0));
            Date endDate = Optional.of(param.getEndDate()).orElse(new Date());
            List<Integer> responseCodes = param.getResponseCodes();

            // 根据 maxResult 确定分页查询参数。
            Integer maxResult = param.getMaxResult();
            List<LoginHistory> loginHistories;
            if (Objects.isNull(maxResult)) {
                loginHistories = loginHistoryMaintainService.lookupAsList(
                        LoginHistoryMaintainService.PROTECTOR_INSPECT,
                        new Object[]{accountId, startDate, endDate, responseCodes}
                );
            } else {
                loginHistories = loginHistoryMaintainService.lookupAsList(
                        LoginHistoryMaintainService.PROTECTOR_INSPECT,
                        new Object[]{accountId, startDate, endDate, responseCodes},
                        new PagingInfo(0, maxResult)
                );
            }

            // 根据查询结果，将每个登录历史转换为登录记录。
            List<Protector.LoginRecord> result = new ArrayList<>();
            for (LoginHistory loginHistory : loginHistories) {
                Protector.LoginRecord loginRecord = historyToRecord(loginHistory);
                result.add(loginRecord);
            }

            // 返回结果。
            return result;
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private Protector.LoginRecord historyToRecord(LoginHistory loginHistory) throws Exception {
        String accountId = loginHistory.getAccountId();
        Date happenedDate = loginHistory.getHappenedDate();
        int responseCode = loginHistory.getResponseCode();
        String message = loginHistory.getMessage();
        Integer alarmLevel = loginHistory.getAlarmLevel();
        Map<String, String> extraParamMap = new HashMap<>();
        Map<String, String> protectDetailMap = new HashMap<>();

        List<LoginParamRecord> loginParamRecords = loginParamRecordMaintainService.lookupAsList(
                LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY, new Object[]{loginHistory.getKey()}
        );
        for (LoginParamRecord loginParamRecord : loginParamRecords) {
            extraParamMap.put(loginParamRecord.getKey().getRecordId(), loginParamRecord.getValue());
        }

        List<ProtectDetailRecord> protectDetailRecords = protectDetailRecordMaintainService.lookupAsList(
                ProtectDetailRecordMaintainService.CHILD_FOR_LOGIN_HISTORY, new Object[]{loginHistory.getKey()}
        );
        for (ProtectDetailRecord protectDetailRecord : protectDetailRecords) {
            protectDetailMap.put(protectDetailRecord.getKey().getRecordId(), protectDetailRecord.getValue());
        }

        return new Protector.LoginRecord(
                accountId, happenedDate, responseCode, message, alarmLevel, extraParamMap, protectDetailMap
        );
    }

    @Override
    public boolean existsVariable(String variableId) throws ProtectorException {
        try {
            String accountId = account.getKey().getStringId();

            return protectorVariableMaintainService.exists(new ProtectorVariableKey(accountId, variableId));
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }

    @Override
    public Protector.Variable getVariable(String variableId) throws ProtectorException {
        try {
            String accountId = account.getKey().getStringId();

            ProtectorVariable protectorVariable = protectorVariableMaintainService.getIfExists(
                    new ProtectorVariableKey(accountId, variableId)
            );
            return new Protector.Variable(protectorVariable.getValue(), protectorVariable.getRemark());
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }

    @Override
    public void setVariable(String variableId, Protector.Variable variable) throws ProtectorException {
        try {
            String accountId = account.getKey().getStringId();

            ProtectorVariable protectorVariable = new ProtectorVariable(
                    new ProtectorVariableKey(accountId, variableId), variable.getValue(), variable.getRemark()
            );
            protectorVariableMaintainService.insertOrUpdate(protectorVariable);
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }

    @Override
    public void removeVariable(String variableId) throws ProtectorException {
        try {
            String accountId = account.getKey().getStringId();

            protectorVariableMaintainService.deleteIfExists(new ProtectorVariableKey(accountId, variableId));
        } catch (Exception e) {
            throw new ProtectorException(e);
        }
    }

    @Override
    public boolean passwordCorrect() {
        return passwordCorrect;
    }
}
