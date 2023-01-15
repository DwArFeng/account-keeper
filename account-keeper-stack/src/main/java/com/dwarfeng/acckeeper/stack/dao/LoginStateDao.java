package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 登陆状态据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginStateDao extends BatchBaseDao<LongIdKey, LoginState>, EntireLookupDao<LoginState>,
        PresetLookupDao<LoginState> {
}
