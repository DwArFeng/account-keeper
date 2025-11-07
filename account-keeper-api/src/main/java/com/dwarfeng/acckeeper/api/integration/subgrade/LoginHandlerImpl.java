package com.dwarfeng.acckeeper.api.integration.subgrade;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordCheckInfo;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.LoginHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * 登录处理器的实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LoginHandlerImpl implements LoginHandler {

    private final LoginService loginService;
    private final AccountOperateService accountOperateService;

    public LoginHandlerImpl(LoginService loginService, AccountOperateService accountOperateService) {
        this.loginService = loginService;
        this.accountOperateService = accountOperateService;
    }

    @Override
    public boolean checkPassword(String userId, String password) throws HandlerException {
        try {
            return accountOperateService.checkPassword(new PasswordCheckInfo(userIdToAccountKey(userId), password));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public String login(String userId, String password) throws HandlerException {
        try {
            DynamicLoginInfo loginInfo = new DynamicLoginInfo(
                    userIdToAccountKey(userId), password, StringUtils.EMPTY, Collections.emptyMap()
            );
            return String.valueOf(loginService.dynamicLogin(loginInfo).getKey().getLongId());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void logout(String loginId) throws HandlerException {
        try {
            loginService.logout(loginIdToLongIdKey(loginId));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public boolean isLogin(String loginId) throws HandlerException {
        try {
            return loginService.isLogin(loginIdToLongIdKey(loginId));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public void postpone(String loginId) throws HandlerException {
        try {
            loginService.postpone(loginIdToLongIdKey(loginId));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    public String login(String userId, String password, Map<String, String> extraParamMap) throws HandlerException {
        try {
            DynamicLoginInfo loginInfo = new DynamicLoginInfo(
                    userIdToAccountKey(userId), password, StringUtils.EMPTY,
                    extraParamMap != null ? extraParamMap : Collections.emptyMap()
            );
            return String.valueOf(loginService.dynamicLogin(loginInfo).getKey().getLongId());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private StringIdKey userIdToAccountKey(String userId) {
        return new StringIdKey(userId);
    }

    private LongIdKey loginIdToLongIdKey(String loginId) {
        return new LongIdKey(Long.parseLong(loginId));
    }
}
