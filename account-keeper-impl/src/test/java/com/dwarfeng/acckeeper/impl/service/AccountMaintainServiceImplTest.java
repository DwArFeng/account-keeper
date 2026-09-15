package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
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
public class AccountMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(
                new StringIdKey("test-account"), "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0
        );
    }

    @AfterEach
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
            if (Objects.nonNull(account.getKey())) {
                accountMaintainService.deleteIfExists(account.getKey());
            }
        }
    }
}
