package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.CheckerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 检查器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface CheckerSupportMaintainService extends BatchCrudService<StringIdKey, CheckerSupport>,
        EntireLookupService<CheckerSupport>, PresetLookupService<CheckerSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    /**
     * 重置检查器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}
