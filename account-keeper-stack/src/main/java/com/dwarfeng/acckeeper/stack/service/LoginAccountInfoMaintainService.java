package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginAccountInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 登陆账户信息维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginAccountInfoMaintainService extends BatchCrudService<StringIdKey, LoginAccountInfo>,
        EntireLookupService<LoginAccountInfo> {
}
