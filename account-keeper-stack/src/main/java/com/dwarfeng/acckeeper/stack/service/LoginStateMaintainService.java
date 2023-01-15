package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 登陆状态维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginStateMaintainService extends BatchCrudService<LongIdKey, LoginState>,
        EntireLookupService<LoginState>, PresetLookupService<LoginState> {

    String CHILD_FOR_ACCOUNT = "child_for_account";
}
