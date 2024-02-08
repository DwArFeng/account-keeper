package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AccountServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;

    private Account account;

    @Before
    public void setUp() {
        account = new Account(
                new StringIdKey("test-account"), "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0
        );
    }

    @After
    public void tearDown() {
        account = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);

            Account testAccount = accountMaintainService.get(account.getKey());
            assertEquals(BeanUtils.describe(account), BeanUtils.describe(testAccount));
            accountMaintainService.update(account);
            testAccount = accountMaintainService.get(account.getKey());
            assertEquals(BeanUtils.describe(account), BeanUtils.describe(testAccount));
        } finally {
            accountMaintainService.deleteIfExists(account.getKey());
        }
    }
}
