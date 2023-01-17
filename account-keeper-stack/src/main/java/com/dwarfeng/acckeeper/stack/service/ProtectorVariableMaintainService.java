package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 保护器变量维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorVariableMaintainService extends BatchCrudService<ProtectorVariableKey, ProtectorVariable>,
        EntireLookupService<ProtectorVariable>, PresetLookupService<ProtectorVariable> {

    String CHILD_FOR_PROTECTOR_INFO = "child_for_protector_info";
}
