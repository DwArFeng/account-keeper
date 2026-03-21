package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 访问服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface AccessService extends Service {

    /**
     * 授权查看。
     *
     * @param info 授权查看信息。
     * @return 授权查看结果。
     * @throws ServiceException 服务异常。
     */
    AuthInspectResult authInspect(AuthInspectInfo info) throws ServiceException;

    /**
     * 动态登录。
     *
     * <p>
     * 请求成功后返回过期时间较短的登录状态，需要客户端定期延期以保持登录状态有效。
     *
     * @param info 动态登录信息。
     * @return 动态登录结果。
     * @throws ServiceException 服务异常。
     */
    DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws ServiceException;

    /**
     * 静态登录。
     *
     * <p>
     * 请求成功后返回指定过期时间的登录状态。
     *
     * @param info 静态登录信息。
     * @return 静态登录结果。
     * @throws ServiceException 服务异常。
     */
    StaticLoginResult staticLogin(StaticLoginInfo info) throws ServiceException;

    /**
     * 可信动态登录。
     *
     * <p>
     * 用于与第三方登录系统（如 OAuth2、SAML、企业 SSO）集成。<br>
     * 当用户已通过外部身份提供者完成认证后，调用方传入账户标识等信息，
     * 系统将信任该认证结果，跳过密码校验直接创建登录状态。<br>
     * 动态登录返回的登录状态过期时间较短，需通过 {@link #postpone(PostponeInfo)} 延长。<br>
     * 典型使用场景包括：OAuth2 授权码流程回调、SAML 断言消费、企业统一身份认证等。
     *
     * @param info 可信动态登录信息。
     * @return 动态登录结果。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    DynamicLoginResult trustedDynamicLogin(TrustedDynamicLoginInfo info) throws ServiceException;

    /**
     * 可信静态登录。
     *
     * <p>
     * 用于与第三方登录系统（如 OAuth2、SAML、企业 SSO）集成。<br>
     * 当用户已通过外部身份提供者完成认证后，调用方传入账户标识及期望的过期时间，
     * 系统将信任该认证结果，跳过密码校验直接创建登录状态。<br>
     * 静态登录的过期时间由调用方指定，适用于长期登录场景。<br>
     * 典型使用场景包括：OAuth2 长期令牌兑换、SAML 单点登录后的持久会话、企业统一身份认证的"记住我"等。
     *
     * @param info 可信静态登录信息。
     * @return 静态登录结果。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    StaticLoginResult trustedStaticLogin(TrustedStaticLoginInfo info) throws ServiceException;

    /**
     * 登出指定登录状态主键对应的登录状态。
     *
     * <p>
     * 登出后该登录状态主键将失效。
     *
     * @param info 登出信息。
     * @throws ServiceException 服务异常。
     */
    void logout(LogoutInfo info) throws ServiceException;

    /**
     * 延期。
     *
     * <p>
     * 该操作可以将指定的登录状态的过期时间延长一段时间，从而保持登录状态的有效性，延长的时间由系统配置决定。
     *
     * <p>
     * 该方法仅对动态登录有效；对于静态登录不进行任何修改，结果中的过期时间保持不变。
     *
     * @param info 延期信息。
     * @return 延期结果。
     * @throws ServiceException 服务异常。
     */
    PostponeResult postpone(PostponeInfo info) throws ServiceException;

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
     * @throws ServiceException 服务异常。
     * @see KickInfo
     */
    void kick(KickInfo info) throws ServiceException;
}
