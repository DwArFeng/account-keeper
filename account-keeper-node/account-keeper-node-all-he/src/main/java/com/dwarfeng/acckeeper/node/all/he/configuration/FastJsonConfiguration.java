package com.dwarfeng.acckeeper.node.all.he.configuration;

import com.alibaba.fastjson2.JSONFactory;
import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonAccount.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonLoginState.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonLoginHistory.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonProtectorInfo.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonProtectorSupport.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonProtectorVariable.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonLoginParamRecord.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonProtectDetailRecord.class.getCanonicalName());
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept(FastJsonDeriveHistory.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
