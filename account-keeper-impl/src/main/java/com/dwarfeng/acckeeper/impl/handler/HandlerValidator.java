package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.exception.*;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class HandlerValidator {

    private final AccountMaintainService accountMaintainService;
    private final LoginStateMaintainService loginStateMaintainService;

    public HandlerValidator(
            AccountMaintainService accountMaintainService,
            LoginStateMaintainService loginStateMaintainService
    ) {
        this.accountMaintainService = accountMaintainService;
        this.loginStateMaintainService = loginStateMaintainService;
    }

    public void makeSureAccountNotExists(StringIdKey accountKey) throws HandlerException {
        try {
            if (accountMaintainService.exists(accountKey)) {
                throw new AccountAlreadyExistedException(accountKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public boolean isAccountNotExists(StringIdKey accountKey) throws HandlerException {
        try {
            return Objects.isNull(accountKey) || !accountMaintainService.exists(accountKey);
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureAccountExists(StringIdKey accountKey) throws HandlerException {
        if (isAccountNotExists(accountKey)) {
            throw new AccountNotExistsException(accountKey);
        }
    }

    public boolean isPasswordIncorrect(StringIdKey accountKey, String password) throws HandlerException {
        try {
            Account account = accountMaintainService.get(accountKey);
            return !BCrypt.checkpw(password, account.getPassword());
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSurePasswordCorrect(StringIdKey accountKey, String password) throws HandlerException {
        if (isPasswordIncorrect(accountKey, password)) {
            throw new PasswordIncorrectException(accountKey);
        }
    }

    public boolean isAccountDisabled(StringIdKey accountKey) throws HandlerException {
        try {
            Account account = accountMaintainService.get(accountKey);
            return !account.isEnabled();
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureAccountNotDisabled(StringIdKey accountKey) throws HandlerException {
        if (isAccountDisabled(accountKey)) {
            throw new AccountDisabledException(accountKey);
        }
    }

    public void makeSureLoginStateExists(LongIdKey loginStateKey) throws HandlerException {
        try {
            if (!loginStateMaintainService.exists(loginStateKey)) {
                throw new LoginStateNotExistsException(loginStateKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureLoginStateNotExpired(LongIdKey loginStateKey) throws HandlerException {
        try {
            LoginState loginState = loginStateMaintainService.get(loginStateKey);
            if (Objects.isNull(loginState)) {
                throw new LoginStateNotExistsException(loginStateKey);
            }
            long expireTimestamp = Optional.ofNullable(loginState.getExpireDate()).map(Date::getTime).orElse(0L);
            long currentTimestamp = System.currentTimeMillis();
            if (expireTimestamp < currentTimestamp) {
                throw new LoginStateExpiredException(loginStateKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureSerialNumberConsistent(LongIdKey loginStateKey) throws HandlerException {
        try {
            LoginState loginState = loginStateMaintainService.get(loginStateKey);
            Account account = accountMaintainService.get(loginState.getAccountKey());
            if (loginState.getSerialVersion() != account.getSerialVersion()) {
                throw new SerialVersionInconsistentException(loginStateKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}
