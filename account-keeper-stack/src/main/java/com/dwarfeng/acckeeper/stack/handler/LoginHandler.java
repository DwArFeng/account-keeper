package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
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
     * <p>
     * 该方法在新版本中已经被废弃，新版本中使用
     * {@link #dynamicLogin(DynamicLoginInfo)} 或者 {@link #staticLogin(StaticLoginInfo)}。<br>
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     * @deprecated 使用 {@link #dynamicLogin(DynamicLoginInfo)} 或者 {@link #staticLogin(StaticLoginInfo)}。
     */
    @Deprecated
    LoginState login(LoginInfo loginInfo) throws HandlerException;

    /**
     * 动态登录。
     *
     * <p>
     * 动态登录是指登录请求成功后，返回一个过期时间较短的登录状态。<br>
     * 在该登录状态过期之前，客户端需要及时调用 {@link #postpone(LongIdKey)} 方法来延长登录状态的过期时间。<br>
     * 如果登录状态过期，客户端需要重新登录。
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     * @since 1.7.0
     */
    LoginState dynamicLogin(DynamicLoginInfo loginInfo) throws HandlerException;

    /**
     * 静态登录。
     *
     * <p>
     * 静态登录是指登录时可以指定一个过期时间，登录状态将在过期时间到达之前一直有效。<br>
     * 指定的过期时间可以是一个较长的时间，用于实现长期登录。<br>
     * 在过期时间到达之前，客户端不需要做任何操作，登录状态都将保持有效。<br>
     * 如果登录状态过期，客户端需要重新登录。
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     * @since 1.7.0
     */
    LoginState staticLogin(StaticLoginInfo loginInfo) throws HandlerException;

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
     * <p>
     * 该方法对于动态登录的登录状态有效。<br>
     * 对于静态登录的登录状态，该方法将不做任何操作，直接返回指定的主键对应的登录状态。
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
