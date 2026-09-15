package com.dwarfeng.acckeeper.sdk.internal.i18n;

import com.dwarfeng.acckeeper.base.sdk.i18n.Messages;
import com.dwarfeng.acckeeper.base.stack.i18n.MessageCatalog;

import java.util.Locale;

/**
 * SDK 模块私有消息入口。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class SdkMessages {

    public static String message(SdkMessageKey key, Object... args) {
        return Messages.resolve(key.catalog().messageCatalog(), key.key(), args);
    }

    public static String message(Locale locale, SdkMessageKey key, Object... args) {
        return Messages.resolve(key.catalog().messageCatalog(), key.key(), locale, args);
    }

    private SdkMessages() {
        throw new AssertionError("No instances");
    }

    enum Catalog {

        SDK("com.dwarfeng.acckeeper.sdk.i18n.messages");

        private final MessageCatalog messageCatalog;

        Catalog(String baseName) {
            this.messageCatalog = MessageCatalog.of(SdkMessages.class, baseName);
        }

        MessageCatalog messageCatalog() {
            return messageCatalog;
        }
    }
}
