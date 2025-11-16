package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 授权查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class AuthInspectResult implements Dto {

    private static final long serialVersionUID = 2800076471754481299L;

    /**
     * 是否登录。
     *
     * <p>
     * 如果为 <code>false</code>，则表示未登录。
     *
     * <p>
     * 需要注意的是，有可能存在未登录但登录状态详情存在的情况，
     * 例如登录状态已过期但仍保留在系统中，此时该字段为 <code>false</code>，
     * 但 {@link #loginState} 字段不为 <code>null</code>。
     */
    private boolean isLogin;

    /**
     * 登录状态。
     *
     * <p>
     * 如果登录状态不存在，则为 <code>null</code>。
     */
    private LoginState loginState;

    public AuthInspectResult() {
    }

    public AuthInspectResult(boolean isLogin, LoginState loginState) {
        this.isLogin = isLogin;
        this.loginState = loginState;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public void setLoginState(LoginState loginState) {
        this.loginState = loginState;
    }

    @Override
    public String toString() {
        return "AuthInspectResult{" +
                "isLogin=" + isLogin +
                ", loginState=" + loginState +
                '}';
    }
}
