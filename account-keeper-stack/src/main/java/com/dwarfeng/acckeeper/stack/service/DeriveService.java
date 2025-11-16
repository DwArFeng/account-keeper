package com.dwarfeng.acckeeper.stack.service;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface DeriveService extends Service {

    /**
     * 动态派生。
     *
     * <p>
     * 动态派生是指派生请求成功后，返回一个过期时间较短的登录状态。<br>
     * 在该登录状态过期之前，客户端需要及时调用 {@link AccessService#postpone(PostponeInfo)} 方法来延长登录状态的过期时间。<br>
     * 如果登录状态过期，客户端需要重新派生。
     *
     * @param info 派生信息。
     * @return 动态派生结果。
     * @throws ServiceException 服务异常。
     */
    DynamicDeriveResult dynamicDerive(DynamicDeriveInfo info) throws ServiceException;

    /**
     * 静态派生。
     *
     * <p>
     * 静态派生是指派生时可以指定一个过期时间，派生状态将在过期时间到达之前一直有效。<br>
     * 指定的过期时间可以是一个较长的时间，用于实现长期派生。<br>
     * 在过期时间到达之前，客户端不需要做任何操作，派生状态都将保持有效。<br>
     * 如果派生状态过期，客户端需要重新派生。
     *
     * @param info 派生信息。
     * @return 静态派生结果。
     * @throws ServiceException 服务异常。
     */
    StaticDeriveResult staticDerive(StaticDeriveInfo info) throws ServiceException;
}
