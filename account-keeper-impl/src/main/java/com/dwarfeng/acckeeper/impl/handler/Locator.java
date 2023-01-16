package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.handler.LocateHandler.LocateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nullable;

/**
 * 定位器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface Locator {

    /**
     * 返回定位器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 定位器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 对指定的 IP 地址进行定位。
     *
     * <p>
     * 注意：入口参数可能为 <code>null</code>。
     *
     * @param ipAddress IP 地址。
     * @return 定位结果。
     * @throws HandlerException 处理器异常。
     */
    LocateResult locate(@Nullable String ipAddress) throws HandlerException;
}
