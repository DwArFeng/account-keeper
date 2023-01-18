package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.CheckerInfo;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.CheckerInfoMaintainService;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CheckerInfoMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private CheckerInfoMaintainService checkerInfoMaintainService;

    private Account account;
    private CheckerInfo checkerInfo;

    @Before
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date());
        checkerInfo = new CheckerInfo(accountKey, "type", "param", "remark");
    }

    @After
    public void tearDown() {
        account = null;
        checkerInfo = null;
    }

    @Test
    public void testForCurd() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            checkerInfoMaintainService.insertOrUpdate(checkerInfo);
            checkerInfoMaintainService.update(checkerInfo);
            CheckerInfo testCheckerInfo = checkerInfoMaintainService.get(checkerInfo.getKey());
            assertEquals(BeanUtils.describe(checkerInfo), BeanUtils.describe(testCheckerInfo));
        } finally {
            checkerInfoMaintainService.deleteIfExists(checkerInfo.getKey());
            accountMaintainService.deleteIfExists(account.getKey());
        }
    }

    @Test
    public void testForAccountCascade() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            checkerInfoMaintainService.insertOrUpdate(checkerInfo);

            assertTrue(checkerInfoMaintainService.exists(checkerInfo.getKey()));

            accountMaintainService.deleteIfExists(account.getKey());

            assertFalse(checkerInfoMaintainService.exists(checkerInfo.getKey()));
        } finally {
            checkerInfoMaintainService.deleteIfExists(checkerInfo.getKey());
            accountMaintainService.deleteIfExists(account.getKey());
        }
    }
}
