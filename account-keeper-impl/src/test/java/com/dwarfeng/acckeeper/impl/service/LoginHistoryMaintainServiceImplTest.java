package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
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
public class LoginHistoryMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private LoginHistoryMaintainService loginHistoryMaintainService;

    private Account account;
    private LoginHistory loginHistory;

    @BeforeEach
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0);
        loginHistory = new LoginHistory(null, "accountId", new Date(), 12450, "message", 12450, "loginRemark");
    }

    @AfterEach
    public void tearDown() {
        loginHistory = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            loginHistory.setKey(loginHistoryMaintainService.insertOrUpdate(loginHistory));

            LoginHistory testLoginHistory = loginHistoryMaintainService.get(loginHistory.getKey());
            assertEquals(BeanUtils.describe(loginHistory), BeanUtils.describe(testLoginHistory));
            loginHistoryMaintainService.update(loginHistory);
            testLoginHistory = loginHistoryMaintainService.get(loginHistory.getKey());
            assertEquals(BeanUtils.describe(loginHistory), BeanUtils.describe(testLoginHistory));
        } finally {
            if (Objects.nonNull(loginHistory.getKey())) {
                loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());
            }
            if (Objects.nonNull(account.getKey())) {
                accountMaintainService.deleteIfExists(account.getKey());
            }
        }
    }
}
