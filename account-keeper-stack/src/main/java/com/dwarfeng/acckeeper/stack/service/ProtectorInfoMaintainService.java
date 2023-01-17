package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 保护器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorInfoMaintainService extends BatchCrudService<StringIdKey, ProtectorInfo>, EntireLookupService<ProtectorInfo>,
        PresetLookupService<ProtectorInfo> {

    String TYPE_EQUALS = "type_equals";
    String TYPE_LIKE = "type_like";
}
