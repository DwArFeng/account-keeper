package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 保护 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface ProtectQosService extends Service {

    /**
     * 获取指定账户的保护器。
     *
     * @param accountKey 指定的账户。
     * @return 指定的账户的保护器。
     * @throws ServiceException 服务异常。
     */
    Protector getProtector(StringIdKey accountKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
