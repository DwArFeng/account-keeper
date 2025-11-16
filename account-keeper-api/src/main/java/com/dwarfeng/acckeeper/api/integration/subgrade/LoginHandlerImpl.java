package com.dwarfeng.acckeeper.api.integration.subgrade;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
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

    private final AccessService accessService;
    private final AccountOperateService accountOperateService;

    public LoginHandlerImpl(AccessService accessService, AccountOperateService accountOperateService) {
        this.accessService = accessService;
        this.accountOperateService = accountOperateService;
    }

    /**
     * 校验密码。
     *
     * @param userId   用户 ID。
     * @param password 密码。
     * @return 密码是否正确。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public boolean checkPassword(String userId, String password) throws HandlerException {
        try {
            return accountOperateService.checkPassword(new PasswordCheckInfo(userIdToAccountKey(userId), password));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 登录。
     *
     * @param userId   用户 ID。
     * @param password 密码。
     * @return 登录状态键的字符串 ID。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public String login(String userId, String password) throws HandlerException {
        try {
            DynamicLoginInfo loginInfo = new DynamicLoginInfo(
                    userIdToAccountKey(userId), password, StringUtils.EMPTY, Collections.emptyMap()
            );
            return accessService.dynamicLogin(loginInfo).getLoginStateKey().getStringId();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 登出。
     *
     * @param loginId 登录状态键的字符串 ID。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public void logout(String loginId) throws HandlerException {
        try {
            accessService.logout(new LogoutInfo(new StringIdKey(loginId)));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 判断指定登录 ID 是否处于有效的登录状态。
     *
     * @param loginId 登录状态键的字符串 ID。
     * @return 是否处于有效登录状态。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public boolean isLogin(String loginId) throws HandlerException {
        try {
            return accessService.authInspect(new AuthInspectInfo(new StringIdKey(loginId))).isLogin();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 延期指定登录 ID 对应的登录状态。
     *
     * @param loginId 登录状态键的字符串 ID。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public void postpone(String loginId) throws HandlerException {
        try {
            accessService.postpone(new PostponeInfo(new StringIdKey(loginId)));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 登录（带额外参数）。
     *
     * @param userId        用户 ID。
     * @param password      密码。
     * @param extraParamMap 额外参数映射。
     * @return 登录状态键的字符串 ID。
     * @throws HandlerException 处理器异常。
     */
    @Override
    public String login(String userId, String password, Map<String, String> extraParamMap) throws HandlerException {
        try {
            DynamicLoginInfo loginInfo = new DynamicLoginInfo(
                    userIdToAccountKey(userId), password, StringUtils.EMPTY,
                    extraParamMap != null ? extraParamMap : Collections.emptyMap()
            );
            return accessService.dynamicLogin(loginInfo).getLoginStateKey().getStringId();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private StringIdKey userIdToAccountKey(String userId) {
        return new StringIdKey(userId);
    }
}
