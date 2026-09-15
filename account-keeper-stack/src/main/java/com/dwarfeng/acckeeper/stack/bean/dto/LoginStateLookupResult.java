package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.basic.stack.bean.dto.Dto;

import java.io.Serial;
import java.util.List;

/**
 * 登录状态查询结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginStateLookupResult implements Dto {

    @Serial
    private static final long serialVersionUID = -7683816569599538667L;

    private List<LoginState> loginStates;

    public LoginStateLookupResult() {
    }

    public LoginStateLookupResult(List<LoginState> loginStates) {
        this.loginStates = loginStates;
    }

    public List<LoginState> getLoginStates() {
        return loginStates;
    }

    public void setLoginStates(List<LoginState> loginStates) {
        this.loginStates = loginStates;
    }

    @Override
    public String toString() {
        return "LoginStateLookupResult{" +
                "loginStates=" + loginStates +
                '}';
    }
}
