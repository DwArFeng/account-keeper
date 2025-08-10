package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PurgeFinishedResult;
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
     * 登录历史被记录时执行的调度。
     *
     * @param loginHistoryRecordInfo 登录历史记录信息。
     * @throws HandlerException 处理器异常。
     */
    void loginHistoryRecorded(LoginHistoryRecordInfo loginHistoryRecordInfo) throws HandlerException;

    /**
     * 派生历史被记录时执行的调度。
     *
     * @param deriveHistoryRecordInfo 派生历史记录信息。
     * @throws HandlerException 处理器异常。
     */
    void deriveHistoryRecorded(DeriveHistoryRecordInfo deriveHistoryRecordInfo) throws HandlerException;

    /**
     * 保护被重置时执行的调度。
     *
     * @throws HandlerException 处理器异常。
     */
    void protectReset() throws HandlerException;

    /**
     * 清除完成时执行的调度。
     *
     * @param result 清除结束结果。
     * @throws HandlerException 处理器异常。
     * @since 1.9.0
     */
    void purgeFinished(PurgeFinishedResult result) throws HandlerException;

    /**
     * 清除失败时执行的调度。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.9.0
     */
    void purgeFailed() throws HandlerException;
}
