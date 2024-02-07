package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class LoginServiceImplTest {

    @Autowired
    private AccountMaintainService accountMaintainService;
    @Autowired
    private AccountOperateService accountOperateService;
    @Autowired
    private LoginService loginService;

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

    @SuppressWarnings("deprecation")
    @Test
    public void testForLogin() throws Exception {
        try {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            LoginState loginState = loginService.login(new LoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "ninja123456", Collections.emptyMap()
            ));
            loginState = loginService.postpone(loginState.getKey());
            try {
                loginService.login(new LoginInfo(
                        zhangSanRegisterInfo.getAccountKey(), "123456", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }
            assertTrue(loginService.isLogin(loginState.getKey()));
            assertFalse(loginService.isLogin(new LongIdKey(loginState.getKey().getLongId() + 1)));
            loginService.logout(loginState.getKey());

            try {
                loginService.login(new LoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.ACCOUNT_DISABLED.getCode(), e.getCode().getCode());
            }
        } finally {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
        }
    }

    @Test
    public void testForDynamicLogin() throws Exception {
        try {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            LoginState loginState = loginService.dynamicLogin(new DynamicLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "ninja123456", "remark", Collections.emptyMap()
            ));
            loginState = loginService.postpone(loginState.getKey());
            try {
                loginService.dynamicLogin(new DynamicLoginInfo(
                        zhangSanRegisterInfo.getAccountKey(), "123456", "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }
            assertTrue(loginService.isLogin(loginState.getKey()));
            assertFalse(loginService.isLogin(new LongIdKey(loginState.getKey().getLongId() + 1)));
            loginService.logout(loginState.getKey());

            try {
                loginService.dynamicLogin(new DynamicLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.ACCOUNT_DISABLED.getCode(), e.getCode().getCode());
            }
        } finally {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
        }
    }

    private static final Long STATIC_LOGIN_EXPIRE_DURATION = 3600000L;

    @Test
    public void testForStaticLogin() throws Exception {
        Date expireDate = new Date(System.currentTimeMillis() + STATIC_LOGIN_EXPIRE_DURATION);
        try {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());

            accountOperateService.register(zhangSanRegisterInfo);
            accountOperateService.register(liSiRegisterInfo);

            LoginState loginState = loginService.staticLogin(new StaticLoginInfo(
                    zhangSanRegisterInfo.getAccountKey(), "ninja123456", expireDate, "remark", Collections.emptyMap()
            ));
            loginState = loginService.postpone(loginState.getKey());
            try {
                loginService.staticLogin(new StaticLoginInfo(
                        zhangSanRegisterInfo.getAccountKey(), "123456", expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.PASSWORD_INCORRECT.getCode(), e.getCode().getCode());
            }
            assertTrue(loginService.isLogin(loginState.getKey()));
            assertFalse(loginService.isLogin(new LongIdKey(loginState.getKey().getLongId() + 1)));
            loginService.logout(loginState.getKey());

            try {
                loginService.staticLogin(new StaticLoginInfo(
                        liSiRegisterInfo.getAccountKey(), "ninja123456", expireDate, "remark", Collections.emptyMap()
                ));
            } catch (ServiceException e) {
                assertEquals(ServiceExceptionCodes.ACCOUNT_DISABLED.getCode(), e.getCode().getCode());
            }
        } finally {
            accountMaintainService.deleteIfExists(zhangSanRegisterInfo.getAccountKey());
            accountMaintainService.deleteIfExists(liSiRegisterInfo.getAccountKey());
        }
    }
}
