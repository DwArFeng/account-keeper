package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateProtectorInfo;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProtectorInfoPresetCriteriaMaker implements PresetCriteriaMaker<HibernateProtectorInfo> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorInfo> root,
            String s, Object[] objects
    ) {
        switch (s) {
            case ProtectorInfoMaintainService.TYPE_EQUALS:
                typeEquals(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case ProtectorInfoMaintainService.TYPE_LIKE:
                typeLike(criteriaBuilder, criteriaQuery, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void typeEquals(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorInfo> root,
            Object[] objects
    ) {
        try {
            String type = (String) objects[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("type"), type)

            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void typeLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorInfo> root,
            Object[] objects
    ) {
        try {
            String pattern = (String) objects[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("type"), pattern)

            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
