package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.sdk.util.AccountUtil;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.handler.AccountOperateHandler;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class AccountOperateHandlerImpl implements AccountOperateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountOperateHandlerImpl.class);

    private final AccountMaintainService accountMaintainService;
    private final ProtectorInfoMaintainService protectorInfoMaintainService;

    private final HandlerValidator handlerValidator;

    @Value("${register.password.salt_log_rounds}")
    private int logRounds;
    @Value("${register.default_protector.type}")
    private String defaultProtectorType;
    @Value("${register.default_protector.param}")
    private String defaultProtectorParam;

    public AccountOperateHandlerImpl(
            AccountMaintainService accountMaintainService,
            ProtectorInfoMaintainService protectorInfoMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.accountMaintainService = accountMaintainService;
        this.protectorInfoMaintainService = protectorInfoMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void register(AccountRegisterInfo accountRegisterInfo) throws HandlerException {
        try {
            // 获取主键，记录信息。
            StringIdKey accountKey = accountRegisterInfo.getAccountKey();
            LOGGER.info("注册新账户 " + accountKey + " ...");

            // 确定主键对应的账户不存在。
            handlerValidator.makeSureAccountNotExists(accountKey);

            // 使用 BCrypt 加密密码。
            String password = accountRegisterInfo.getPassword();
            String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(logRounds));

            // 构造账户实体。
            Account account = new Account(
                    accountKey, encryptedPassword, accountRegisterInfo.isEnabled(), accountRegisterInfo.getRemark(), 0,
                    accountRegisterInfo.getDisplayName(), new Date(), 0, 0
            );

            // 调用维护服务插入账户实体。
            accountMaintainService.insert(account);

            // 构造默认的保护器信息。
            ProtectorInfo protectorInfo = new ProtectorInfo(
                    accountKey, defaultProtectorType, defaultProtectorParam, "注册用户时自动创建的默认保护器。"
            );

            // 调用维护服务插入保护器信息。
            protectorInfoMaintainService.insert(protectorInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(AccountUpdateInfo accountUpdateInfo) throws HandlerException {
        try {
            // 获取主键，记录信息。
            StringIdKey accountKey = accountUpdateInfo.getAccountKey();
            LOGGER.info("注册新账户 " + accountKey + " ...");

            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 使用维护服务查询账户实体，并将对应的字段设置为新的信息。
            Account account = accountMaintainService.get(accountKey);
            account.setDisplayName(accountUpdateInfo.getDisplayName());
            account.setEnabled(accountUpdateInfo.isEnabled());
            account.setRemark(accountUpdateInfo.getRemark());

            // 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(StringIdKey accountKey) throws HandlerException {
        try {
            // 记录信息。
            LOGGER.info("删除账户 " + accountKey + " ...");

            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 调用维护服务删除实体。
            accountMaintainService.delete(accountKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws HandlerException {
        try {
            // 获取主键。
            StringIdKey accountKey = passwordCheckInfo.getAccountKey();

            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 调用维护服务获取账户的实体。
            Account account = accountMaintainService.get(accountKey);

            // 判断密码是否正确，并返回结果。如果密码不正确，在日志中记录此信息。
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
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws HandlerException {
        try {
            // 获取主键，记录信息。
            StringIdKey accountKey = passwordUpdateInfo.getAccountKey();
            LOGGER.info("账户 " + accountKey + " 请求更改密码...");

            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 确认旧密码正确。
            handlerValidator.makeSurePasswordCorrect(accountKey, passwordUpdateInfo.getOldPassword());

            // 使用维护服务查询账户实体，更新密码字段与统计字段。
            Account account = accountMaintainService.get(accountKey);
            account.setPassword(BCrypt.hashpw(passwordUpdateInfo.getNewPassword(), BCrypt.gensalt(logRounds)));
            account.setPasswordUpdateCount(account.getPasswordUpdateCount() + 1);

            // 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws HandlerException {
        try {
            // 获取主键，记录信息。
            StringIdKey accountKey = passwordResetInfo.getAccountKey();
            LOGGER.info("账户 " + accountKey + " 请求强制性更改密码...");

            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 使用维护服务查询账户实体，更新密码字段与统计字段。
            Account account = accountMaintainService.get(accountKey);
            account.setPassword(BCrypt.hashpw(passwordResetInfo.getNewPassword(), BCrypt.gensalt(logRounds)));
            account.setPasswordUpdateCount(account.getPasswordUpdateCount() + 1);

            // 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void invalid(StringIdKey accountKey) throws HandlerException {
        try {
            // 确定主键对应的账户存在。
            handlerValidator.makeSureAccountExists(accountKey);

            // 使用维护服务查询账户实体，并自增序列码字段。
            Account account = accountMaintainService.get(accountKey);
            AccountUtil.increaseSerial(account);

            // 使用维护服务更新实体。
            accountMaintainService.update(account);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
