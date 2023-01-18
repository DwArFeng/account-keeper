package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 检查器信息据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface CheckerInfoDao extends BatchBaseDao<StringIdKey, CheckerInfo>, EntireLookupDao<CheckerInfo>,
        PresetLookupDao<CheckerInfo> {
}
