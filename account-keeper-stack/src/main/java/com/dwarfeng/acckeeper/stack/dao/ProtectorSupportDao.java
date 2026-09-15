package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 保护器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorSupportDao extends BatchBaseDao<StringIdKey, ProtectorSupport>,
        EntireLookupDao<ProtectorSupport>, PresetLookupDao<ProtectorSupport> {
}
