package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateLoginParamRecord;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class LoginParamRecordPresetCriteriaMaker implements PresetCriteriaMaker<HibernateLoginParamRecord> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginParamRecord> root,
            String s, Object[] objects
    ) {
        switch (s) {
            case LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY:
                childForLoginHistory(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC:
                childForLoginHistoryRecordIdAsc(criteriaBuilder, criteriaQuery, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForLoginHistory(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginParamRecord> root,
            Object[] objects
    ) {
        try {
            if (Objects.isNull(objects[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("loginHistoryId"))
                );
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("loginHistoryId"), longIdKey.getLongId()
                        )
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForLoginHistoryRecordIdAsc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginParamRecord> root,
            Object[] objects
    ) {
        try {
            if (Objects.isNull(objects[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("loginHistoryId"))
                );
            } else {
                LongIdKey longIdKey = (LongIdKey) objects[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("loginHistoryId"), longIdKey.getLongId()
                        )
                );
            }
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("recordId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
