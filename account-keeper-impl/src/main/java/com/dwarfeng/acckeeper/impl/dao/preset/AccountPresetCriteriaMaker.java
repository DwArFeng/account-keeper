package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 账户的 CriteriaMaker。
 *
 * @author DwArFeng
 * @since 1.1.3
 */
@Component
public class AccountPresetCriteriaMaker implements PresetCriteriaMaker<HibernateAccount> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateAccount> root,
            String preset, Object[] objs
    ) {
        switch (preset) {
            case AccountMaintainService.ID_LIKE:
                idLike(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case AccountMaintainService.DISPLAY_NAME_LIKE:
                displayNameLike(criteriaBuilder, criteriaQuery, root, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void idLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateAccount> root,
            Object[] objs
    ) {
        try {
            String id = (String) objs[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("stringId"), id)

            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("stringId")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void displayNameLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateAccount> root,
            Object[] objs
    ) {
        try {
            String id = (String) objs[0];
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,

                    CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("displayName"), id)

            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("displayName")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
