package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 登录状态查询结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLoginStateLookupResult implements Dto {

    private static final long serialVersionUID = 5956400637189757074L;

    public static FastJsonLoginStateLookupResult of(LoginStateLookupResult loginStateLookupResult) {
        if (Objects.isNull(loginStateLookupResult)) {
            return null;
        } else {
            return new FastJsonLoginStateLookupResult(
                    Optional.ofNullable(loginStateLookupResult.getLoginStates()).map(
                            f -> f.stream().map(FastJsonLoginState::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "login_states", ordinal = 1)
    private List<FastJsonLoginState> loginStates;

    public FastJsonLoginStateLookupResult() {
    }

    public FastJsonLoginStateLookupResult(List<FastJsonLoginState> loginStates) {
        this.loginStates = loginStates;
    }

    public List<FastJsonLoginState> getLoginStates() {
        return loginStates;
    }

    public void setLoginStates(List<FastJsonLoginState> loginStates) {
        this.loginStates = loginStates;
    }

    @Override
    public String toString() {
        return "FastJsonLoginStateLookupResult{" +
                "loginStates=" + loginStates +
                '}';
    }
}
