package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.AccountUtil;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.exception.AccountAlreadyExistedException;
import com.dwarfeng.acckeeper.stack.exception.AccountNotExistsException;
import com.dwarfeng.acckeeper.stack.exception.PasswordIncorrectException;
import com.dwarfeng.acckeeper.stack.handler.AccountOperateHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountOperateHandlerImpl implements AccountOperateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountOperateHandlerImpl.class);

    private final AccountMaintainService accountMaintainService;

    @Value("${acckeeper.password.salt_log_rounds}")
    private int logRounds;

    public AccountOperateHandlerImpl(AccountMaintainService accountMaintainService) {
        this.accountMaintainService = accountMaintainService;
    }

    @Override
    public void register(AccountRegisterInfo accountRegisterInfo) throws HandlerException {
        try {
            // 1. 获取主键，记录信息。
            StringIdKey accountKey = accountRegisterInfo.getAccountKey();
            LOGGER.info("注册新账户 " + accountKey + " ...");

            // 2. 确定主键对应的账户不存在。
            makeSureAccountNotExists(accountKey);

            // 3. 使用 BCrypt 加密密码。
            String password = accountRegisterInfo.getPassword();
            String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));

            // 4. 构造账户实体。
            Account account = new Account(
                    accountKey, encryptedPassword, accountRegisterInfo.isEnabled(), accountRegisterInfo.getRemark(), 0,
                    accountRegisterInfo.getDisplayName()
            );

            // 5. 调用维护服务插入账户实体。
            accountMaintainService.insert(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void update(AccountUpdateInfo accountUpdateInfo) throws HandlerException {
        try {
            // 1. 获取主键，记录信息。
            StringIdKey accountKey = accountUpdateInfo.getAccountKey();
            LOGGER.info("注册新账户 " + accountKey + " ...");

            // 2. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 使用维护服务查询账户实体，并将对应的字段设置为新的信息。
            Account account = accountMaintainService.get(accountKey);
            account.setDisplayName(accountUpdateInfo.getDisplayName());
            account.setEnabled(accountUpdateInfo.isEnabled());
            account.setRemark(accountUpdateInfo.getRemark());

            // 4. 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void delete(StringIdKey accountKey) throws HandlerException {
        try {
            // 1. 记录信息。
            LOGGER.info("删除账户 " + accountKey + " ...");

            // 2. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 调用维护服务删除实体。
            accountMaintainService.delete(accountKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws HandlerException {
        try {
            // 1. 获取主键。
            StringIdKey accountKey = passwordCheckInfo.getAccountKey();

            // 2. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 调用维护服务获取账户的实体。
            Account account = accountMaintainService.get(accountKey);

            // 4. 判断密码是否正确，并返回结果。如果密码不正确，在日志中记录此信息。
            String password = passwordCheckInfo.getPassword();
            if (BCrypt.checkpw(password, account.getPassword())) {
                return true;
            } else {
                LOGGER.warn("账户 ID=" + account.getKey().getStringId() + " 密码验证失败...");
                return false;
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws HandlerException {
        try {
            // 1. 获取主键，记录信息。
            StringIdKey accountKey = passwordUpdateInfo.getAccountKey();
            LOGGER.info("账户 " + accountKey + " 请求更改密码...");

            // 2. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 确认旧密码正确。
            makeSurePasswordCorrect(accountKey, passwordUpdateInfo.getOldPassword());

            // 4. 使用维护服务查询账户实体，并将密码字段设置为新的信息。
            Account account = accountMaintainService.get(accountKey);
            account.setPassword(BCrypt.hashpw(passwordUpdateInfo.getNewPassword(), BCrypt.gensalt(logRounds)));

            // 5. 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws HandlerException {
        try {
            // 1. 获取主键，记录信息。
            StringIdKey accountKey = passwordResetInfo.getAccountKey();
            LOGGER.info("账户 " + accountKey + " 请求强制性更改密码...");

            // 2. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 使用维护服务查询账户实体，并将密码字段设置为新的信息。
            Account account = accountMaintainService.get(accountKey);
            account.setPassword(BCrypt.hashpw(passwordResetInfo.getNewPassword(), BCrypt.gensalt(logRounds)));

            // 4. 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void invalid(StringIdKey accountKey) throws HandlerException {
        try {
            // 1. 确定主键对应的账户存在。
            makeSureAccountExists(accountKey);

            // 3. 使用维护服务查询账户实体，并自增序列码字段。
            Account account = accountMaintainService.get(accountKey);
            AccountUtil.increaseSerial(account);

            // 3. 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void makeSureAccountNotExists(StringIdKey accountKey) throws HandlerException {
        try {
            if (accountMaintainService.exists(accountKey)) {
                throw new AccountAlreadyExistedException(accountKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    private void makeSureAccountExists(StringIdKey accountKey) throws HandlerException {
        try {
            if (!accountMaintainService.exists(accountKey)) {
                throw new AccountNotExistsException(accountKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    private void makeSurePasswordCorrect(StringIdKey accountKey, String password) throws HandlerException {
        try {
            Account account = accountMaintainService.get(accountKey);
            if (!BCrypt.checkpw(password, account.getPassword())) {
                throw new PasswordIncorrectException(accountKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}
