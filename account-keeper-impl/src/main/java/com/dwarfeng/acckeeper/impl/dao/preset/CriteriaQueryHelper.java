package com.dwarfeng.acckeeper.impl.dao.preset;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Jakarta Criteria 查询配置辅助类。
 *
 * <p>
 * 该类用于保持旧 Hibernate Criteria 逐项追加查询条件和排序的语义，避免后续调用覆盖已经配置的限制或排序。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
final class CriteriaQueryHelper {

    public static void addRestriction(
            CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Predicate predicate
    ) {
        Predicate restriction = criteriaQuery.getRestriction();
        criteriaQuery.where(Objects.isNull(restriction) ? predicate : criteriaBuilder.and(restriction, predicate));
    }

    public static Predicate equalOrIsNull(
            CriteriaBuilder criteriaBuilder, Expression<?> expression, Object value
    ) {
        return Objects.isNull(value) ? criteriaBuilder.isNull(expression) : criteriaBuilder.equal(expression, value);
    }

    public static Predicate likeAnywhere(
            CriteriaBuilder criteriaBuilder, Expression<String> expression, String pattern
    ) {
        return criteriaBuilder.like(expression, "%" + pattern + "%");
    }

    public static void addOrder(CriteriaQuery<?> criteriaQuery, Order order) {
        List<Order> orders = new ArrayList<>(criteriaQuery.getOrderList());
        orders.add(order);
        criteriaQuery.orderBy(orders);
    }

    private CriteriaQueryHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
