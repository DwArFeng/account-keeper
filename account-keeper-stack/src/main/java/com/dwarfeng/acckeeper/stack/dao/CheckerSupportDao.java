package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.CheckerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 检查器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface CheckerSupportDao extends BatchBaseDao<StringIdKey, CheckerSupport>,
        EntireLookupDao<CheckerSupport>, PresetLookupDao<CheckerSupport> {
}
