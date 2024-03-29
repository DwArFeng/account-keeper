package com.dwarfeng.acckeeper.api.integration.subgrade;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordCheckInfo;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.handler.LoginHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 登录处理器的实现。
 *
 * @author DwArFeng
 * @since alpha-0.0.1
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
    public boolean checkPassword(StringIdKey accountKey, String password) throws HandlerException {
        try {
            return accountOperateService.checkPassword(new PasswordCheckInfo(accountKey, password));
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public LongIdKey login(StringIdKey accountKey, String password) throws HandlerException {
        try {
            DynamicLoginInfo loginInfo = new DynamicLoginInfo(
                    accountKey, password, StringUtils.EMPTY, Collections.emptyMap()
            );
            return loginService.dynamicLogin(loginInfo).getKey();
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void logout(LongIdKey idKey) throws HandlerException {
        try {
            loginService.logout(idKey);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean isLogin(LongIdKey idKey) throws HandlerException {
        try {
            return loginService.isLogin(idKey);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void postpone(LongIdKey idKey) throws HandlerException {
        try {
            loginService.postpone(idKey);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
