package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 登录状态主键生成 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginStateKeyGenerateQosService extends Service {

    /**
     * 生成登录状态主键。
     *
     * @return 生成的登录状态主键。
     * @throws ServiceException 服务异常。
     */
    StringIdKey generate() throws ServiceException;

    /**
     * 生成登录状态主键。
     *
     * @param size 生成的数量。
     * @return 生成的登录状态主键组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<StringIdKey> generate(int size) throws ServiceException;
}
