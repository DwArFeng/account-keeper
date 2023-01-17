package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectorVariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ProtectorVariableMaintainServiceImplTest {

    @Autowired
    private ProtectorInfoMaintainService protectorInfoMaintainService;
    @Autowired
    private ProtectorVariableMaintainService protectorVariableMaintainService;

    private ProtectorInfo protectorInfo;
    private ProtectorVariable protectorVariable;

    @Before
    public void setUp() {
        protectorInfo = new ProtectorInfo(new StringIdKey("test.protector_info"), "type", "param", "remark");
        protectorVariable = new ProtectorVariable(
                new ProtectorVariableKey("test.protector_info", "test.variable_id"), "value", "remark"
        );
    }

    @After
    public void tearDown() {
        protectorInfo = null;
        protectorVariable = null;
    }

    @Test
    public void testForCurd() throws Exception {
        try {
            protectorInfoMaintainService.insertOrUpdate(protectorInfo);
            protectorVariableMaintainService.insertOrUpdate(protectorVariable);
            protectorVariableMaintainService.update(protectorVariable);
            ProtectorVariable testProtectorVariable = protectorVariableMaintainService.get(protectorVariable.getKey());
            assertEquals(BeanUtils.describe(protectorVariable), BeanUtils.describe(testProtectorVariable));
        } finally {
            protectorVariableMaintainService.deleteIfExists(protectorVariable.getKey());
            protectorInfoMaintainService.deleteIfExists(protectorInfo.getKey());
        }
    }

    @Test
    public void testForProtectorInfoCascade() throws Exception {
        try {
            protectorInfoMaintainService.insertOrUpdate(protectorInfo);
            protectorVariableMaintainService.insertOrUpdate(protectorVariable);

            assertTrue(protectorVariableMaintainService.exists(protectorVariable.getKey()));

            protectorInfoMaintainService.deleteIfExists(protectorInfo.getKey());

            assertFalse(protectorVariableMaintainService.exists(protectorVariable.getKey()));
        } finally {
            protectorVariableMaintainService.deleteIfExists(protectorVariable.getKey());
            protectorInfoMaintainService.deleteIfExists(protectorInfo.getKey());
        }
    }
}
