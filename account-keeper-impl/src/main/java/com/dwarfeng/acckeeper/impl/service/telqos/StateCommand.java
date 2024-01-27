package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class StateCommand extends CliCommand {

    private static final String COMMAND_OPTION_INSPECT_FOR_ID = "i";
    private static final String COMMAND_OPTION_INSPECT_FOR_ACCOUNT = "n";
    private static final String COMMAND_OPTION_INSPECT_ALL = "a";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_INSPECT_FOR_ID,
            COMMAND_OPTION_INSPECT_FOR_ACCOUNT,
            COMMAND_OPTION_INSPECT_ALL
    };

    private static final String COMMAND_OPTION_PAGE = "p";

    private static final String IDENTITY = "state";
    private static final String DESCRIPTION = "列出用户的登录状态";

    private static final String CMD_LINE_SYNTAX_INSPECT_FOR_ID = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_INSPECT_FOR_ID) + " login-state-id [-p page-size]";
    private static final String CMD_LINE_SYNTAX_INSPECT_FOR_NAME = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_INSPECT_FOR_ACCOUNT) + " account-id [-p page-size]";
    private static final String CMD_LINE_SYNTAX_INSPECT_ALL = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_INSPECT_ALL) + " [-p page-size]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_INSPECT_FOR_ID,
            CMD_LINE_SYNTAX_INSPECT_FOR_NAME,
            CMD_LINE_SYNTAX_INSPECT_ALL
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final LoginQosService loginQosService;

    public StateCommand(LoginQosService loginQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginQosService = loginQosService;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_INSPECT_FOR_ID).optionalArg(true).type(Number.class).hasArg(true)
                .desc("登录状态 ID").build());
        list.add(Option.builder(COMMAND_OPTION_INSPECT_FOR_ACCOUNT).optionalArg(true).type(String.class).hasArg(true)
                .desc("登录账号 ID").build());
        list.add(Option.builder(COMMAND_OPTION_INSPECT_ALL).optionalArg(true).hasArg(false).desc("全部已登录实例")
                .build());
        list.add(Option.builder(COMMAND_OPTION_PAGE).optionalArg(true).type(Number.class).hasArg(true)
                .desc("单页显示数量").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_INSPECT_FOR_ID:
                    inspectForId(context, cmd);
                    break;
                case COMMAND_OPTION_INSPECT_FOR_ACCOUNT:
                    inspectForName(context, cmd);
                    break;
                case COMMAND_OPTION_INSPECT_ALL:
                    inspectAll(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void inspectForId(Context context, CommandLine cmd) throws Exception {
        LongIdKey key = new LongIdKey(((Number) cmd.getParsedOptionValue(COMMAND_OPTION_INSPECT_FOR_ID)).longValue());
        List<LoginState> loginStates = loginQosService.inspectLoginStateByKey(key);
        processLoginStateList(context, cmd, loginStates);
    }

    private void inspectForName(Context context, CommandLine cmd) throws Exception {
        StringIdKey accountKey = new StringIdKey((String) cmd.getParsedOptionValue(COMMAND_OPTION_INSPECT_FOR_ACCOUNT));
        List<LoginState> loginStates = loginQosService.inspectLoginStateByAccount(accountKey);
        processLoginStateList(context, cmd, loginStates);
    }

    private void inspectAll(Context context, CommandLine cmd) throws Exception {
        List<LoginState> loginStates = loginQosService.inspectAllLoginState();
        processLoginStateList(context, cmd, loginStates);
    }

    private void processLoginStateList(Context context, CommandLine cmd, List<LoginState> loginStates)
            throws Exception {
        if (!cmd.hasOption(COMMAND_OPTION_PAGE)) {
            printLoginStates(context, loginStates, 0, loginStates.size());
            return;
        }

        int pageSize;
        try {
            pageSize = Math.max(((Number) cmd.getParsedOptionValue("p")).intValue(), 1);
        } catch (Exception e) {
            context.sendMessage(CommandUtil.concatOptionPrefix(COMMAND_OPTION_PAGE) + " 选项必须接数字");
            return;
        }

        int currentPage = 0;
        boolean printFlag = true;
        do {
            if (printFlag) {
                printLoginStates(context, loginStates, currentPage, pageSize);
                context.sendMessage("输入 q 退出，输入 n 进入下一页，输入 p 进入上一页，输入数字进入指定页");
            }
            String command = context.receiveMessage();
            if (StringUtils.equalsIgnoreCase(command, "n")) {
                currentPage += 1;
                printFlag = true;
            } else if (StringUtils.equalsIgnoreCase(command, "p")) {
                currentPage = Math.max(0, currentPage - 1);
                printFlag = true;
            } else if (StringUtils.equalsIgnoreCase(command, "q")) {
                break;
            } else if (StringUtils.isNumeric(command)) {
                currentPage = Math.max(0, Integer.parseInt(command) - 1);
                printFlag = true;
            } else {
                context.sendMessage("输入内容非法");
                printFlag = false;
            }
        } while (true);
    }

    private void printLoginStates(
            Context context, List<LoginState> loginStates, int currentPage, int pageSize
    ) throws Exception {
        // 计算部分字段的最大长度。
        int countLength = 0;
        int sizeTest = loginStates.size();
        while ((sizeTest /= 10) > 0) {
            countLength++;
        }
        int maxAccountLength = loginStates.stream()
                .map(loginState -> loginState.getAccountKey().getStringId().length())
                .max(Integer::compareTo).orElse(0);
        // 渲染元素。
        StringBuilder stringBuilder = new StringBuilder();
        int lengthOfLine = renderTitle(stringBuilder, countLength, maxAccountLength);
        stringBuilder.append(System.lineSeparator());
        renderSeparator(stringBuilder, lengthOfLine);
        stringBuilder.append(System.lineSeparator());
        int startIndex = currentPage * pageSize;
        for (int i = 0; i < pageSize; i++) {
            int index = startIndex + i;
            if (index < loginStates.size()) {
                renderSingleLoginState(stringBuilder, loginStates.get(index), index + 1,
                        countLength, maxAccountLength);
                stringBuilder.append(System.lineSeparator());
            }
        }
        renderSeparator(stringBuilder, lengthOfLine);
        stringBuilder.append(System.lineSeparator());
        renderBrief(stringBuilder, currentPage, pageSize, loginStates.size());
        stringBuilder.append(System.lineSeparator());
        context.sendMessage(stringBuilder.toString());
    }

    private static final int FORMAT_LENGTH_ID = 19;
    private static final int FORMAT_LENGTH_DATE = 23;
    private static final String FORMAT_LABEL_INDEX = "index";
    private static final String FORMAT_LABEL_ID = "id";
    private static final String FORMAT_LABEL_ACCOUNT = "account";
    private static final String FORMAT_LABEL_EXPIRE_DATE = "expire-date";
    private static final String FORMAT_LABEL_SERIAL_VERSION = "serial-version";
    private static final DateFormat FORMAT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private int renderTitle(StringBuilder stringBuilder, int countLength, int maxAccountLength) {
        countLength = Math.max(FORMAT_LABEL_INDEX.length(), countLength);
        maxAccountLength = Math.max(FORMAT_LABEL_ACCOUNT.length(), maxAccountLength);
        String formatString = "%-" + countLength + "s %-" + FORMAT_LENGTH_ID + "s %-" + maxAccountLength +
                "s %-" + FORMAT_LENGTH_DATE + "s %-" + FORMAT_LENGTH_ID + "s";
        String title = String.format(formatString, FORMAT_LABEL_INDEX, FORMAT_LABEL_ID, FORMAT_LABEL_ACCOUNT,
                FORMAT_LABEL_EXPIRE_DATE, FORMAT_LABEL_SERIAL_VERSION);
        stringBuilder.append(title);
        return title.length();
    }

    private void renderSeparator(StringBuilder stringBuilder, int lengthOfLine) {
        for (int i = 0; i < lengthOfLine; i++) {
            stringBuilder.append('-');
        }
    }

    private void renderSingleLoginState(
            StringBuilder stringBuilder, LoginState loginState, int index, int countLength, int maxAccountLength
    ) {
        countLength = Math.max(FORMAT_LABEL_INDEX.length(), countLength);
        maxAccountLength = Math.max(FORMAT_LABEL_ACCOUNT.length(), maxAccountLength);
        String formatString = "%-" + countLength + "d %-" + FORMAT_LENGTH_ID + "s %-" + maxAccountLength +
                "s %-" + FORMAT_LENGTH_DATE + "s %-" + FORMAT_LENGTH_ID + "d";
        stringBuilder.append(String.format(formatString, index, loginState.getKey().getLongId(),
                loginState.getAccountKey().getStringId(), FORMAT_DATE_FORMAT.format(loginState.getExpireDate()),
                loginState.getSerialVersion()));
    }

    private void renderBrief(StringBuilder stringBuilder, int currentPage, int pageSize, int totalSize) {
        stringBuilder.append(String.format("%d / %d", Math.min((currentPage + 1) * pageSize, totalSize), totalSize));
    }
}
