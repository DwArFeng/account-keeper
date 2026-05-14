package com.dwarfeng.acckeeper.impl.handler.lskgen;

import com.dwarfeng.acckeeper.sdk.handler.lskgen.AbstractLoginStateKeyGenerator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Randx 登录状态主键生成器。
 *
 * <p>
 * 生成固定长度的 [a-zA-Z0-9] 范围内的随机字符串作为登录状态主键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RandxLoginStateKeyGenerator extends AbstractLoginStateKeyGenerator {

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final char[] SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            .toCharArray();

    public static final String SUPPORT_TYPE = "randx";

    @Value("${com.dwarfeng.acckeeper.lskgen.randx.length}")
    private int length;

    private final SecureRandom random = new SecureRandom();

    public RandxLoginStateKeyGenerator() {
        super(SUPPORT_TYPE);
    }

    @Override
    protected StringIdKey doGenerate() {
        if (length <= 0) {
            throw new IllegalArgumentException("lskgen.randx.length must be positive, but was: " + length);
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SYMBOLS[random.nextInt(SYMBOLS.length)]);
        }
        return new StringIdKey(sb.toString());
    }

    @Override
    public String toString() {
        return "RandxLoginStateKeyGenerator{" +
                "length=" + length +
                ", random=" + random +
                '}';
    }
}
