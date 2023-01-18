package com.dwarfeng.acckeeper.stack.dao;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 登陆参数记录数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginParamRecordDao extends BatchBaseDao<RecordKey, LoginParamRecord>, EntireLookupDao<LoginParamRecord>,
        PresetLookupDao<LoginParamRecord> {
}
