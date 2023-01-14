package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.AccountLoginInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 账户登陆信息维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface AccountLoginInfoMaintainService extends BatchCrudService<StringIdKey, AccountLoginInfo>,
        EntireLookupService<AccountLoginInfo> {
}
