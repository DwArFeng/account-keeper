package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.memory.filter.PresetEntityFilter;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class LoginStatePresetEntityFilter implements PresetEntityFilter<LoginState> {

    @Override
    public boolean accept(LoginState entity, String preset, Object[] objs) {
        switch (preset) {
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT:
                return childForPoint(entity, objs);
            case LoginStateMaintainService.EXPIRE_DATE_BEFORE:
                return expireDateBefore(entity, objs);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private boolean childForPoint(LoginState entity, Object[] objs) {
        try {
            StringIdKey stringIdKey = (StringIdKey) objs[0];
            return Objects.equals(entity.getAccountKey(), stringIdKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private boolean expireDateBefore(LoginState entity, Object[] objs) {
        try {
            Date referenceDate = (Date) objs[0];
            long referenceTimestamp = referenceDate.getTime();
            long targetTimestamp = Optional.ofNullable(entity.getExpireDate()).map(Date::getTime).orElse(0L);
            return targetTimestamp <= referenceTimestamp;
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
