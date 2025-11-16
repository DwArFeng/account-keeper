package com.dwarfeng.acckeeper.impl.handler.lskgen;

import com.dwarfeng.acckeeper.sdk.handler.lskgen.AbstractLoginStateKeyGenerator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

/**
 * UUID 登录状态主键生成器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class UuidLoginStateKeyGenerator extends AbstractLoginStateKeyGenerator {

    public static final String SUPPORT_TYPE = "uuid";

    public UuidLoginStateKeyGenerator() {
        super(SUPPORT_TYPE);
    }

    @Override
    protected StringIdKey doGenerate() {
        return new StringIdKey(java.util.UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return "UuidLoginStateKeyGenerator{" +
                "lskGeneratorType='" + loginStateKeyGenerateType + '\'' +
                '}';
    }
}
