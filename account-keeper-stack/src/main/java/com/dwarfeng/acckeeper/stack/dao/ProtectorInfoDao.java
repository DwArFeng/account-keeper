package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 保护器信息据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorInfoDao extends BatchBaseDao<StringIdKey, ProtectorInfo>, EntireLookupDao<ProtectorInfo>,
        PresetLookupDao<ProtectorInfo> {
}
