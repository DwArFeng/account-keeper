package com.dwarfeng.acckeeper.impl.handler.lskgen;

import com.dwarfeng.acckeeper.sdk.handler.lskgen.AbstractLoginStateKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.stereotype.Component;

/**
 * Snowflake 登录状态主键生成器。
 *
 * <p>
 * 该实现是为了兼容旧的登录状态主键格式而保留的。在旧版本中，登录状态主键可能使用 Snowflake ID 格式。
 * 为了确保向后兼容性，本实现允许系统继续生成 Snowflake 格式的登录状态主键。
 *
 * <p>
 * 该生成器已被废弃。Snowflake ID 基于时间戳和机器 ID 生成，容易被推测，存在安全风险，请使用其它安全实现代替。
 *
 * @author DwArFeng
 * @see RandxLoginStateKeyGenerator
 * @see UuidLoginStateKeyGenerator
 * @since 2.0.0
 * @deprecated 该生成器已被废弃。Snowflake ID 基于时间戳和机器 ID 生成，容易被推测，存在安全风险，请使用其它安全实现代替。
 * 该实现仅为了兼容旧的登录状态主键格式而保留。
 */
@Deprecated
@Component
public class SnowflakeLoginStateKeyGenerator extends AbstractLoginStateKeyGenerator {

    public static final String SUPPORT_TYPE = "snowflake";

    private final GenerateService snowflakeGenerateService;

    public SnowflakeLoginStateKeyGenerator(GenerateService snowflakeGenerateService) {
        super(SUPPORT_TYPE);
        this.snowflakeGenerateService = snowflakeGenerateService;
    }

    @Override
    protected StringIdKey doGenerate() throws Exception {
        Long id = snowflakeGenerateService.generateLong();
        return new StringIdKey(String.valueOf(id));
    }

    @Override
    public String toString() {
        return "SnowflakeLoginStateKeyGenerator{" +
                "lskGeneratorType='" + loginStateKeyGenerateType + '\'' +
                '}';
    }
}
