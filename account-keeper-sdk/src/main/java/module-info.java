module com.dwarfeng.acckeeper.sdk {

    requires com.dwarfeng.acckeeper.base;
    requires com.dwarfeng.acckeeper.stack;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.data;
    requires com.dwarfeng.subgrade.web;
    requires com.alibaba.fastjson2;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires org.mapstruct;
    requires org.slf4j;

    exports com.dwarfeng.acckeeper.sdk.bean;
    exports com.dwarfeng.acckeeper.sdk.bean.dto;
    exports com.dwarfeng.acckeeper.sdk.bean.entity;
    exports com.dwarfeng.acckeeper.sdk.bean.key;
    exports com.dwarfeng.acckeeper.sdk.bean.key.formatter;
    exports com.dwarfeng.acckeeper.sdk.exception;
    exports com.dwarfeng.acckeeper.sdk.handler;
    exports com.dwarfeng.acckeeper.sdk.handler.lskgen;
    exports com.dwarfeng.acckeeper.sdk.handler.protector;
    exports com.dwarfeng.acckeeper.sdk.handler.pusher;
    exports com.dwarfeng.acckeeper.sdk.handler.resetter;
    exports com.dwarfeng.acckeeper.sdk.util;

    opens com.dwarfeng.acckeeper.sdk.i18n to com.dwarfeng.acckeeper.base;
}
