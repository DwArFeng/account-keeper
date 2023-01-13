package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginAccountInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;

/**
 * 账户数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginAccountInfoDao extends BatchBaseDao<StringIdKey, LoginAccountInfo>,
        EntireLookupDao<LoginAccountInfo> {
}
