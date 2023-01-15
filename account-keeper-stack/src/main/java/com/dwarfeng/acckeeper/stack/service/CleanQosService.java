package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 清理 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface CleanQosService extends Service {

    /**
     * 清理服务是否启动。
     *
     * @return 清理服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 启动清理服务。
     *
     * <p>
     * 该方法重复调用安全，多次调用实际只会执行一次。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 停止清理服务。
     *
     * <p>
     * 该方法重复调用安全，多次调用实际只会执行一次。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;
}
