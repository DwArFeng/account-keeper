module com.dwarfeng.acckeeper.stack {

    requires com.dwarfeng.acckeeper.base;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.data;
    requires com.dwarfeng.subgrade.cache;
    requires com.dwarfeng.subgrade.lifecycle;
    requires com.dwarfeng.subgrade.lock;
    requires org.jetbrains.annotations;

    exports com.dwarfeng.acckeeper.stack.bean.dto;
    exports com.dwarfeng.acckeeper.stack.bean.entity;
    exports com.dwarfeng.acckeeper.stack.bean.key;
    exports com.dwarfeng.acckeeper.stack.cache;
    exports com.dwarfeng.acckeeper.stack.dao;
    exports com.dwarfeng.acckeeper.stack.exception;
    exports com.dwarfeng.acckeeper.stack.handler;
    exports com.dwarfeng.acckeeper.stack.service;

    opens com.dwarfeng.acckeeper.stack.i18n to com.dwarfeng.acckeeper.base;
}
