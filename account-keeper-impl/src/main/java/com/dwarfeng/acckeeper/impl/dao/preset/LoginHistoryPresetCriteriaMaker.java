package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateLoginHistory;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class LoginHistoryPresetCriteriaMaker implements PresetCriteriaMaker<HibernateLoginHistory> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            String preset, Object[] objs
    ) {
        switch (preset) {
            case LoginHistoryMaintainService.ACCOUNT_ID_EQUALS:
                accountIdEquals(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_LIKE:
                accountIdLike(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.HAPPENED_DATE_DESC:
                happenedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC:
                accountIdEqualsHappenedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC:
                accountIdLikeHappenedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.PROTECTOR_INSPECT:
                protectorInspect(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginHistoryMaintainService.TO_PURGED:
                toPurged(criteriaBuilder, criteriaQuery, root, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdEquals(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountId"))
                );
            } else {
                String pattern = (String) objs[0];
                CriteriaQueryHelper.addRestriction(

                        criteriaBuilder, criteriaQuery,

                        CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("accountId"), pattern)

                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdLike(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountId"))
                );
            } else {
                String pattern = (String) objs[0];
                CriteriaQueryHelper.addRestriction(

                        criteriaBuilder, criteriaQuery,

                        CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("accountId"), pattern)

                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void happenedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("happenedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdEqualsHappenedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountId"))
                );
            } else {
                String pattern = (String) objs[0];
                CriteriaQueryHelper.addRestriction(

                        criteriaBuilder, criteriaQuery,

                        CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("accountId"), pattern)

                );
            }
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("happenedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void accountIdLikeHappenedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountId"))
                );
            } else {
                String pattern = (String) objs[0];
                CriteriaQueryHelper.addRestriction(

                        criteriaBuilder, criteriaQuery,

                        CriteriaQueryHelper.likeAnywhere(criteriaBuilder, root.get("accountId"), pattern)

                );
            }
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("happenedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void protectorInspect(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            // 账户 ID。
            String accountId = (String) objs[0];
            if (Objects.isNull(accountId)) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountId"))
                );
            } else {
                CriteriaQueryHelper.addRestriction(

                        criteriaBuilder, criteriaQuery,

                        CriteriaQueryHelper.equalOrIsNull(criteriaBuilder, root.get("accountId"), accountId)

                );
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
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery,
                    criteriaBuilder.between(root.get("happenedDate"), beginDate.toInstant(), endDate.toInstant())

            );

            // 响应代码。
            @SuppressWarnings("unchecked")
            List<Integer> responseCodes = (List<Integer>) objs[3];
            if (Objects.nonNull(responseCodes)) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, root.get("responseCode").in(responseCodes)
                );
            }

            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("happenedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void toPurged(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginHistory> root,
            Object[] objs
    ) {
        try {
            Instant date = ((Date) objs[0]).toInstant();
            CriteriaQueryHelper.addRestriction(

                    criteriaBuilder, criteriaQuery, criteriaBuilder.lessThan(root.get("happenedDate"), date)

            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.asc(root.get("happenedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
