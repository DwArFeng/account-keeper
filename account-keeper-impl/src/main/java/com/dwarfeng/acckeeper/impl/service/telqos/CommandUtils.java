package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
class CommandUtils {

    /**
     * 根据I, N, A 选项命令行获取对用的所有登录状态。
     *
     * @param service 登录状态缓存。
     * @param cmd     命令行。
     * @return 命令行获取对用的所有登录状态组成的列表。
     * @throws Exception 获取登录状态期间发生的任何异常。
     */
    public static List<LoginState> getLoginStateFromInaCommand(LoginStateMaintainService service, CommandLine cmd)
            throws Exception {
        if (cmd.hasOption("i")) {
            LongIdKey key = new LongIdKey(((Number) cmd.getParsedOptionValue("i")).longValue());
            if (service.exists(key)) {
                return Collections.singletonList(service.get(key));
            } else {
                return Collections.emptyList();
            }
        } else if (cmd.hasOption("n")) {
            String accountName = cmd.getOptionValue("n");
            return service.lookupAsList().stream()
                    .filter(state -> Objects.equals(state.getAccountKey().getStringId(), accountName))
                    .collect(Collectors.toList());
        } else if (cmd.hasOption("a")) {
            return service.lookupAsList();
        } else {
            throw new IllegalArgumentException("命令行不合法");
        }
    }

    /**
     * 分析 I, N, A 选项命令行。
     *
     * @param cmd 指定的命令行。
     * @return 指定的分析结果。
     */
    public static Pair<String, Integer> analyseInaCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("i")) {
            i++;
            subCmd = "i";
        }
        if (cmd.hasOption("n")) {
            i++;
            subCmd = "n";
        }
        if (cmd.hasOption("a")) {
            i++;
            subCmd = "a";
        }
        return Pair.of(subCmd, i);
    }

    /**
     * 构建 I, N, A 选项。
     *
     * @param list 指定的选项列表。
     */
    public static void buildInaOptions(List<Option> list) {
        list.add(Option.builder("i").optionalArg(true).type(Number.class).hasArg(true).desc("登录状态id").build());
        list.add(Option.builder("n").optionalArg(true).type(String.class).hasArg(true).desc("登录账号名称").build());
        list.add(Option.builder("a").optionalArg(true).hasArg(false).desc("全部已登录实例").build());
    }

    private static final int FORMAT_LENGTH_ID = 19;
    private static final int FORMAT_LENGTH_DATE = 23;
    private static final String FORMAT_LABEL_INDEX = "index";
    private static final String FORMAT_LABEL_ID = "id";
    private static final String FORMAT_LABEL_ACCOUNT = "account";
    private static final String FORMAT_LABEL_EXPIRE_DATE = "expire-date";
    private static final String FORMAT_LABEL_SERIAL_VERSION = "serial-version";
    private static final DateFormat FORMAT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static int renderTitle(StringBuilder stringBuilder, int countLength, int maxAccountLength) {
        countLength = Math.max(FORMAT_LABEL_INDEX.length(), countLength);
        maxAccountLength = Math.max(FORMAT_LABEL_ACCOUNT.length(), maxAccountLength);
        String formatString = "%-" + countLength + "s %-" + FORMAT_LENGTH_ID + "s %-" + maxAccountLength +
                "s %-" + FORMAT_LENGTH_DATE + "s %-" + FORMAT_LENGTH_ID + "s";
        String title = String.format(formatString, FORMAT_LABEL_INDEX, FORMAT_LABEL_ID, FORMAT_LABEL_ACCOUNT,
                FORMAT_LABEL_EXPIRE_DATE, FORMAT_LABEL_SERIAL_VERSION);
        stringBuilder.append(title);
        return title.length();
    }

    public static void renderSeparator(StringBuilder stringBuilder, int lengthOfLine) {
        for (int i = 0; i < lengthOfLine; i++) {
            stringBuilder.append('-');
        }
    }

    public static void renderSingleLoginState(
            StringBuilder stringBuilder, LoginState loginState, int index, int countLength, int maxAccountLength) {
        countLength = Math.max(FORMAT_LABEL_INDEX.length(), countLength);
        maxAccountLength = Math.max(FORMAT_LABEL_ACCOUNT.length(), maxAccountLength);
        String formatString = "%-" + countLength + "d %-" + FORMAT_LENGTH_ID + "s %-" + maxAccountLength +
                "s %-" + FORMAT_LENGTH_DATE + "s %-" + FORMAT_LENGTH_ID + "d";
        stringBuilder.append(String.format(formatString, index, loginState.getKey().getLongId(),
                loginState.getAccountKey().getStringId(), FORMAT_DATE_FORMAT.format(loginState.getExpireDate()),
                loginState.getSerialVersion()));
    }

    public static void renderBrief(StringBuilder stringBuilder, int currentPage, int pageSize, int totalSize) {
        stringBuilder.append(String.format("%d / %d", Math.min((currentPage + 1) * pageSize, totalSize), totalSize));
    }

    private CommandUtils() {
        throw new IllegalStateException("禁止实例化。");
    }
}
