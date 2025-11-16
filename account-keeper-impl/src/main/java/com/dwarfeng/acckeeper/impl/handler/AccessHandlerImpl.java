package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.exception.LoginStateKeyConflictException;
import com.dwarfeng.acckeeper.stack.handler.AccessHandler;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccessHandlerImpl implements AccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessHandlerImpl.class);

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;

    private final LoginStateKeyGenerateHandler loginStateKeyGenerateHandler;

    private final AccessProcessor accessProcessor;

    private final HandlerValidator handlerValidator;

    @Value("${acckeeper.login.dynamic.expire_duration}")
    private long dynamicLoginExpireDuration;

    public AccessHandlerImpl(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService,
            LoginStateKeyGenerateHandler loginStateKeyGenerateHandler,
            AccessProcessor accessProcessor,
            HandlerValidator handlerValidator
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
        this.loginStateKeyGenerateHandler = loginStateKeyGenerateHandler;
        this.accessProcessor = accessProcessor;
        this.handlerValidator = handlerValidator;
    }

    @Override
    @BehaviorAnalyse
    public AuthInspectResult authInspect(AuthInspectInfo info) throws HandlerException {
        try {
            // 展开参数。
            StringIdKey loginStateKey = info.getLoginStateKey();

            // 获取登录状态实体（可能为 null）。
            LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);

            // 如果登录状态不存在，直接返回登录失败相应的结果。
            if (Objects.isNull(loginState)) {
                return new AuthInspectResult(false, null);
            }

            // 如果过了登录超时期，则返回登录失败相应的结果。
            long expireTimestamp = Optional.ofNullable(loginState.getExpireDate()).map(Date::getTime).orElse(0L);
            long currentTimestamp = System.currentTimeMillis();
            if (expireTimestamp < currentTimestamp) {
                return new AuthInspectResult(false, loginState);
            }

            // 根据登录状态判断账户维护服务中是否有对应的账户，如果没有，则返回登录失败相应的结果。
            if (!accountMaintainService.exists(loginState.getAccountKey())) {
                return new AuthInspectResult(false, loginState);
            }

            // 获取账户实体。
            Account account = accountMaintainService.get(loginState.getAccountKey());

            // 如果账户实体被禁用，则返回登录失败相应的结果。
            if (!account.isEnabled()) {
                return new AuthInspectResult(false, loginState);
            }

            // 如果账户实体的序列版本等于登录状态的序列版本，则返回登录成功相应的结果，否则返回登录失败相应的结果。
            return new AuthInspectResult(account.getSerialVersion() == loginState.getSerialVersion(), loginState);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws HandlerException {
        try {
            LoginState loginState = login0(LoginType.DYNAMIC, info, null);
            return new DynamicLoginResult(
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
    public StaticLoginResult staticLogin(StaticLoginInfo info) throws HandlerException {
        try {
            LoginState loginState = login0(LoginType.STATIC, null, info);
            return new StaticLoginResult(
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
    private LoginState login0(LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo)
            throws Exception {
        // 处理登录主逻辑。
        LoginComplex loginComplex = accessProcessor.processLogin(loginType, dynamicLoginInfo, staticLoginInfo);

        // 记录登录历史。
        // 登录历史的记录很重要，因为登录历史会作为上下文，传入保护器，参与后续的账号保护逻辑。
        // 因此，如果登录历史记录失败了，那么登录过程应该中止，不应该记录日志并继续执行。
        accessProcessor.processRecord(loginComplex);

        // 如果响应中的异常字段不为 null，则抛出对应的异常。
        if (Objects.nonNull(loginComplex.getException())) {
            throw loginComplex.getException();
        }

        // 代码执行至此处，说明登录正常，可以为本次登录请求创建登录状态。
        // 根据账户实体构造登录状态实体。
        StringIdKey key = generateUniqueLoginStateKey();
        StringIdKey accountKey = loginComplex.getAccountKey();
        Date happenedDate = loginComplex.getHappenedDate();
        Date expireDate = loginComplex.getExpireDate();
        String remark = parseRemark(loginType, dynamicLoginInfo, staticLoginInfo);
        long serialVersion = loginComplex.getSerialVersion();
        int type = parseLoginStateType(loginType);
        LoginState loginState = new LoginState(key, accountKey, expireDate, serialVersion, happenedDate, type, remark);
        // 插入登录状态实体。
        loginStateMaintainService.insertOrUpdate(loginState);

        // 自增账户的登录次数。
        Account account = loginComplex.getAccount();
        account.setLoginCount(account.getLoginCount() + 1);
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

    private String parseRemark(
            LoginType loginType, DynamicLoginInfo dynamicLoginInfo, StaticLoginInfo staticLoginInfo
    ) {
        switch (loginType) {
            case DYNAMIC:
                return dynamicLoginInfo.getRemark();
            case STATIC:
                return staticLoginInfo.getRemark();
            default:
                throw new AssertionError("未知的登录类型: " + loginType);
        }
    }

    private int parseLoginStateType(LoginType loginType) {
        switch (loginType) {
            case DYNAMIC:
                return Constants.LOGIN_STATE_TYPE_DYNAMIC;
            case STATIC:
                return Constants.LOGIN_STATE_TYPE_STATIC;
            default:
                throw new AssertionError("未知的登录类型: " + loginType);
        }
    }

    @Override
    @BehaviorAnalyse
    public void logout(LogoutInfo info) throws HandlerException {
        try {
            // 展开参数。
            StringIdKey loginStateKey = info.getLoginStateKey();

            // 获取登录状态实体（可能为 null）。
            LoginState loginState = loginStateMaintainService.getIfExists(loginStateKey);

            // 如果登录状态实体存在，则清除实体，如果不存在，也不做特别的动作。
            if (Objects.nonNull(loginState)) {
                loginStateMaintainService.delete(loginState.getKey());
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public PostponeResult postpone(PostponeInfo info) throws HandlerException {
        try {
            // 展开参数。
            StringIdKey loginStateKey = info.getLoginStateKey();

            // 确认登录状态存在。
            handlerValidator.makeSureLoginStateExists(loginStateKey);

            // 确认登录状态没有过期。
            handlerValidator.makeSureLoginStateNotExpired(loginStateKey);

            // 获取其中的账户主键。
            LoginState loginState = loginStateMaintainService.get(loginStateKey);
            StringIdKey accountKey = loginState.getAccountKey();

            // 确认账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 确认用户没有被禁用。
            handlerValidator.makeSureAccountNotDisabled(accountKey);

            // 确认用户的序列编码与登录状态编码一致。
            handlerValidator.makeSureSerialNumberConsistent(loginStateKey);

            // 如果登录状态类型是静态类型，则不做任何动作。
            if (Objects.equals(Constants.LOGIN_STATE_TYPE_STATIC, loginState.getType())) {
                LOGGER.debug("登录状态 {} 为静态类型, 不做任何动作", loginStateKey);
                return loginStateToPostponeResult(loginState);
            }

            // 更新登录状态实体，设置新的超时日期。
            loginState.setExpireDate(new Date(System.currentTimeMillis() + dynamicLoginExpireDuration));

            // 将新的实体推送到缓存中，并更新缓存的超时时间，最后返回结果。
            loginStateMaintainService.insertOrUpdate(loginState);
            return loginStateToPostponeResult(loginState);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private PostponeResult loginStateToPostponeResult(LoginState loginState) {
        return new PostponeResult(
                loginState.getKey(),
                loginState.getAccountKey(),
                loginState.getExpireDate(),
                loginState.getGeneratedDate(),
                loginState.getType(),
                loginState.getRemark()
        );
    }

    @Override
    @BehaviorAnalyse
    public void kick(KickInfo info) throws HandlerException {
        try {
            // 展开参数。
            StringIdKey loginStateKey = info.getLoginStateKey();
            StringIdKey accountKey = info.getAccountKey();

            // 踢出逻辑处理。
            if (Objects.nonNull(loginStateKey)) {
                kickLoginStateKey(loginStateKey);
            } else if (Objects.nonNull(accountKey)) {
                kickAccountKey(accountKey);
            } else {
                kickAll();
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void kickLoginStateKey(StringIdKey loginStateKey) throws Exception {
        loginStateMaintainService.deleteIfExists(loginStateKey);
    }

    private void kickAccountKey(StringIdKey accountKey) throws Exception {
        List<StringIdKey> loginStateKeys = loginStateMaintainService.lookupAsList(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{accountKey}
        ).stream().map(LoginState::getKey).collect(Collectors.toList());
        loginStateMaintainService.batchDelete(loginStateKeys);
    }

    private void kickAll() throws Exception {
        List<StringIdKey> loginStateKeys = loginStateMaintainService.lookupAsList()
                .stream().map(LoginState::getKey).collect(Collectors.toList());
        loginStateMaintainService.batchDelete(loginStateKeys);
    }
}
