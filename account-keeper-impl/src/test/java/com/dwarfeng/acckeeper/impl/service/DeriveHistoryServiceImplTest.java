package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
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
public class DeriveHistoryServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private DeriveHistoryMaintainService deriveHistoryMaintainService;

    private Account account;
    private DeriveHistory deriveHistory;

    @Before
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date(), 0, 0);
        deriveHistory = new DeriveHistory(null, "accountId", new Date(), 12450, "deriveRemark");
    }

    @After
    public void tearDown() {
        deriveHistory = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            deriveHistory.setKey(deriveHistoryMaintainService.insertOrUpdate(deriveHistory));

            DeriveHistory testDeriveHistory = deriveHistoryMaintainService.get(deriveHistory.getKey());
            assertEquals(BeanUtils.describe(deriveHistory), BeanUtils.describe(testDeriveHistory));
            deriveHistoryMaintainService.update(deriveHistory);
            testDeriveHistory = deriveHistoryMaintainService.get(deriveHistory.getKey());
            assertEquals(BeanUtils.describe(deriveHistory), BeanUtils.describe(testDeriveHistory));
        } finally {
            deriveHistoryMaintainService.deleteIfExists(deriveHistory.getKey());
            accountMaintainService.deleteIfExists(account.getKey());
        }
    }
}
