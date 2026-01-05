package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 账户的 CriteriaMaker。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component
public class AccountPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case AccountMaintainService.ID_LIKE:
                idLike(criteria, objs);
                break;
            case AccountMaintainService.DISPLAY_NAME_LIKE:
                displayNameLike(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void idLike(DetachedCriteria criteria, Object[] objs) {
        try {
            String id = (String) objs[0];
            criteria.add(Restrictions.like("stringId", id, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("stringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void displayNameLike(DetachedCriteria criteria, Object[] objs) {
        try {
            String id = (String) objs[0];
            criteria.add(Restrictions.like("displayName", id, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("displayName"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
