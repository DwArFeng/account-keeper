module com.dwarfeng.acckeeper.api {

    requires com.dwarfeng.acckeeper.stack;
    requires com.dwarfeng.acckeeper.sdk;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.web;
    requires org.apache.commons.lang3;
    requires spring.context;

    exports com.dwarfeng.acckeeper.api.integration.subgrade;
}
