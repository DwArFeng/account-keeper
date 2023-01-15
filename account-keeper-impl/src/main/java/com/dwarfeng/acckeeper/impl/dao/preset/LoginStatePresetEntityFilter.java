package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.memory.filter.PresetEntityFilter;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class LoginStatePresetEntityFilter implements PresetEntityFilter<LoginState> {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public boolean accept(LoginState entity, String preset, Object[] objs) {
        switch (preset) {
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT:
                return childForPoint(entity, objs);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private boolean childForPoint(LoginState entity, Object[] objs) {
        try {
            StringIdKey stringIdKey = (StringIdKey) objs[0];
            return Objects.equals(entity.getAccountKey(), stringIdKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
