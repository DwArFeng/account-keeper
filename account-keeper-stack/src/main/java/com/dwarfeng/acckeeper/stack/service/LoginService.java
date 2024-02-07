package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录服务。
 *
 * @author DwArFeng
 * @since 0.1.0-alpha
 */
public interface LoginService extends Service {

    /**
     * 判断指定的登录状态主键是否处于有效的登录状态。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @return 指定的登录状态主键是否处于有效的登录状态。
     * @throws ServiceException 服务异常。
     */
    boolean isLogin(LongIdKey loginStateKey) throws ServiceException;

    /**
     * 获取指定的登录状态主键对应的登录状态。
     *
     * @param loginStateKey 指定登录状态主键。
     * @return 对应的登录状态。
     * @throws ServiceException 服务异常。
     */
    LoginState getLoginState(LongIdKey loginStateKey) throws ServiceException;

    /**
     * 登录。
     *
     * <p>
     * 该方法在新版本中已经被废弃，新版本中使用
     * {@link #dynamicLogin(DynamicLoginInfo)} 或者 {@link #staticLogin(StaticLoginInfo)}。<br>
     *
     * @param loginInfo 登录信息。
     * @return 登录状态。
     * @throws ServiceException 服务异常。
     * @deprecated 使用 {@link #dynamicLogin(DynamicLoginInfo)} 或者 {@link #staticLogin(StaticLoginInfo)}。
     */
    @Deprecated
    LoginState login(LoginInfo loginInfo) throws ServiceException;

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
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    LoginState dynamicLogin(DynamicLoginInfo loginInfo) throws ServiceException;

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
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    LoginState staticLogin(StaticLoginInfo loginInfo) throws ServiceException;

    /**
     * 登出。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @throws ServiceException 服务异常。
     */
    void logout(LongIdKey loginStateKey) throws ServiceException;

    /**
     * 延长指定登录状态主键的超时日期。
     *
     * <p>
     * 该方法对于动态登录的登录状态有效。<br>
     * 对于静态登录的登录状态，该方法将不做任何操作，直接返回指定的主键对应的登录状态。
     *
     * @param loginStateKey 指定的登录状态主键。
     * @return 延期后的新的登录状态。
     * @throws ServiceException 服务异常。
     */
    LoginState postpone(LongIdKey loginStateKey) throws ServiceException;
}
