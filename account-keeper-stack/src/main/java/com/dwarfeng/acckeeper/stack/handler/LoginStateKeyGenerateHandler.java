package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 登录状态主键生成处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginStateKeyGenerateHandler extends Handler {

    /**
     * 生成登录状态主键。
     *
     * <p>
     * 该方法需要尽量保证生成的登录状态主键的唯一性，在可能的情况下，生成的登录状态主键不应当重复。
     *
     * <p>
     * 在极端情况下，生成的登录状态主键允许以低概率重复，如果发生重复，系统会继续调用该方法重新生成，
     * 直到生成不重复的登录状态主键为止。
     *
     * @return 生成的登录状态主键。
     * @throws HandlerException 处理器异常。
     */
    StringIdKey generate() throws HandlerException;
}
