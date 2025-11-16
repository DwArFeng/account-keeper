package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AccessServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private AccountOperateService accountOperateService;
    @Autowired
    private AccessService accessService;

    private AccountRegisterInfo zhangSanRegisterInfo;
    private AccountRegisterInfo liSiRegisterInfo;

    @Before
    public void setUp() {
        zhangSanRegisterInfo = new AccountRegisterInfo(
                new StringIdKey("zhang_san"), "张三", true, "测试用账号", "ninja123456"
        );
        liSiRegisterInfo = new AccountRegisterInfo(
                new StringIdKey("li_si"), "李四", false, "测试用账号", "ninja123456"
        );
    }

    @After
    public void tearDown() {
        zhangSanRegisterInfo = null;
        liSiRegisterInfo = null;
    }

    @Test
    public void testForDynamicLogin() throws Exception {
        try {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            StringIdKey loginStateKey = accessService.dynamicLogin(new DynamicLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "ninja123456", "remark", Collections.emptyMap()
            )).getLoginStateKey();
            accessService.postpone(new PostponeInfo(loginStateKey));
            try {
                accessService.dynamicLogin(new DynamicLoginInfo(
                        zhangSanRegisterInfo.getAccountKey(), "123456", "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            assertFalse(accessService.authInspect(new AuthInspectInfo(new StringIdKey(StringUtils.EMPTY))).isLogin());
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.dynamicLogin(new DynamicLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.ACCOUNT_DISABLED.getCode(), e.getCode().getCode());
            }
        } finally {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
        }
    }

    private static final Long STATIC_LOGIN_EXPIRE_DURATION = 3600000L;

    @Test
    public void testForStaticLogin() throws Exception {
        Date expireDate = new Date(System.currentTimeMillis() + STATIC_LOGIN_EXPIRE_DURATION);
        try {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            StringIdKey loginStateKey = accessService.staticLogin(new StaticLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "ninja123456", expireDate, "remark", Collections.emptyMap()
            )).getLoginStateKey();
            accessService.postpone(new PostponeInfo(loginStateKey));
            try {
                accessService.staticLogin(new StaticLoginInfo(
                        zhangSanRegisterInfo.getAccountKey(), "123456", expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            assertFalse(accessService.authInspect(new AuthInspectInfo(new StringIdKey(StringUtils.EMPTY))).isLogin());
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.staticLogin(new StaticLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.ACCOUNT_DISABLED.getCode(), e.getCode().getCode());
            }
        } finally {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }
        }
    }
}
