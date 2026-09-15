package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/acckeeper/impl/spring/application-context*.xml")
public class DeriveHistoryMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private DeriveHistoryMaintainService deriveHistoryMaintainService;

    private Account account;
    private DeriveHistory deriveHistory;

    @BeforeEach
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0);
        deriveHistory = new DeriveHistory(null, "accountId", new Date(), 12450, "deriveRemark");
    }

    @AfterEach
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
            if (Objects.nonNull(deriveHistory.getKey())) {
                deriveHistoryMaintainService.deleteIfExists(deriveHistory.getKey());
            }
            if (Objects.nonNull(account.getKey())) {
                accountMaintainService.deleteIfExists(account.getKey());
            }
        }
    }
}
