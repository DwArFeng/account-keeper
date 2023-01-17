package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 保护器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorSupportMaintainService extends BatchCrudService<StringIdKey, ProtectorSupport>,
        EntireLookupService<ProtectorSupport>, PresetLookupService<ProtectorSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    /**
     * 重置调度器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}
