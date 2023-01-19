package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.handler.LoginHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LoginHandlerImpl implements LoginHandler {

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;

    private final HandlerValidator handlerValidator;

    private final KeyFetcher<LongIdKey> keyFetcher;

    private final LoginProcessor loginProcessor;

    @Value("${acckeeper.login.expire}")
    private long expireTimeout;

    public LoginHandlerImpl(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService,
            HandlerValidator handlerValidator,
            KeyFetcher<LongIdKey> keyFetcher,
            LoginProcessor loginProcessor
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
        this.handlerValidator = handlerValidator;
        this.keyFetcher = keyFetcher;
        this.loginProcessor = loginProcessor;
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
            // 处理登陆主逻辑。
            LoginComplex loginComplex = loginProcessor.processLogin(loginInfo);

            // 记录登陆历史。
            // 登陆历史的记录很重要，因为登陆历史会作为上下文，传入保护器，参与后续的账号保护逻辑。
            // 因此，如果登陆历史记录失败了，那么登陆过程应该中止，不应该记录日志并继续执行。
            loginProcessor.processRecord(loginComplex);

            // 如果响应中的异常字段不为 null，则抛出对应的异常。
            if (Objects.nonNull(loginComplex.getException())) {
                throw loginComplex.getException();
            }

            // 代码执行至此处，说明登陆正常，可以为本次登陆请求创建登陆状态。
            // 根据账户实体构造登录状态实体。
            StringIdKey accountKey = loginComplex.getAccountKey();
            Date expireDate = new Date(
                    loginComplex.getHappenedDate().getTime() + expireTimeout
            );
            long serialVersion = loginComplex.getSerialVersion();
            LoginState loginState = new LoginState(keyFetcher.fetchKey(), accountKey, expireDate, serialVersion);
            // 插入登陆实体。
            loginStateMaintainService.insertOrUpdate(loginState);

            // 自增账户的登陆次数。
            Account account = loginComplex.getAccount();
            account.setLoginCount(account.getLoginCount() + 1);
            accountMaintainService.update(account);

            // 返回结果。
            return loginState;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
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
