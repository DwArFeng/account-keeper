module com.dwarfeng.acckeeper.node.all.he {

    requires com.dwarfeng.acckeeper.base;
    requires com.dwarfeng.acckeeper.stack;
    requires com.dwarfeng.acckeeper.sdk;
    requires com.dwarfeng.acckeeper.impl;
    requires com.alibaba.fastjson2;
    requires com.dwarfeng.datamark.api;
    requires com.dwarfeng.datamark.core;
    requires com.dwarfeng.sfds.api;
    requires com.dwarfeng.sfds.stack;
    requires com.dwarfeng.springtelqos.api;
    requires com.dwarfeng.springtelqos.core;
    requires com.dwarfeng.springterminator.api;
    requires com.dwarfeng.springterminator.core;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.data;
    requires jakarta.annotation;
    requires org.slf4j;
    requires spring.aop;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.data.redis;

    exports com.dwarfeng.acckeeper.node.all.he.configuration;
    exports com.dwarfeng.acckeeper.node.all.he.handler;
    exports com.dwarfeng.acckeeper.node.all.he.launcher;

    opens com.dwarfeng.acckeeper.node.all.he.configuration to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.acckeeper.node.all.he.handler to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.acckeeper.node.all.he.i18n to com.dwarfeng.acckeeper.base;
}
