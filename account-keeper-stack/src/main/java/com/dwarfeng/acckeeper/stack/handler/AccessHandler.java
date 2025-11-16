package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 访问处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface AccessHandler extends Handler {

    /**
     * 授权查看。
     *
     * @param info 授权查看信息。
     * @return 授权查看结果。
     * @throws HandlerException 处理器异常。
     */
    AuthInspectResult authInspect(AuthInspectInfo info) throws HandlerException;

    /**
     * 动态登录。
     *
     * <p>
     * 请求成功后返回过期时间较短的登录状态，需要客户端定期延期以保持登录状态有效。
     *
     * @param info 动态登录信息。
     * @return 动态登录结果。
     * @throws HandlerException 处理器异常。
     */
    DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws HandlerException;

    /**
     * 静态登录。
     *
     * <p>
     * 请求成功后返回指定过期时间的登录状态。
     *
     * @param info 静态登录信息。
     * @return 静态登录结果。
     * @throws HandlerException 处理器异常。
     */
    StaticLoginResult staticLogin(StaticLoginInfo info) throws HandlerException;

    /**
     * 登出。
     *
     * <p>
     * 登出后该登录状态主键将失效。
     *
     * @param info 登出信息。
     * @throws HandlerException 处理器异常。
     */
    void logout(LogoutInfo info) throws HandlerException;

    /**
     * 延期。
     *
     * <p>
     * 该操作可以将指定的登录状态的过期时间延长一段时间，从而保持登录状态的有效性，延长的事件由系统配置决定。
     *
     * <p>
     * 该方法仅对动态登录有效；对于静态登录不进行任何修改，结果中的过期时间保持不变。
     *
     * @param info 延期信息。
     * @return 延期结果。
     * @throws HandlerException 处理器异常。
     */
    PostponeResult postpone(PostponeInfo info) throws HandlerException;

    /**
     * 踢出。
     *
     * <p>
     * 强制解除指定登录状态主键对应的登录状态。
     *
     * <p>
     * 操作具有破坏性，请确保具备相应的授权后使用。
     *
     * <p>
     * 关于该方法的说明，请参阅 {@link KickInfo} 的文档注释。
     *
     * @param info 指定的登录状态主键信息。
     * @throws HandlerException 处理器异常。
     * @see KickInfo
     */
    void kick(KickInfo info) throws HandlerException;
}
