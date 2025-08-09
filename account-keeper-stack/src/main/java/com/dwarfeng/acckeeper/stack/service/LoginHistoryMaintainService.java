package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 登录历史维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface LoginHistoryMaintainService extends BatchCrudService<LongIdKey, LoginHistory>,
        EntireLookupService<LoginHistory>, PresetLookupService<LoginHistory> {

    String ACCOUNT_ID_EQUALS = "account_id_equals";
    String ACCOUNT_ID_LIKE = "account_id_like";
    String HAPPENED_DATE_DESC = "happened_date_desc";
    String ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC = "account_id_equals_happened_date_desc";
    String ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC = "account_id_like_happened_date_desc";
    String PROTECTOR_INSPECT = "protector_inspect";

    /**
     * 获取将要被清理的登录历史实体。
     *
     * <p>
     * 返回 <code>happenedDate（发生日期）</code> 早于指定的日期的实体，
     * 且按照 <code>happenedDate（发生日期）</code> 升序排列。
     *
     * <p>
     * 参数列表：
     * <ol>
     *     <li>Data 指定的日期。</li>
     * </ol>
     * 返回的数据按照 <code>happenedDate（发生日期）</code> 升序排列。
     *
     * @since 1.9.0
     */
    String TO_PURGED = "to_purged";
}
