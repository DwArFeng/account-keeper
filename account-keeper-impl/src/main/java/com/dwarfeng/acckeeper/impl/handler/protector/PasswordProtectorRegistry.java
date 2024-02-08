package com.dwarfeng.acckeeper.impl.handler.protector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.util.Constants;
import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.acckeeper.stack.exception.ProtectorExecutionException;
import com.dwarfeng.acckeeper.stack.exception.ProtectorMakeException;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * 密码保护器注册。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class PasswordProtectorRegistry extends AbstractProtectorRegistry {

    public static final String PROTECTOR_TYPE = "password_incorrect_protector";

    /**
     * 将指定的配置转换为参数。
     *
     * @param config 指定的配置。
     * @return 指配置转换成的参数。
     */
    public static String toParam(Config config) {
        return JSON.toJSONString(config, false);
    }

    /**
     * 解析参数并获取配置。
     *
     * @param param 指定的参数。
     * @return 解析参数获取到的配置。
     */
    public static Config parseParam(String param) {
        return JSON.parseObject(param, Config.class);
    }

    private final ApplicationContext ctx;

    public PasswordProtectorRegistry(ApplicationContext ctx) {
        super(PROTECTOR_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "密码保护器";
    }

    @Override
    public String provideDescription() {
        return "如果用户在一段时间内连续输入错误密码超过一定的次数，则禁止用户登录一段时间";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(86400000L, 3, 6, 6);
        return JSON.toJSONString(config, false);
    }

    @Override
    public Protector makeProtector(String type, String param) throws ProtectorException {
        try {
            // 通过 param 生成发送器的参数。
            Config config = parseParam(param);

            // 通过 ctx 生成保护器。
            return ctx.getBean(PasswordProtector.class, config);
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
    public static class PasswordProtector implements Protector {

        private static final String VARIABLE_KEY_INIT = "init";
        private static final String VARIABLE_KEY_PROHIBIT_UNTIL_TIMESTAMP = "prohibit_until_timestamp";
        private static final String VARIABLE_KEY_INCORRECT_COUNT = "incorrect_count";

        private final Config config;

        public PasswordProtector(Config config) {
            this.config = config;
        }

        @Override
        public Response execProtect(Context context) throws ProtectorException {
            try {
                // 如果没有初始化，先行初始化。
                if (!context.existsVariable(VARIABLE_KEY_INIT)) {
                    initVariable(context);
                }

                // 获取当前系统时间。
                long currentTimestamp = System.currentTimeMillis();

                // 判断当前时间下账号是否处于禁止状态。
                long prohibitUntilTimestamp = Long.parseLong(
                        context.getVariable(VARIABLE_KEY_PROHIBIT_UNTIL_TIMESTAMP).getValue()
                );
                if (currentTimestamp <= prohibitUntilTimestamp) {
                    String prohibitDateString = formatTimestamp(prohibitUntilTimestamp);
                    return new Response(
                            false,
                            "账户在连续输错了过多次密码, 直到 " + prohibitDateString + " 之前禁止登录",
                            Constants.PROTECTOR_MESSAGE_LEVEL_INFO,
                            Collections.emptyMap()
                    );
                }

                // 如果本次密码输入正确，则重置密码计数，随后放行。
                if (context.passwordCorrect()) {
                    String incorrectCountString = 0 + "";
                    context.setVariable(
                            VARIABLE_KEY_PROHIBIT_UNTIL_TIMESTAMP,
                            new Variable(incorrectCountString, "连续输入错误密码计数")
                    );
                    return new Response(
                            true, "放行", Constants.PROTECTOR_MESSAGE_LEVEL_INFO, Collections.emptyMap()
                    );
                }

                // 获取连续输入错误密码次数，加一后回写。
                int incorrectCount = Integer.parseInt(context.getVariable(VARIABLE_KEY_INCORRECT_COUNT).getValue());
                incorrectCount++;
                context.setVariable(
                        VARIABLE_KEY_INCORRECT_COUNT, new Variable(incorrectCount + "", "连续输入错误密码计数")
                );

                // 判断信息的类型。
                int alarmLevel = parseAlarmLevel(incorrectCount);

                // 如果 count 大于禁止阈值，则禁止。
                int prohibitThreshold = config.getProhibitThreshold();
                if (incorrectCount >= prohibitThreshold) {
                    prohibitUntilTimestamp = currentTimestamp + config.getProhibitPeriod();
                    String prohibitUntilTimestampString = prohibitUntilTimestamp + "";
                    String prohibitDateString = formatTimestamp(prohibitUntilTimestamp);
                    context.setVariable(
                            VARIABLE_KEY_PROHIBIT_UNTIL_TIMESTAMP,
                            new Variable(prohibitUntilTimestampString, "禁止登录截止时间戳")
                    );
                    return new Response(
                            false,
                            "账户在最近一段时间内输错了过多次密码, 直到 " + prohibitDateString + " 之前禁止登录",
                            alarmLevel,
                            Collections.emptyMap()
                    );
                }
                // 否则只禁止登录。
                else {
                    return new Response(
                            false,
                            "账户输入了错误密码, 还有 " + (prohibitThreshold - incorrectCount) + " 次尝试机会",
                            alarmLevel,
                            Collections.emptyMap()
                    );
                }
            } catch (Exception e) {
                throw new ProtectorExecutionException(e);
            }
        }

        private String formatTimestamp(long prohibitUntilTimestamp) {
            return new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date(prohibitUntilTimestamp));
        }

        private int parseAlarmLevel(int incorrectCount) {
            int alarmLevel;
            int warnThreshold = config.getWarnThreshold();
            int dangerThreshold = config.getDangerThreshold();
            if (incorrectCount >= dangerThreshold) {
                alarmLevel = Constants.PROTECTOR_MESSAGE_LEVEL_DANGER;
            } else if (incorrectCount >= warnThreshold) {
                alarmLevel = Constants.PROTECTOR_MESSAGE_LEVEL_WARN;
            } else {
                alarmLevel = Constants.PROTECTOR_MESSAGE_LEVEL_INFO;
            }
            return alarmLevel;
        }

        @Override
        public void resetProtect(Context context) throws ProtectorException {
            try {
                initVariable(context);
            } catch (Exception e) {
                throw new ProtectorExecutionException(e);
            }
        }

        private void initVariable(Context context) throws ProtectorException {
            context.setVariable(VARIABLE_KEY_INIT, new Variable(StringUtils.EMPTY, "初始化标记"));
            String currentTimestampString = System.currentTimeMillis() + "";
            context.setVariable(
                    VARIABLE_KEY_PROHIBIT_UNTIL_TIMESTAMP, new Variable(currentTimestampString, "禁止登录截止时间戳")
            );
            String incorrectCountString = 0 + "";
            context.setVariable(
                    VARIABLE_KEY_INCORRECT_COUNT, new Variable(incorrectCountString, "连续输入错误密码计数")
            );
        }

        @Override
        public String toString() {
            return "PasswordProtector{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 1623975931260956258L;

        @JSONField(name = "prohibit_period", ordinal = 2)
        private long prohibitPeriod;

        @JSONField(name = "warn_threshold", ordinal = 3)
        private int warnThreshold;

        @JSONField(name = "danger_threshold", ordinal = 4)
        private int dangerThreshold;

        @JSONField(name = "prohibit_threshold", ordinal = 5)
        private int prohibitThreshold;

        public Config() {
        }

        public Config(
                long prohibitPeriod, int warnThreshold, int dangerThreshold, int prohibitThreshold
        ) {
            this.prohibitPeriod = prohibitPeriod;
            this.warnThreshold = warnThreshold;
            this.dangerThreshold = dangerThreshold;
            this.prohibitThreshold = prohibitThreshold;
        }

        public long getProhibitPeriod() {
            return prohibitPeriod;
        }

        public void setProhibitPeriod(long prohibitPeriod) {
            this.prohibitPeriod = prohibitPeriod;
        }

        public int getWarnThreshold() {
            return warnThreshold;
        }

        public void setWarnThreshold(int warnThreshold) {
            this.warnThreshold = warnThreshold;
        }

        public int getDangerThreshold() {
            return dangerThreshold;
        }

        public void setDangerThreshold(int dangerThreshold) {
            this.dangerThreshold = dangerThreshold;
        }

        public int getProhibitThreshold() {
            return prohibitThreshold;
        }

        public void setProhibitThreshold(int prohibitThreshold) {
            this.prohibitThreshold = prohibitThreshold;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "prohibitPeriod=" + prohibitPeriod +
                    ", warnThreshold=" + warnThreshold +
                    ", dangerThreshold=" + dangerThreshold +
                    ", prohibitThreshold=" + prohibitThreshold +
                    '}';
        }
    }
}
