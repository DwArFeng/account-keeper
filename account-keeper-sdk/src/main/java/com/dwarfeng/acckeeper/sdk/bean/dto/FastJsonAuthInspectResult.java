package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.dto.AuthInspectResult;
import com.dwarfeng.subgrade.basic.stack.bean.Bean;

import java.io.Serial;
import java.util.Objects;

/**
 * FastJson 授权查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonAuthInspectResult implements Bean {

    @Serial
    private static final long serialVersionUID = -6451767252432714713L;

    public static FastJsonAuthInspectResult of(AuthInspectResult authInspectResult) {
        if (Objects.isNull(authInspectResult)) {
            return null;
        } else {
            return new FastJsonAuthInspectResult(
                    authInspectResult.isLogin(),
                    FastJsonLoginState.of(authInspectResult.getLoginState())
            );
        }
    }

    @JSONField(name = "is_login", ordinal = 1)
    private boolean isLogin;

    @JSONField(name = "login_state", ordinal = 2)
    private FastJsonLoginState loginState;

    public FastJsonAuthInspectResult() {
    }

    public FastJsonAuthInspectResult(boolean isLogin, FastJsonLoginState loginState) {
        this.isLogin = isLogin;
        this.loginState = loginState;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public FastJsonLoginState getLoginState() {
        return loginState;
    }

    public void setLoginState(FastJsonLoginState loginState) {
        this.loginState = loginState;
    }

    @Override
    public String toString() {
        return "FastJsonAuthInspectResult{" +
                "isLogin=" + isLogin +
                ", loginState=" + loginState +
                '}';
    }
}
