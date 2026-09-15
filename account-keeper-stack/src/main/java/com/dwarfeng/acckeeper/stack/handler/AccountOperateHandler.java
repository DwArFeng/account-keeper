package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
import com.dwarfeng.subgrade.basic.stack.handler.Handler;

/**
 * 账户操作处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface AccountOperateHandler extends Handler {

    /**
     * 注册账户。
     *
     * @param accountRegisterInfo 账户注册信息。
     * @throws HandlerException 处理器异常。
     */
    void register(AccountRegisterInfo accountRegisterInfo) throws HandlerException;

    /**
     * 更新账户。
     *
     * @param accountUpdateInfo 账户更新信息。
     * @throws HandlerException 处理器异常。
     */
    void update(AccountUpdateInfo accountUpdateInfo) throws HandlerException;

    /**
     * 删除账户。
     *
     * @param accountKey 待删除的账户的主键。
     * @throws HandlerException 处理器异常。
     */
    void delete(StringIdKey accountKey) throws HandlerException;

    /**
     * 判断指定的账户密码是否正确。
     *
     * @param passwordCheckInfo 密码检查信息。
     * @return 账户的密码是否正确。
     * @throws HandlerException 处理器异常。
     */
    boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws HandlerException;

    /**
     * 更新账户密码。
     *
     * @param passwordUpdateInfo 密码更新信息。
     * @throws HandlerException 处理器异常。
     */
    void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws HandlerException;

    /**
     * 重置账户密码。
     *
     * @param passwordResetInfo 密码重置信息。
     * @throws HandlerException 处理器异常。
     */
    void resetPassword(PasswordResetInfo passwordResetInfo) throws HandlerException;

    /**
     * 使账户之前的登录信息无效。
     *
     * @param accountKey 指定的账户主键。
     * @throws HandlerException 处理器异常。
     */
    void invalid(StringIdKey accountKey) throws HandlerException;
}
