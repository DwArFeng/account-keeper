package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 登录处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface LoginHandler extends Handler {

    /**
     * 判断指定的登录状态主键是否处于有效的登录状态。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @return 指定的登录状态主键是否处于有效的登录状态。
     * @throws HandlerException 处理器异常。
     */
    boolean isLogin(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 获取指定的登录状态主键对应的登录状态。
     *
     * @param loginStateKey 指定登录状态主键。
     * @return 对应的登录状态。
     * @throws HandlerException 处理器异常。
     */
    LoginState getLoginState(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 登录。
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     */
    LoginState login(LoginInfo loginInfo) throws HandlerException;

    /**
     * 登出。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @throws HandlerException 处理器异常。
     */
    void logout(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 延长指定登录状态主键的超时日期。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @return 延期后的新的登录状态。
     * @throws HandlerException 处理器异常。
     */
    LoginState postpone(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 根据登陆状态主键查询登陆状态。
     *
     * <p>
     * 结果返回一个列表，列表的元素最大数量是 1。
     * 如果主键对应的值存在，则列表中仅有一个元素，即主键对应的值；
     * 如果主键对应的值不存在，则返回一个空列表。
     *
     * @param loginStateKey 登陆状态的主键。
     * @return 登陆状态组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LoginState> inspectLoginStateByKey(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 查询指定用户的所有登陆状态。
     *
     * @param accountKey 用户的主键。
     * @return 登陆状态组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LoginState> inspectLoginStateByAccount(StringIdKey accountKey) throws HandlerException;

    /**
     * 查询所有的登陆状态。
     *
     * @return 登陆状态组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LoginState> inspectAllLoginState() throws HandlerException;

    /**
     * 解除指定的登陆状态。
     *
     * @param loginStateKey 登陆状态的主键。
     * @throws HandlerException 处理器异常。
     */
    void kickByLoginState(LongIdKey loginStateKey) throws HandlerException;

    /**
     * 解除指定用户的所有登陆状态。
     *
     * @param accountKey 用户的主键。
     * @throws HandlerException 处理器异常。
     */
    void kickByAccount(StringIdKey accountKey) throws HandlerException;

    /**
     * 解除所有的登陆状态。
     *
     * @throws HandlerException 处理器异常。
     */
    void kickAll() throws HandlerException;
}
