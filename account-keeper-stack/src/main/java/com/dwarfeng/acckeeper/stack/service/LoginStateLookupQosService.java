package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录状态查询 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginStateLookupQosService extends Service {

    /**
     * 登录状态查看。
     *
     * <p>
     * 关于该方法的说明，请参阅 {@link LoginStateLookupInfo} 的文档注释。
     *
     * @param info 登录状态查看信息。
     * @return 登录状态查看结果。
     * @throws ServiceException 服务异常。
     * @see LoginStateLookupInfo
     */
    LoginStateLookupResult lookup(LoginStateLookupInfo info) throws ServiceException;
}
