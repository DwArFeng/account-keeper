package com.dwarfeng.acckeeper.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public final class Constants {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_PASSED = 0;
    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_ACCOUNT_NOT_EXISTS = 10;
    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_ACCOUNT_DISABLED = 20;
    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_PASSWORD_INCORRECT = 30;
    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_PROTECTOR_PROHIBITED = 40;
    @LoginResponseCodeItem
    public static final int LOGIN_RESPONSE_CODE_PROTECTOR_INFO_NOT_EXISTS = 50;

    @LoginAlarmLevelItem
    public static final int PROTECTOR_MESSAGE_LEVEL_INFO = 0;
    @LoginAlarmLevelItem
    public static final int PROTECTOR_MESSAGE_LEVEL_WARN = 10;
    @LoginAlarmLevelItem
    public static final int PROTECTOR_MESSAGE_LEVEL_DANGER = 20;

    @LoginStateTypeItem
    public static final int LOGIN_STATE_TYPE_DYNAMIC = 0;
    @LoginStateTypeItem
    public static final int LOGIN_STATE_TYPE_STATIC = 10;

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> loginResponseCodeSpace = null;
    private static List<Integer> loginAlarmLevelSpace = null;
    private static List<Integer> loginStateTypeSpace = null;

    /**
     * 登录响应代码的空间。
     *
     * @return 登录响应代码的空间。
     * @since 1.6.0
     */
    public static List<Integer> loginResponseCodeSpace() {
        if (Objects.nonNull(loginResponseCodeSpace)) {
            return loginResponseCodeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(loginResponseCodeSpace)) {
                return loginResponseCodeSpace;
            }
            initLoginResponseCodeSpace();
            return loginResponseCodeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initLoginResponseCodeSpace() {
        List<Integer> result = new ArrayList<>();

        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(LoginResponseCodeItem.class)) {
                continue;
            }
            Integer value;
            try {
                value = (Integer) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        loginResponseCodeSpace = Collections.unmodifiableList(result);
    }

    /**
     * 保护器消息等级的空间。
     *
     * @return 保护器消息等级的空间。
     * @since 1.6.0
     */
    public static List<Integer> loginAlarmLevelSpace() {
        if (Objects.nonNull(loginAlarmLevelSpace)) {
            return loginAlarmLevelSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(loginAlarmLevelSpace)) {
                return loginAlarmLevelSpace;
            }
            initLoginAlarmLevelSpace();
            return loginAlarmLevelSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initLoginAlarmLevelSpace() {
        List<Integer> result = new ArrayList<>();

        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(LoginAlarmLevelItem.class)) {
                continue;
            }
            Integer value;
            try {
                value = (Integer) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        loginAlarmLevelSpace = Collections.unmodifiableList(result);
    }

    /**
     * 登录状态类型的空间。
     *
     * @return 登录状态类型的空间。
     * @since 1.7.0
     */
    public static List<Integer> loginStateTypeSpace() {
        if (Objects.nonNull(loginStateTypeSpace)) {
            return loginStateTypeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(loginStateTypeSpace)) {
                return loginStateTypeSpace;
            }
            initLoginStateTypeSpace();
            return loginStateTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initLoginStateTypeSpace() {
        List<Integer> result = new ArrayList<>();

        Field[] declaredFields = Constants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(LoginStateTypeItem.class)) {
                continue;
            }
            Integer value;
            try {
                value = (Integer) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        loginStateTypeSpace = Collections.unmodifiableList(result);
    }

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
