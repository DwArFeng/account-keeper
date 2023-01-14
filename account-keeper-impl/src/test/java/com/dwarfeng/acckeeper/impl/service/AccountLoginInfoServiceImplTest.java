package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.AccountLoginInfo;
import com.dwarfeng.acckeeper.stack.service.AccountLoginInfoMaintainService;
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
public class AccountLoginInfoServiceImplTest {

    @Autowired
    private AccountLoginInfoMaintainService accountLoginInfoMaintainService;

    private AccountLoginInfo accountLoginInfo;

    @Before
    public void setUp() {
        accountLoginInfo = new AccountLoginInfo(
                new StringIdKey("test.account_login_info"),
                Arrays.asList(new LongIdKey(123L), new LongIdKey(456L), new LongIdKey(789L))
        );
    }

    @After
    public void tearDown() {
        accountLoginInfo = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            accountLoginInfoMaintainService.insertOrUpdate(accountLoginInfo);

            AccountLoginInfo testAccountLoginInfo = accountLoginInfoMaintainService.get(accountLoginInfo.getKey());
            assertEquals(BeanUtils.describe(accountLoginInfo), BeanUtils.describe(testAccountLoginInfo));
            accountLoginInfoMaintainService.update(accountLoginInfo);
            testAccountLoginInfo = accountLoginInfoMaintainService.get(accountLoginInfo.getKey());
            assertEquals(BeanUtils.describe(accountLoginInfo), BeanUtils.describe(testAccountLoginInfo));
        } finally {
            accountLoginInfoMaintainService.deleteIfExists(accountLoginInfo.getKey());
        }
    }
}
