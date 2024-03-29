package com.dwarfeng.acckeeper.sdk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保护器消息等级条目。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAlarmLevelItem {
}
