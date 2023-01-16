package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.exception.AccountDisabledException;
import com.dwarfeng.acckeeper.stack.exception.AccountNotExistsException;
import com.dwarfeng.acckeeper.stack.exception.PasswordIncorrectException;
import com.dwarfeng.acckeeper.stack.handler.LocateHandler;
import com.dwarfeng.acckeeper.stack.handler.LoginHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LoginHandlerImpl implements LoginHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandlerImpl.class);

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;

    private final LocateHandler locateHandler;

    private final HandlerValidator handlerValidator;

    private final KeyFetcher<LongIdKey> keyFetcher;

    @Value("${acckeeper.login.expire}")
    private long expireTimeout;

    public LoginHandlerImpl(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService,
            LoginHistoryMaintainService loginHistoryMaintainService,
            LocateHandler locateHandler,
            HandlerValidator handlerValidator,
            KeyFetcher<LongIdKey> keyFetcher
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.locateHandler = locateHandler;
        this.handlerValidator = handlerValidator;
        this.keyFetcher = keyFetcher;
    }

    @Override
    @BehaviorAnalyse
    public boolean isLogin(LongIdKey loginStateKey) throws HandlerException {
        try {
            // 判断登录状态缓存中是否有登录状态实体，如果没有，则返回 false。
            if (!loginStateMaintainService.exists(loginStateKey)) {
                return false;
            }

            // 获取登录状态实体。
            LoginState loginState = loginStateMaintainService.get(loginStateKey);

            // 如果过了登录超时期，则返回 false。
            long expireTimestamp = Optional.ofNullable(loginState.getExpireDate()).map(Date::getTime).orElse(0L);
            long currentTimestamp = System.currentTimeMillis();
            if (expireTimestamp < currentTimestamp) {
                return false;
            }

            // 根据登录状态判断账户维护服务中是否有对应的账户，如果没有，则返回 false。
            if (!accountMaintainService.exists(loginState.getAccountKey())) {
                return false;
            }

            // 获取账户实体。
            Account account = accountMaintainService.get(loginState.getAccountKey());

            // 如果账户实体被禁用，则返回 false。
            if (!account.isEnabled()) {
                return false;
            }

            // 如果账户实体的序列版本等于登录状态的序列版本，则返回 true，否则返回 false。
            return account.getSerialVersion() == loginState.getSerialVersion();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public LoginState login(LoginInfo loginInfo) throws HandlerException {
        try {
            // 获取主键。
            StringIdKey accountKey = loginInfo.getAccountKey();

            // 解析 IP 地址。
            String ipAddress = loginInfo.getIpAddress();
            LocateHandler.LocateResult locateResult = locateHandler.locate(ipAddress);
            String location = locateResult.getLocation();
            Double latitude = locateResult.getLatitude();
            Double longitude = locateResult.getLongitude();

            // 获取当前时间。
            Date currentDate = new Date();

            // 确定主键对应的账户存在。
            if (handlerValidator.isAccountNotExists(accountKey)) {
                LoginHistory loginHistory = new LoginHistory(
                        null, null, currentDate, ipAddress, location, latitude, longitude,
                        Constants.LOGIN_RESPONSE_CODE_ACCOUNT_NOT_EXISTS, accountKey.getStringId()
                );
                insertLoginHistory(loginHistory);
                throw new AccountNotExistsException(accountKey);
            }

            // 确定账户未被禁用。
            if (handlerValidator.isAccountDisabled(accountKey)) {
                LoginHistory loginHistory = new LoginHistory(
                        null, accountKey, currentDate, ipAddress, location, latitude, longitude,
                        Constants.LOGIN_RESPONSE_CODE_ACCOUNT_DISABLED, null
                );
                insertLoginHistory(loginHistory);
                throw new AccountDisabledException(accountKey);
            }

            // 确认密码正确。
            String password = loginInfo.getPassword();
            if (handlerValidator.isPasswordIncorrect(accountKey, password)) {
                LoginHistory loginHistory = new LoginHistory(
                        null, accountKey, currentDate, ipAddress, location, latitude, longitude,
                        Constants.LOGIN_RESPONSE_CODE_PASSWORD_INCORRECT, null
                );
                insertLoginHistory(loginHistory);
                throw new PasswordIncorrectException(accountKey);
            }

            // 获取账户实体，并根据账户实体构造登录状态实体。
            Account account = accountMaintainService.get(accountKey);
            LoginState loginState = new LoginState(
                    keyFetcher.fetchKey(), accountKey, new Date(System.currentTimeMillis() + expireTimeout),
                    account.getSerialVersion()
            );

            // 插入登录状态实体。
            loginStateMaintainService.insertOrUpdate(loginState);

            // 插入登录历史。
            LoginHistory loginHistory = new LoginHistory(
                    null, accountKey, currentDate, ipAddress, location, latitude, longitude,
                    Constants.LOGIN_RESPONSE_CODE_PASSED, null
            );
            insertLoginHistory(loginHistory);

            // 返回结果。
            return loginState;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void insertLoginHistory(LoginHistory loginHistory) {
        try {
            loginHistoryMaintainService.insertOrUpdate(loginHistory);
        } catch (Exception e) {
            LOGGER.warn("登录历史 " + loginHistory + "插入失败, 异常信息如下: ", e);
        }
    }

    @Override
    @BehaviorAnalyse
    public LoginState getLoginState(LongIdKey loginStateKey) throws HandlerException {
        try {
            // 确认登录状态主键存在。
            handlerValidator.makeSureLoginStateExists(loginStateKey);

            // 获取登录状态，并返回。
            return loginStateMaintainService.get(loginStateKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void logout(LongIdKey loginStateKey) throws HandlerException {
        try {
            // 如果登录状态缓存中有实体，则清除实体，如果不存在，也不做特别的动作。
            if (loginStateMaintainService.exists(loginStateKey)) {
                loginStateMaintainService.delete(loginStateKey);
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public LoginState postpone(LongIdKey loginStateKey) throws HandlerException {
        try {
            // 确认登录状态存在。
            handlerValidator.makeSureLoginStateExists(loginStateKey);

            // 确认登录状态没有过期。
            handlerValidator.makeSureLoginStateNotExpired(loginStateKey);

            // 获取登录状态，并获取其中的账户主键。
            LoginState loginState = loginStateMaintainService.get(loginStateKey);
            StringIdKey accountKey = loginState.getAccountKey();

            // 确认账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 确认用户没有被禁用。
            handlerValidator.makeSureAccountNotDisabled(accountKey);

            // 确认用户的序列编码与登录状态编码一致。
            handlerValidator.makeSureSerialNumberConsistent(loginStateKey);

            // 更新登录状态实体，设置新的超时日期。
            loginState.setExpireDate(new Date(System.currentTimeMillis() + expireTimeout));

            // 将新的实体推送到缓存中，并更新缓存的超时时间，最后返回结果。
            loginStateMaintainService.insertOrUpdate(loginState);
            return loginState;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public List<LoginState> inspectLoginStateByKey(LongIdKey loginStateKey) throws HandlerException {
        try {
            LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);
            if (Objects.isNull(loginState)) {
                return Collections.emptyList();
            } else {
                return Collections.singletonList(loginState);
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public List<LoginState> inspectLoginStateByAccount(StringIdKey accountKey) throws HandlerException {
        try {
            return loginStateMaintainService.lookupAsList(
                    LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{accountKey}
            );
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public List<LoginState> inspectAllLoginState() throws HandlerException {
        try {
            return loginStateMaintainService.lookupAsList();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void kickByLoginState(LongIdKey loginStateKey) throws HandlerException {
        try {
            LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);
            if (Objects.nonNull(loginState)) {
                loginStateMaintainService.delete(loginState.getKey());
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void kickByAccount(StringIdKey accountKey) throws HandlerException {
        try {
            List<LongIdKey> loginStateKeys = loginStateMaintainService.lookupAsList(
                    LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{accountKey}
            ).stream().map(LoginState::getKey).collect(Collectors.toList());
            loginStateMaintainService.batchDelete(loginStateKeys);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void kickAll() throws HandlerException {
        try {
            List<LongIdKey> loginStateKeys = loginStateMaintainService.lookupAsList()
                    .stream().map(LoginState::getKey).collect(Collectors.toList());
            loginStateMaintainService.batchDelete(loginStateKeys);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
