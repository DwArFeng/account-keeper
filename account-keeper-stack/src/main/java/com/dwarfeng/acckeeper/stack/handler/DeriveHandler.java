package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 派生处理器。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public interface DeriveHandler extends Handler {

    /**
     * 动态派生。
     *
     * <p>
     * 动态派生是指派生请求成功后，返回一个过期时间较短的登录状态。<br>
     * 在该登录状态过期之前，客户端需要及时调用 {@link LoginHandler#postpone(LongIdKey)} 方法来延长登录状态的过期时间。<br>
     * 如果登录状态过期，客户端需要重新登录。
     *
     * @param deriveInfo 派生信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     */
    LoginState dynamicDerive(DynamicDeriveInfo deriveInfo) throws HandlerException;

    /**
     * 静态派生。
     *
     * <p>
     * 静态派生是指派生时可以指定一个过期时间，派生状态将在过期时间到达之前一直有效。<br>
     * 指定的过期时间可以是一个较长的时间，用于实现长期派生。<br>
     * 在过期时间到达之前，客户端不需要做任何操作，派生状态都将保持有效。<br>
     * 如果派生状态过期，客户端需要重新派生。
     *
     * @param deriveInfo 派生信息。
     * @return 登录状态。
     * @throws HandlerException 处理器异常。
     */
    LoginState staticDerive(StaticDeriveInfo deriveInfo) throws HandlerException;
}
