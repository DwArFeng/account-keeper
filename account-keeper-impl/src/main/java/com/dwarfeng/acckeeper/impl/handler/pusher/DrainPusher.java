package com.dwarfeng.acckeeper.impl.handler.pusher;

import com.dwarfeng.acckeeper.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import org.springframework.stereotype.Component;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String SUPPORT_TYPE = "drain";

    public DrainPusher() {
        super(SUPPORT_TYPE);
    }

    @Override
    public void loginHistoryRecorded(LoginHistoryRecordInfo loginHistory) {
    }

    @Override
    public void deriveHistoryRecorded(DeriveHistoryRecordInfo deriveHistoryRecordInfo) {
    }

    @Override
    public void protectReset() {
    }
}
