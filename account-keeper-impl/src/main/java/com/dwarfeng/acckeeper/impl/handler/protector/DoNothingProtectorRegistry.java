package com.dwarfeng.acckeeper.impl.handler.protector;

import com.dwarfeng.acckeeper.sdk.handler.protector.AbstractProtectorRegistry;
import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.acckeeper.stack.exception.ProtectorMakeException;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 不做任何事情的保护器注册。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class DoNothingProtectorRegistry extends AbstractProtectorRegistry {

    public static final String PROTECTOR_TYPE = "do_nothing_protector";

    private final ApplicationContext ctx;

    public DoNothingProtectorRegistry(ApplicationContext ctx) {
        super(PROTECTOR_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "不做任何事情的保护器";
    }

    @Override
    public String provideDescription() {
        return "不做任何事情的保护器";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Protector makeProtector(String type, String param) throws ProtectorException {
        try {
            // 通过 ctx 生成保护器。
            return ctx.getBean(DoNothingProtector.class);
        } catch (Exception e) {
            throw new ProtectorMakeException(e, type, param);
        }
    }

    @Override
    public String toString() {
        return "DoNothingProtectorRegistry{" +
                "protectorType='" + protectorType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DoNothingProtector implements Protector {

        @Override
        public Response execProtect(Context context) {
            return new Response(
                    true, StringUtils.EMPTY, Constants.PROTECTOR_MESSAGE_LEVEL_INFO, Collections.emptyMap()
            );
        }

        @Override
        public void resetProtect(Context context) {
        }

        @Override
        public String toString() {
            return "DoNothingProtector{}";
        }
    }
}
