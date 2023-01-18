package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class LoginParamRecordMaintainServiceImplTest {

    @Autowired
    private LoginHistoryMaintainService loginHistoryMaintainService;
    @Autowired
    private LoginParamRecordMaintainService loginParamRecordMaintainService;

    private LoginHistory loginHistory;
    private LoginParamRecord loginParamRecord;

    @Before
    public void setUp() {
        loginHistory = new LoginHistory(
                null, "accountId", new Date(), "ipAddress", "location", 12.450, 114.514, 12450
        );
        loginParamRecord = new LoginParamRecord(null, "value");
    }

    @After
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
            loginParamRecordMaintainService.deleteIfExists(loginParamRecord.getKey());
            loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());
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
            loginParamRecordMaintainService.deleteIfExists(loginParamRecord.getKey());
            loginHistoryMaintainService.deleteIfExists(loginHistory.getKey());
        }
    }
}
