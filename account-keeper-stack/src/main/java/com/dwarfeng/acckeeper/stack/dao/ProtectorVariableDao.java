package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 保护器变量数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface ProtectorVariableDao extends BatchBaseDao<ProtectorVariableKey, ProtectorVariable>,
        EntireLookupDao<ProtectorVariable>, PresetLookupDao<ProtectorVariable> {
}
