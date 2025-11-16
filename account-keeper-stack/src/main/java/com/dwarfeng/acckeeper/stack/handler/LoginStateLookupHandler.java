package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 登录状态查询处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginStateLookupHandler extends Handler {

    /**
     * 登录状态查看。
     *
     * <p>
     * 关于该方法的说明，请参阅 {@link LoginStateLookupInfo} 的文档注释。
     *
     * @param info 登录状态查看信息。
     * @return 登录状态查看结果。
     * @throws HandlerException 处理器异常。
     * @see LoginStateLookupInfo
     */
    LoginStateLookupResult lookup(LoginStateLookupInfo info) throws HandlerException;
}
