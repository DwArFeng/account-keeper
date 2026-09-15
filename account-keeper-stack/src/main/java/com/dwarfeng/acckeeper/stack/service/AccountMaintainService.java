package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountUpdateInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import com.dwarfeng.subgrade.data.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.data.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.data.stack.service.PresetLookupService;

/**
 * 账户维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface AccountMaintainService extends BatchCrudService<StringIdKey, Account>,
        EntireLookupService<Account>, PresetLookupService<Account> {

    String ID_LIKE = "id_like";
    String DISPLAY_NAME_LIKE = "display_name_like";

    /**
     * 请勿在注册用户时直接调用本方法，而是使用 {@link AccountOperateService#register(AccountRegisterInfo)}。
     * <p>
     * 请勿直接调用该方法，除非您清楚调用该方法意味着什么。
     */
    @Override
    StringIdKey insert(Account element) throws ServiceException;

    /**
     * 请勿在更新用户时直接调用本方法，而是使用 {@link AccountOperateService#update(AccountUpdateInfo)}。
     * <p>
     * 请勿直接调用该方法，除非您清楚调用该方法意味着什么。
     */
    @Override
    void update(Account element) throws ServiceException;

    /**
     * 请勿直接调用该方法，除非您清楚调用该方法意味着什么。
     */
    @Override
    StringIdKey insertIfNotExists(Account element) throws ServiceException;

    /**
     * 请勿直接调用该方法，除非您清楚调用该方法意味着什么。
     */
    @Override
    void updateIfExists(Account element) throws ServiceException;

    /**
     * 请勿直接调用该方法，除非您清楚调用该方法意味着什么。
     */
    @Override
    StringIdKey insertOrUpdate(Account element) throws ServiceException;
}
