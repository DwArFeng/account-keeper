package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 账户 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface AccountQosService extends Service {

    /**
     * 注册账户。
     *
     * @param accountRegisterInfo 账户注册信息。
     * @throws ServiceException 服务异常。
     */
    void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException;

    /**
     * 更新账户。
     *
     * @param accountUpdateInfo 账户更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException;

    /**
     * 删除账户。
     *
     * @param accountKey 待删除的账户的主键。
     * @throws ServiceException 服务异常。
     */
    void delete(StringIdKey accountKey) throws ServiceException;

    /**
     * 判断指定的账户密码是否正确。
     *
     * @param passwordCheckInfo 密码检查信息。
     * @return 账户的密码是否正确。
     * @throws ServiceException 服务异常。
     */
    boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws ServiceException;

    /**
     * 更新账户密码。
     *
     * @param passwordUpdateInfo 密码更新信息。
     * @throws ServiceException 服务异常。
     */
    void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException;

    /**
     * 重置账户密码。
     *
     * @param passwordResetInfo 密码重置信息。
     * @throws ServiceException 服务异常。
     */
    void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException;

    /**
     * 使账户之前的登录信息无效。
     *
     * @param accountKey 指定的账户主键。
     * @throws ServiceException 服务异常。
     */
    void invalid(StringIdKey accountKey) throws ServiceException;
}
