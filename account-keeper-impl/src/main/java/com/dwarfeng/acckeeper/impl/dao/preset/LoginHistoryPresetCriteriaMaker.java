package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class LoginHistoryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case LoginHistoryMaintainService.ACCOUNT_ID_EQUALS:
                accountIdEquals(criteria, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_LIKE:
                accountIdLike(criteria, objs);
                break;
            case LoginHistoryMaintainService.HAPPENED_DATE_DESC:
                happenedDateDesc(criteria, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC:
                accountIdEqualsHappenedDateDesc(criteria, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC:
                accountIdLikeHappenedDateDesc(criteria, objs);
                break;
            case LoginHistoryMaintainService.PROTECTOR_INSPECT:
                protectorInspect(criteria, objs);
                break;
            case LoginHistoryMaintainService.TO_PURGED:
                toPurged(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdEquals(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountId"));
            } else {
                String pattern = (String) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountId", pattern));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdLike(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountId"));
            } else {
                String pattern = (String) objs[0];
                criteria.add(Restrictions.like("accountId", pattern, MatchMode.ANYWHERE));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void happenedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.addOrder(Order.desc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdEqualsHappenedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountId"));
            } else {
                String pattern = (String) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountId", pattern));
            }
            criteria.addOrder(Order.desc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdLikeHappenedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountId"));
            } else {
                String pattern = (String) objs[0];
                criteria.add(Restrictions.like("accountId", pattern, MatchMode.ANYWHERE));
            }
            criteria.addOrder(Order.desc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void protectorInspect(DetachedCriteria criteria, Object[] objs) {
        try {
            // 账户 ID。
            String accountId = (String) objs[0];
            if (Objects.isNull(accountId)) {
                criteria.add(Restrictions.isNull("accountId"));
            } else {
                criteria.add(Restrictions.eqOrIsNull("accountId", accountId));
            }

            // 发生时间。
            Date beginDate = (Date) objs[1];
            Date endDate = (Date) objs[2];
            if (Objects.isNull(beginDate)) {
                beginDate = new Date(0);
            }
            if (Objects.isNull(endDate)) {
                endDate = new Date();
            }
            criteria.add(Restrictions.between("happenedDate", beginDate, endDate));

            // 响应代码。
            @SuppressWarnings("unchecked")
            List<Integer> responseCodes = (List<Integer>) objs[3];
            if (Objects.nonNull(responseCodes)) {
                criteria.add(Restrictions.in("responseCode", responseCodes));
            }

            criteria.addOrder(Order.desc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void toPurged(DetachedCriteria criteria, Object[] objs) {
        try {
            Date date = (Date) objs[0];
            criteria.add(Restrictions.lt("happenedDate", date));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
