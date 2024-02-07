package com.dwarfeng.acckeeper.impl.dao.preset;

import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class LoginStatePresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT:
                childForAccount(criteria, objs);
                break;
            case LoginStateMaintainService.EXPIRE_DATE_BEFORE:
                expireDateBefore(criteria, objs);
                break;
            // ↓清理作业使用。
            case LoginStateMaintainService.WITHOUT_ACCOUNT:
                withoutAccount(criteria, objs);
                break;
            case LoginStateMaintainService.EXPIRE_DATE_BEFORE_NOW:
                expireDateBeforeNow(criteria, objs);
                break;
            case LoginStateMaintainService.SERIAL_VERSION_LOWER_THAN_ACCOUNT:
                serialVersionLowerThanAccount(criteria, objs);
                break;
            // ↑清理作业使用。
            case LoginStateMaintainService.GENERATED_DATE_DESC:
                generatedDateDesc(criteria, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_GENERATED_DATE_DESC:
                childForAccountGeneratedDateDesc(criteria, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC:
                childForAccountTypeEqualsDynamic(criteria, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC_GENERATED_DATE_DESC:
                childForAccountTypeEqualsDynamicGeneratedDateDesc(criteria, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC:
                childForAccountTypeEqualsStatic(criteria, objs);
                break;
            case LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC_GENERATED_DATE_DESC:
                childForAccountTypeEqualsStaticGeneratedDateDesc(criteria, objs);
                break;
            case LoginStateMaintainService.ID_EQUALS:
                idEquals(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void childForAccount(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void expireDateBefore(DetachedCriteria criteria, Object[] objs) {
        try {
            Date date = (Date) objs[0];
            criteria.add(Restrictions.lt("expireDate", date));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void withoutAccount(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.add(Restrictions.isNull("accountStringId"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void expireDateBeforeNow(DetachedCriteria criteria, Object[] objs) {
        try {
            Date date = new Date();
            criteria.add(Restrictions.lt("expireDate", date));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void serialVersionLowerThanAccount(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.createAlias("account", "a");
            criteria.add(Restrictions.ltProperty("serialVersion", "a.serialVersion"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void generatedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            criteria.addOrder(Order.desc("generatedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForAccountGeneratedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
            criteria.addOrder(Order.desc("generatedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsDynamic(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
            criteria.add(Restrictions.eq("type", Constants.LOGIN_STATE_TYPE_DYNAMIC));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsDynamicGeneratedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
            criteria.add(Restrictions.eq("type", Constants.LOGIN_STATE_TYPE_DYNAMIC));
            criteria.addOrder(Order.desc("generatedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsStatic(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
            criteria.add(Restrictions.eq("type", Constants.LOGIN_STATE_TYPE_STATIC));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForAccountTypeEqualsStaticGeneratedDateDesc(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("accountStringId"));
            } else {
                StringIdKey stringIdKey = (StringIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("accountStringId", stringIdKey.getStringId()));
            }
            criteria.add(Restrictions.eq("type", Constants.LOGIN_STATE_TYPE_STATIC));
            criteria.addOrder(Order.desc("generatedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void idEquals(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("longId"));
            } else {
                Long id = (Long) objs[0];
                criteria.add(Restrictions.eq("longId", id));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
