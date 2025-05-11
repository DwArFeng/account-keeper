package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AccountOperateServiceImplTest {

    @Autowired
    private AccountOperateService accountOperateService;
    @Autowired
    private AccountMaintainService accountMaintainService;

    private AccountRegisterInfo zhangSanRegisterInfo;
    private AccountRegisterInfo liSiRegisterInfo;
    private AccountRegisterInfo wangWuRegisterInfo;

    private AccountUpdateInfo zhangSanUpdateInfo;
    private AccountUpdateInfo liSiUpdateInfo;
    private AccountUpdateInfo wangWuUpdateInfo;

    @Before
    public void setUp() {
        zhangSanRegisterInfo = new AccountRegisterInfo(new StringIdKey("zhang_san"), "张三", true, "测试用账号", "ninja123456");
        liSiRegisterInfo = new AccountRegisterInfo(new StringIdKey("li_si"), "李四", false, "测试用账号", "ninja123456");
        wangWuRegisterInfo = new AccountRegisterInfo(new StringIdKey("wang_wu"), "王五", true, "测试用账号", "ninja123456");

        zhangSanUpdateInfo = new AccountUpdateInfo(new StringIdKey("zhang_san"), "张三", true, "测试用账号");
        liSiUpdateInfo = new AccountUpdateInfo(new StringIdKey("li_si"), "李四", false, "测试用账号");
        wangWuUpdateInfo = new AccountUpdateInfo(new StringIdKey("wang_wu"), "王五", true, "测试用账号");
    }

    @After
    public void tearDown() {
        zhangSanRegisterInfo = null;
        liSiRegisterInfo = null;
        wangWuRegisterInfo = null;

        zhangSanUpdateInfo = null;
        liSiUpdateInfo = null;
        wangWuUpdateInfo = null;
    }

    @Test
    public void testForRegister() throws ServiceException {
        Account zhangSan;
        Account liSi;
        Account wangWu;
        try {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(wangWuRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(wangWuRegisterInfo.getAccountKey());
            }

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);
            accountOperateService.register(wangWuRegisterInfo);

            zhangSan = accountMaintainService.get(zhangSanRegisterInfo.getAccountKey());
            liSi = accountMaintainService.get(liSiRegisterInfo.getAccountKey());
            wangWu = accountMaintainService.get(wangWuRegisterInfo.getAccountKey());

            assertEquals(zhangSanRegisterInfo.getDisplayName(), zhangSan.getDisplayName());
            assertEquals(liSiRegisterInfo.getDisplayName(), liSi.getDisplayName());
            assertEquals(wangWuRegisterInfo.getDisplayName(), wangWu.getDisplayName());

            assertEquals(zhangSanRegisterInfo.isEnabled(), zhangSan.isEnabled());
            assertEquals(liSiRegisterInfo.isEnabled(), liSi.isEnabled());
            assertEquals(wangWuRegisterInfo.isEnabled(), wangWu.isEnabled());

            assertEquals(zhangSanRegisterInfo.getRemark(), zhangSan.getRemark());
            assertEquals(liSiRegisterInfo.getRemark(), liSi.getRemark());
            assertEquals(wangWuRegisterInfo.getRemark(), wangWu.getRemark());

            accountOperateService.update(zhangSanUpdateInfo);
            accountOperateService.update(liSiUpdateInfo);
            accountOperateService.update(wangWuUpdateInfo);

            zhangSan = accountMaintainService.get(zhangSanRegisterInfo.getAccountKey());
            liSi = accountMaintainService.get(liSiRegisterInfo.getAccountKey());
            wangWu = accountMaintainService.get(wangWuRegisterInfo.getAccountKey());

            assertEquals(zhangSanUpdateInfo.getDisplayName(), zhangSan.getDisplayName());
            assertEquals(liSiUpdateInfo.getDisplayName(), liSi.getDisplayName());
            assertEquals(wangWuUpdateInfo.getDisplayName(), wangWu.getDisplayName());

            assertEquals(zhangSanUpdateInfo.isEnabled(), zhangSan.isEnabled());
            assertEquals(liSiUpdateInfo.isEnabled(), liSi.isEnabled());
            assertEquals(wangWuUpdateInfo.isEnabled(), wangWu.isEnabled());

            assertEquals(zhangSanUpdateInfo.getRemark(), zhangSan.getRemark());
            assertEquals(liSiUpdateInfo.getRemark(), liSi.getRemark());
            assertEquals(wangWuUpdateInfo.getRemark(), wangWu.getRemark());

            accountOperateService.delete(zhangSan.getKey());
            accountOperateService.delete(liSi.getKey());
            accountOperateService.delete(wangWu.getKey());

            assertFalse(accountMaintainService.exists(zhangSan.getKey()));
            assertFalse(accountMaintainService.exists(liSi.getKey()));
            assertFalse(accountMaintainService.exists(wangWu.getKey()));
        } finally {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(wangWuRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(wangWuRegisterInfo.getAccountKey());
            }
        }
    }

    @Test
    public void testForPassword() throws ServiceException {
        try {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(wangWuRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(wangWuRegisterInfo.getAccountKey());
            }

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);
            accountOperateService.register(wangWuRegisterInfo);

            assertTrue(accountOperateService.checkPassword(new PasswordCheckInfo(zhangSanRegisterInfo.getAccountKey(), "ninja123456")));
            assertFalse(accountOperateService.checkPassword(new PasswordCheckInfo(zhangSanRegisterInfo.getAccountKey(), "123456")));

            accountOperateService.updatePassword(new PasswordUpdateInfo(liSiRegisterInfo.getAccountKey(), "ninja123456", "123456"));
            assertFalse(accountOperateService.checkPassword(new PasswordCheckInfo(liSiRegisterInfo.getAccountKey(), "ninja123456")));
            assertTrue(accountOperateService.checkPassword(new PasswordCheckInfo(liSiRegisterInfo.getAccountKey(), "123456")));

            try {
                accountOperateService.updatePassword(new PasswordUpdateInfo(liSiRegisterInfo.getAccountKey(), "ninja123456", "123456"));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }

            accountOperateService.resetPassword(new PasswordResetInfo(wangWuRegisterInfo.getAccountKey(), "123456"));
            // Account wangWu = accountMaintainService.get(wangWuRegisterInfo.getAccountKey());
            // TODO 进一步完善测试内容。
        } finally {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(wangWuRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(wangWuRegisterInfo.getAccountKey());
            }
        }
    }
}
