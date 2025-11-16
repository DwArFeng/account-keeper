package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 派生处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface DeriveHandler extends Handler {

    /**
     * 动态派生。
     *
     * <p>
     * 请求成功后返回过期时间较短的派生登录状态概要。
     *
     * @param info 动态派生信息。
     * @return 动态派生结果。
     * @throws HandlerException 处理器异常。
     */
    DynamicDeriveResult dynamicDerive(DynamicDeriveInfo info) throws HandlerException;

    /**
     * 静态派生。
     *
     * <p>
     * 请求成功后返回在指定过期时间内有效的派生登录状态概要。
     *
     * @param info 静态派生信息。
     * @return 静态派生结果。
     * @throws HandlerException 处理器异常。
     */
    StaticDeriveResult staticDerive(StaticDeriveInfo info) throws HandlerException;
}
