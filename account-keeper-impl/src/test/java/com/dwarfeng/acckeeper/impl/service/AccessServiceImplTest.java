package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.sdk.exception.ServiceExceptionCodeSuppliers;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.basic.stack.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/acckeeper/impl/spring/application-context*.xml")
public class AccessServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private AccountOperateService accountOperateService;
    @Autowired
    private AccessService accessService;

    private AccountRegisterInfo zhangSanRegisterInfo;
    private AccountRegisterInfo liSiRegisterInfo;

    @BeforeEach
    public void setUp() {
        zhangSanRegisterInfo = new AccountRegisterInfo(
                new StringIdKey("zhang_san"), "张三", true, "测试用账号", "ninja123456"
        );
        liSiRegisterInfo = new AccountRegisterInfo(
                new StringIdKey("li_si"), "李四", false, "测试用账号", "ninja123456"
        );
    }

    @AfterEach
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
                assertEquals(ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT.get().getCode(), e.getCode().getCode());
            }
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            assertFalse(accessService.authInspect(new AuthInspectInfo(new StringIdKey(StringUtils.EMPTY))).isLogin());
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.dynamicLogin(new DynamicLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_DISABLED.get().getCode(), e.getCode().getCode());
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
                assertEquals(ServiceExceptionCodeSuppliers.PASSWORD_INCORRECT.get().getCode(), e.getCode().getCode());
            }
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            assertFalse(accessService.authInspect(new AuthInspectInfo(new StringIdKey(StringUtils.EMPTY))).isLogin());
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.staticLogin(new StaticLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_DISABLED.get().getCode(), e.getCode().getCode());
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

    @Test
    public void testTrustedDynamicLogin() throws Exception {
        try {
            if (Objects.nonNull(zhangSanRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            }
            if (Objects.nonNull(liSiRegisterInfo.getAccountKey())) {
                accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
            }

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            StringIdKey loginStateKey = accessService.trustedDynamicLogin(new TrustedDynamicLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "remark", Collections.emptyMap()
            )).getLoginStateKey();
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            accessService.postpone(new PostponeInfo(loginStateKey));
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.trustedDynamicLogin(new TrustedDynamicLoginInfo(
                        new StringIdKey("non_existent"), "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_NOT_EXISTS.get().getCode(), e.getCode().getCode());
            }

            try {
                accessService.trustedDynamicLogin(new TrustedDynamicLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_DISABLED.get().getCode(), e.getCode().getCode());
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

    @Test
    public void testTrustedStaticLogin() throws Exception {
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

            StringIdKey loginStateKey = accessService.trustedStaticLogin(new TrustedStaticLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), expireDate, "remark", Collections.emptyMap()
            )).getLoginStateKey();
            assertTrue(accessService.authInspect(new AuthInspectInfo(loginStateKey)).isLogin());
            accessService.logout(new LogoutInfo(loginStateKey));

            try {
                accessService.trustedStaticLogin(new TrustedStaticLoginInfo(
                        new StringIdKey("non_existent"), expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_NOT_EXISTS.get().getCode(), e.getCode().getCode());
            }

            try {
                accessService.trustedStaticLogin(new TrustedStaticLoginInfo(
                        liSiRegisterInfo.getAccountKey(), expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodeSuppliers.ACCOUNT_DISABLED.get().getCode(), e.getCode().getCode());
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
