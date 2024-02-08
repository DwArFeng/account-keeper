package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface DeriveQosService extends Service {

    /**
     * 动态派生。
     *
     * @param deriveInfo 派生信息。
     * @return 登录状态。
     * @throws ServiceException 服务异常。
     */
    LoginState dynamicDerive(DynamicDeriveInfo deriveInfo) throws ServiceException;

    /**
     * 静态派生。
     *
     * @param deriveInfo 派生信息。
     * @return 登录状态。
     * @throws ServiceException 服务异常。
     */
    LoginState staticDerive(StaticDeriveInfo deriveInfo) throws ServiceException;
}
