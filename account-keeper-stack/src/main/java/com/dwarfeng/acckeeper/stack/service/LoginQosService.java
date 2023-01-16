package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 登陆 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginQosService extends Service {

    /**
     * 登录。
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws ServiceException 服务异常。
     */
    LoginState login(LoginInfo loginInfo) throws ServiceException;

    /**
     * 查询指定的登陆状态。
     *
     * <p>
     * 结果返回一个列表，列表的元素最大数量是 1。
     * 如果主键对应的值存在，则列表中仅有一个元素，即主键对应的值；
     * 如果主键对应的值不存在，则返回一个空列表。
     *
     * @param loginStateKey 登陆状态的主键。
     * @return 登陆状态组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<LoginState> inspectLoginStateByKey(LongIdKey loginStateKey) throws ServiceException;

    /**
     * 查询指定用户的所有登陆状态。
     *
     * @param accountKey 用户的主键。
     * @return 登陆状态组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<LoginState> inspectLoginStateByAccount(StringIdKey accountKey) throws ServiceException;

    /**
     * 查询所有的登陆状态。
     *
     * @return 登陆状态组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<LoginState> inspectAllLoginState() throws ServiceException;

    /**
     * 解除指定的登陆状态。
     *
     * @param loginStateKey 登陆状态的主键。
     * @throws ServiceException 服务异常。
     */
    void kickByLoginState(LongIdKey loginStateKey) throws ServiceException;

    /**
     * 解除指定用户的所有登陆状态。
     *
     * @param accountKey 用户的主键。
     * @throws ServiceException 服务异常。
     */
    void kickByAccount(StringIdKey accountKey) throws ServiceException;

    /**
     * 解除所有的登陆状态。
     *
     * @throws ServiceException 服务异常。
     */
    void kickAll() throws ServiceException;
}
