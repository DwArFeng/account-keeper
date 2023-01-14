package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginAccountInfo;
import com.dwarfeng.acckeeper.stack.service.LoginAccountInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class LoginAccountInfoServiceImplTest {

    @Autowired
    private LoginAccountInfoMaintainService loginAccountInfoMaintainService;

    private LoginAccountInfo loginAccountInfo;

    @Before
    public void setUp() {
        loginAccountInfo = new LoginAccountInfo(
                new StringIdKey("test.login_account_info"),
                Arrays.asList(new LongIdKey(123L), new LongIdKey(456L), new LongIdKey(789L)),
                3
        );
    }

    @After
    public void tearDown() {
        loginAccountInfo = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            loginAccountInfoMaintainService.insertOrUpdate(loginAccountInfo);

            LoginAccountInfo testLoginAccountInfo = loginAccountInfoMaintainService.get(loginAccountInfo.getKey());
            assertEquals(BeanUtils.describe(loginAccountInfo), BeanUtils.describe(testLoginAccountInfo));
            loginAccountInfoMaintainService.update(loginAccountInfo);
            testLoginAccountInfo = loginAccountInfoMaintainService.get(loginAccountInfo.getKey());
            assertEquals(BeanUtils.describe(loginAccountInfo), BeanUtils.describe(testLoginAccountInfo));
        } finally {
            loginAccountInfoMaintainService.deleteIfExists(loginAccountInfo.getKey());
        }
    }
}
