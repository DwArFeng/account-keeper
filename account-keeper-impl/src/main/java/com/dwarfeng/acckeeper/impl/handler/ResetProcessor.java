package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.handler.ProtectLocalCacheHandler;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final ProtectLocalCacheHandler protectLocalCacheHandler;

    private final PushHandler pushHandler;

    ResetProcessor(
            ProtectLocalCacheHandler protectLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.protectLocalCacheHandler = protectLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetProtect() throws HandlerException {
        protectLocalCacheHandler.clear();

        try {
            pushHandler.protectReset();
        } catch (Exception e) {
            LOGGER.warn("推送保护被重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
