package com.dwarfeng.acckeeper.impl.handler.pusher;

import com.dwarfeng.acckeeper.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 将信息输出至日志的推送器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class LogPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "log";

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPusher.class);

    private static final String LEVEL_TRACE = "TRACE";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";

    @Value("${pusher.log.log_level}")
    private String logLevel;

    public LogPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void loginHistoryRecorded(LoginHistoryRecordInfo loginHistory) throws HandlerException {
        String title = "登录历史被记录事件:";
        String message = Objects.toString(loginHistory);
        logData(title, message);
    }

    @Override
    public void deriveHistoryRecorded(DeriveHistoryRecordInfo deriveHistoryRecordInfo) throws HandlerException {
        String title = "派生历史被记录事件:";
        String message = Objects.toString(deriveHistoryRecordInfo);
        logData(title, message);
    }

    @Override
    public void protectReset() throws HandlerException {
        String title = "保护重置事件:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    private void logData(String title, String message) throws HandlerException {
        String logLevel = this.logLevel.toUpperCase();
        logString(title, logLevel);
        if (StringUtils.isNotEmpty(message)) {
            logString(message, logLevel);
        }
    }

    private void logString(String title, String logLevel) throws HandlerException {
        switch (logLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(title);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(title);
                return;
            case LEVEL_INFO:
                LOGGER.info(title);
                return;
            case LEVEL_WARN:
                LOGGER.warn(title);
                return;
            case LEVEL_ERROR:
                LOGGER.error(title);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + logLevel);
        }
    }

    @Override
    public String toString() {
        return "LogPusher{" +
                "pusherType='" + pusherType + '\'' +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
