package com.dwarfeng.acckeeper.sdk.bean.key.formatter;

import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * ProtectorVariableKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class ProtectorVariableStringKeyFormatter implements StringKeyFormatter<ProtectorVariableKey> {

    private String prefix;

    public ProtectorVariableStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(ProtectorVariableKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getProtectorInfoId() + "_" + key.getVariableId();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "ProtectorVariableStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
