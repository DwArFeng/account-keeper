package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 登录历史数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginHistoryDao extends BatchBaseDao<LongIdKey, LoginHistory>, EntireLookupDao<LoginHistory>,
        PresetLookupDao<LoginHistory> {
}
