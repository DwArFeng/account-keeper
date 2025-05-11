package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.acckeeper.stack.service.ProtectorSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ProtectorSupportMaintainServiceImplTest {

    @Autowired
    private ProtectorSupportMaintainService service;

    private final List<ProtectorSupport> protectorSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            ProtectorSupport protectorSupport = new ProtectorSupport(
                    new StringIdKey("protector-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            protectorSupports.add(protectorSupport);
        }
    }

    @After
    public void tearDown() {
        protectorSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (ProtectorSupport protectorSupport : protectorSupports) {
                protectorSupport.setKey(service.insert(protectorSupport));
                service.update(protectorSupport);
                ProtectorSupport testProtectorSupport = service.get(protectorSupport.getKey());
                assertEquals(BeanUtils.describe(protectorSupport), BeanUtils.describe(testProtectorSupport));
            }
        } finally {
            for (ProtectorSupport protectorSupport : protectorSupports) {
                if (Objects.isNull(protectorSupport.getKey())) {
                    continue;
                }
                service.delete(protectorSupport.getKey());
            }
        }
    }
}
