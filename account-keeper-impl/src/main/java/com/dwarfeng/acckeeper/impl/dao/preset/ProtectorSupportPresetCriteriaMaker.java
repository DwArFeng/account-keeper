package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateProtectorSupport;
import com.dwarfeng.acckeeper.stack.service.ProtectorSupportMaintainService;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProtectorSupportPresetCriteriaMaker implements PresetCriteriaMaker<HibernateProtectorSupport> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorSupport> root,
            String s, Object[] objects
    ) {
        switch (s) {
            case ProtectorSupportMaintainService.ID_LIKE:
                idLike(criteriaBuilder, criteriaQuery, root, objects);
                break;
            case ProtectorSupportMaintainService.LABEL_LIKE:
                labelLike(criteriaBuilder, criteriaQuery, root, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void idLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorSupport> root,
            Object[] objects
    ) {
        try {
            String pattern = (String) objects[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("stringId"), pattern)

            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void labelLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateProtectorSupport> root,
            Object[] objects
    ) {
        try {
            String pattern = (String) objects[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("label"), pattern)

            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
