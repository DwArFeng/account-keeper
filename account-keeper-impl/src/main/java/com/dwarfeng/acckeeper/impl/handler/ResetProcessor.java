package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.handler.ProtectLocalCacheHandler;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.subgrade.basic.stack.exception.HandlerException;
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
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final ProtectLocalCacheHandler protectLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
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
            LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_PROTECT_RESET_MESSAGE_PUSH_FAILED), e);
        }
    }
}
