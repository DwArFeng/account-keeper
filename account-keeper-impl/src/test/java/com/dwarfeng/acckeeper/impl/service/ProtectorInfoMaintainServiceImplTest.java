package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/acckeeper/impl/spring/application-context*.xml")
public class ProtectorInfoMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private ProtectorInfoMaintainService protectorInfoMaintainService;

    private Account account;
    private ProtectorInfo protectorInfo;

    @BeforeEach
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0);
        protectorInfo = new ProtectorInfo(accountKey, "type", "param", "remark");
    }

    @AfterEach
    public void tearDown() {
        account = null;
        protectorInfo = null;
    }

    @Test
    public void testForCurd() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            protectorInfoMaintainService.insertOrUpdate(protectorInfo);
            protectorInfoMaintainService.update(protectorInfo);
            ProtectorInfo testProtectorInfo = protectorInfoMaintainService.get(protectorInfo.getKey());
            assertEquals(BeanUtils.describe(protectorInfo), BeanUtils.describe(testProtectorInfo));
        } finally {
            if (Objects.nonNull(protectorInfo.getKey())) {
                protectorInfoMaintainService.deleteIfExists(protectorInfo.getKey());
            }
            if (Objects.nonNull(account.getKey())) {
                accountMaintainService.deleteIfExists(account.getKey());
            }
        }
    }

    @Test
    public void testForAccountCascade() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            protectorInfoMaintainService.insertOrUpdate(protectorInfo);

            assertTrue(protectorInfoMaintainService.exists(protectorInfo.getKey()));

            accountMaintainService.deleteIfExists(account.getKey());

            assertFalse(protectorInfoMaintainService.exists(protectorInfo.getKey()));
        } finally {
            if (Objects.nonNull(protectorInfo.getKey())) {
                protectorInfoMaintainService.deleteIfExists(protectorInfo.getKey());
            }
            if (Objects.nonNull(account.getKey())) {
                accountMaintainService.deleteIfExists(account.getKey());
            }
        }
    }
}
