package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.data.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.data.stack.dao.PresetLookupDao;

/**
 * 登录状态据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginStateDao extends BatchBaseDao<StringIdKey, LoginState>, EntireLookupDao<LoginState>,
        PresetLookupDao<LoginState> {
}
