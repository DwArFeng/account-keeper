package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.CheckerSupport;
import com.dwarfeng.acckeeper.stack.service.CheckerSupportMaintainService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CheckerSupportMaintainServiceImplTest {

    @Autowired
    private CheckerSupportMaintainService service;

    private final List<CheckerSupport> checkerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            CheckerSupport checkerSupport = new CheckerSupport(
                    new StringIdKey("checker-support-" + (i + 1)), "label", "description", "exampleParam"
            );
            checkerSupports.add(checkerSupport);
        }
    }

    @After
    public void tearDown() {
        checkerSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (CheckerSupport checkerSupport : checkerSupports) {
                checkerSupport.setKey(service.insert(checkerSupport));
                service.update(checkerSupport);
                CheckerSupport testCheckerSupport = service.get(checkerSupport.getKey());
                assertEquals(BeanUtils.describe(checkerSupport), BeanUtils.describe(testCheckerSupport));
            }
        } finally {
            for (CheckerSupport checkerSupport : checkerSupports) {
                service.delete(checkerSupport.getKey());
            }
        }
    }
}
