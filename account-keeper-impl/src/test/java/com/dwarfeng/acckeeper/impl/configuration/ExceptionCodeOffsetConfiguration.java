package com.dwarfeng.acckeeper.impl.configuration;

import com.dwarfeng.acckeeper.sdk.exception.ServiceExceptionCodeSuppliers;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionCodeOffsetConfiguration {

    @Value("${com.dwarfeng.acckeeper.acckeeper.exception_code_offset}")
    private int exceptionCodeOffset;
    @Value("${com.dwarfeng.acckeeper.acckeeper.exception_code_offset.subgrade}")
    private int subgradeExceptionCodeOffset;
    @Value("${com.dwarfeng.acckeeper.acckeeper.exception_code_offset.spring_telqos}")
    private int springTelqosExceptionCodeOffset;
    @Value("${com.dwarfeng.acckeeper.acckeeper.exception_code_offset.spring_terminator}")
    private int springTerminatorExceptionCodeOffset;
    @Value("${com.dwarfeng.acckeeper.acckeeper.exception_code_offset.dwarfeng_datamark}")
    private int dwarfengDatamarkExceptionCodeOffset;

    @PostConstruct
    public void init() {
        ServiceExceptionCodeSuppliers.setExceptionCodeOffset(exceptionCodeOffset);
        com.dwarfeng.subgrade.basic.sdk.exception.ServiceExceptionCodeSuppliers.setExceptionCodeOffset(
                subgradeExceptionCodeOffset
        );
        com.dwarfeng.springtelqos.sdk.exception.ServiceExceptionCodeSuppliers.setExceptionCodeOffset(
                springTelqosExceptionCodeOffset
        );
        com.dwarfeng.springterminator.sdk.exception.ServiceExceptionCodeSuppliers.setExceptionCodeOffset(
                springTerminatorExceptionCodeOffset
        );
        com.dwarfeng.datamark.sdk.exception.ServiceExceptionCodeSuppliers.setExceptionCodeOffset(
                dwarfengDatamarkExceptionCodeOffset
        );
    }
}
