package com.dwarfeng.acckeeper.sdk.bean.key.formatter;

import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * RecordKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class RecordStringKeyFormatter implements StringKeyFormatter<RecordKey> {

    private String prefix;

    public RecordStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(RecordKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getRecordId() + "_" + key.getRecordId();
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
        return "RecordStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
