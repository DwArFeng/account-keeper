package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface DeriveQosService extends Service {

    /**
     * 动态派生。
     *
     * @param info 派生信息。
     * @return 动态派生结果。
     * @throws ServiceException 服务异常。
     */
    DynamicDeriveResult dynamicDerive(DynamicDeriveInfo info) throws ServiceException;

    /**
     * 静态派生。
     *
     * @param info 派生信息。
     * @return 静态派生结果。
     * @throws ServiceException 服务异常。
     */
    StaticDeriveResult staticDerive(StaticDeriveInfo info) throws ServiceException;
}
