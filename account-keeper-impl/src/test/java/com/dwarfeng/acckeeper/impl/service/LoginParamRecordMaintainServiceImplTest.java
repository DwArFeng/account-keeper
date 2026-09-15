package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
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
public class LoginParamRecordMaintainServiceImplTest {

    @Autowired
    private LoginHistoryMaintainService loginHistoryMaintainService;
    @Autowired
    private LoginParamRecordMaintainService loginParamRecordMaintainService;

    private LoginHistory loginHistory;
    private LoginParamRecord loginParamRecord;

    @BeforeEach
    public void setUp() {
        loginHistory = new LoginHistory(null, "accountId", new Date(), 12450, "message", 12450, "loginRemark");
        loginParamRecord = new LoginParamRecord(null, "value");
    }

    @AfterEach
    public void tearDown() {
        loginHistory = null;
        loginParamRecord = null;
    }

    @Test
    public void testForCurd() throws Exception {
        try {
            loginHistory.setKey(loginHistoryMaintainService.insert(loginHistory));
            loginParamRecord.setKey(new RecordKey(loginHistory.getKey().getLongId(), "test.record_id"));
            loginParamRecordMaintainService.insertOrUpdate(loginParamRecord);
            loginParamRecordMaintainService.update(loginParamRecord);
            LoginParamRecord testLoginParamRecord = loginParamRecordMaintainService.get(loginParamRecord.getKey());
            assertEquals(BeanUtils.describe(loginParamRecord), BeanUtils.describe(testLoginParamRecord));
        } finally {
            if (Objects.nonNull(loginParamRecord.getKey())) {
                loginParamRecordMaintainService.deleteIfExists(loginParamRecord.getKey());
            }
            if (Objects.nonNull(loginHistory.getKey())) {
                loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());
            }
        }
    }

    @Test
    public void testForLoginHistoryCascade() throws Exception {
        try {
            loginHistory.setKey(loginHistoryMaintainService.insert(loginHistory));
            loginParamRecord.setKey(new RecordKey(loginHistory.getKey().getLongId(), "test.record_id"));
            loginParamRecordMaintainService.insertOrUpdate(loginParamRecord);

            assertTrue(loginParamRecordMaintainService.exists(loginParamRecord.getKey()));

            loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());

            assertFalse(loginParamRecordMaintainService.exists(loginParamRecord.getKey()));
        } finally {
            if (Objects.nonNull(loginParamRecord.getKey())) {
                loginParamRecordMaintainService.deleteIfExists(loginParamRecord.getKey());
            }
            if (Objects.nonNull(loginHistory.getKey())) {
                loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());
            }
        }
    }
}
