package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
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
public class LoginStateMaintainServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private LoginStateMaintainService loginStateMaintainService;

    private Account account;
    private LoginState loginState;

    @BeforeEach
    public void setUp() {
        StringIdKey accountKey = new StringIdKey("test.account");
        account = new Account(accountKey, "password", true, "remark", 0, "测试账号", new Date(), 0, 0, 0);
        loginState = new LoginState(
                new StringIdKey("test.login_state"), accountKey, new Date(), 12450, new Date(), 12450, "remark"
        );
    }

    @AfterEach
    public void tearDown() {
        loginState = null;
    }

    @Test
    public void testForCrud() throws Exception {
        try {
            accountMaintainService.insertOrUpdate(account);
            loginStateMaintainService.insertOrUpdate(loginState);

            LoginState testLoginState = loginStateMaintainService.get(loginState.getKey());
            assertEquals(BeanUtils.describe(loginState), BeanUtils.describe(testLoginState));
            loginStateMaintainService.update(loginState);
            testLoginState = loginStateMaintainService.get(loginState.getKey());
            assertEquals(BeanUtils.describe(loginState), BeanUtils.describe(testLoginState));
        } finally {
            if (Objects.nonNull(loginState.getKey())) {
                loginStateMaintainService.deleteIfExists(loginState.getKey());
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
            loginStateMaintainService.insertOrUpdate(loginState);

            assertTrue(loginStateMaintainService.exists(loginState.getKey()));

            accountMaintainService.deleteIfExists(account.getKey());
            assertFalse(loginStateMaintainService.exists(loginState.getKey()));
        } finally {
            loginStateMaintainService.deleteIfExists(loginState.getKey());
            accountMaintainService.deleteIfExists(account.getKey());
        }
    }
}
