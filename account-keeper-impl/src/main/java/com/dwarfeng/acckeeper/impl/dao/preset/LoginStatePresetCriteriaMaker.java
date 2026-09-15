package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateLoginState;
import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.data.sdk.hibernate.criteria.PresetCriteriaMaker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class LoginStatePresetCriteriaMaker implements PresetCriteriaMaker<HibernateLoginState> {

    @Override
    public void makeCriteria(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            String preset, Object[] objs
    ) {
        switch (preset) {
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT:
                childForAccount(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.EXPIRE_DATE_BEFORE:
                expireDateBefore(criteriaBuilder, criteriaQuery, root, objs);
                break;
            // ↓清理作业使用。
            case LoginStateMaintainService.WITHOUT_ACCOUNT:
                withoutAccount(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.EXPIRE_DATE_BEFORE_NOW:
                expireDateBeforeNow(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.SERIAL_VERSION_LOWER_THAN_ACCOUNT:
                serialVersionLowerThanAccount(criteriaBuilder, criteriaQuery, root, objs);
                break;
            // ↑清理作业使用。
            case LoginStateMaintainService.GENERATED_DATE_DESC:
                generatedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_GENERATED_DATE_DESC:
                childForAccountGeneratedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC:
                childForAccountTypeEqualsDynamic(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC_GENERATED_DATE_DESC:
                childForAccountTypeEqualsDynamicGeneratedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC:
                childForAccountTypeEqualsStatic(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC_GENERATED_DATE_DESC:
                childForAccountTypeEqualsStaticGeneratedDateDesc(criteriaBuilder, criteriaQuery, root, objs);
                break;
            case LoginStateMaintainService.ID_EQUALS:
                idEquals(criteriaBuilder, criteriaQuery, root, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForAccount(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void expireDateBefore(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            Instant date = ((Date) objs[0]).toInstant();
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder, criteriaQuery, criteriaBuilder.lessThan(root.get("expireDate"), date)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void withoutAccount(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void expireDateBeforeNow(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            Instant date = Instant.now();
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder, criteriaQuery, criteriaBuilder.lessThan(root.get("expireDate"), date)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void serialVersionLowerThanAccount(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    criteriaBuilder.lessThan(root.get("serialVersion"), root.join("account").get("serialVersion"))
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void generatedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("generatedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForAccountGeneratedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )
                );
            }
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("generatedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsDynamic(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )
                );
            }
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    criteriaBuilder.equal(root.get("type"), Constants.LOGIN_STATE_TYPE_DYNAMIC)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsDynamicGeneratedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )
                );
            }
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    criteriaBuilder.equal(root.get("type"), Constants.LOGIN_STATE_TYPE_DYNAMIC)
            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("generatedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsStatic(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )
                );
            }
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    criteriaBuilder.equal(root.get("type"), Constants.LOGIN_STATE_TYPE_STATIC)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsStaticGeneratedDateDesc(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("accountStringId"))
                );
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder,
                        criteriaQuery,
                        CriteriaQueryHelper.equalOrIsNull(
                                criteriaBuilder, root.get("accountStringId"), stringIdKey.getStringId()
                        )

                );
            }
            CriteriaQueryHelper.addRestriction(
                    criteriaBuilder,
                    criteriaQuery,
                    criteriaBuilder.equal(root.get("type"), Constants.LOGIN_STATE_TYPE_STATIC)
            );
            CriteriaQueryHelper.addOrder(criteriaQuery, criteriaBuilder.desc(root.get("generatedDate")));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void idEquals(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<HibernateLoginState> root,
            Object[] objs
    ) {
        try {
            if (Objects.isNull(objs[0])) {
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.isNull(root.get("longId"))
                );
            } else {
                Long id = (Long) objs[0];
                CriteriaQueryHelper.addRestriction(
                        criteriaBuilder, criteriaQuery, criteriaBuilder.equal(root.get("longId"), id)
                );
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
