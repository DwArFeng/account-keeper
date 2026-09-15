package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 派生历史数据访问层。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface DeriveHistoryDao extends BatchBaseDao<LongIdKey, DeriveHistory>, EntireLookupDao<DeriveHistory>,
        PresetLookupDao<DeriveHistory> {
}
