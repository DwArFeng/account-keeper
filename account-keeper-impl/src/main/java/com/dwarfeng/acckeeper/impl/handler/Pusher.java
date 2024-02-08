package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DeriveHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Pusher {

    /**
     * 返回事件推送器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 事件推送器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 历史被记录时执行的保护。
     *
     * @param loginHistory 登陆历史记录信息。
     * @throws HandlerException 处理器异常。
     */
    void loginHistoryRecorded(LoginHistoryRecordInfo loginHistory) throws HandlerException;

    /**
     * 派生历史被记录时执行的保护。
     *
     * @param deriveHistoryRecordInfo 派生历史记录信息。
     * @throws HandlerException 处理器异常。
     */
    void deriveHistoryRecorded(DeriveHistoryRecordInfo deriveHistoryRecordInfo) throws HandlerException;

    /**
     * 保护被重置时执行的保护。
     *
     * @throws HandlerException 处理器异常。
     */
    void protectReset() throws HandlerException;
}
