package com.dwarfeng.acckeeper.impl.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonAccount.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonLoginState.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonLoginHistory.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProtectorInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProtectorSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProtectorVariable.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonLoginParamRecord.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonProtectDetailRecord.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
