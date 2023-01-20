package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PushHandler extends Handler {

    /**
     * 历史被记录时执行的保护。
     *
     * @param loginHistoryRecordInfo 登陆历史记录信息。
     * @throws HandlerException 处理器异常。
     */
    void loginHistoryRecorded(LoginHistoryRecordInfo loginHistoryRecordInfo) throws HandlerException;

    /**
     * 保护被重置时执行的保护。
     *
     * @throws HandlerException 处理器异常。
     */
    void protectReset() throws HandlerException;
}