package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.cache.LoginStateCache;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StateCommand extends CliCommand {

    private static final String IDENTITY = "state";
    private static final String DESCRIPTION = "列出用户的登录状态";
    private static final String CMD_LINE_SYNTAX_I = "state -i id";
    private static final String CMD_LINE_SYNTAX_N = "state -n name";
    private static final String CMD_LINE_SYNTAX_A = "state -a [-p page-size]";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_I + System.lineSeparator() +
            CMD_LINE_SYNTAX_N + System.lineSeparator() + CMD_LINE_SYNTAX_A;

    private final LoginStateCache loginStateCache;

    public StateCommand(LoginStateCache loginStateCache) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginStateCache = loginStateCache;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        CommandUtils.buildInaOptions(list);
        list.add(Option.builder("p").optionalArg(true).type(Number.class).hasArg(true).desc("单页显示数量").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtils.analyseInaCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -i -n -a");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            int pageSize = -1;
            if (cmd.hasOption("p")) {
                try {
                    pageSize = Math.max(((Number) cmd.getParsedOptionValue("p")).intValue(), 1);
                } catch (Exception e) {
                    context.sendMessage("-p 选项必须接数字");
                    return;
                }
                if (pageSize < 1) {
                    context.sendMessage("-p 选项后的数组必须大于 0");
                    return;
                }
            }

            List<LoginState> loginStates = CommandUtils.getLoginStateFromInaCommand(loginStateCache, cmd);
            if (loginStates.isEmpty()) {
                context.sendMessage("not found.");
                return;
            }
            if (cmd.hasOption("a") && cmd.hasOption("p")) {
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
            } else {
                printLoginStates(context, loginStates, 0, loginStates.size());
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void printLoginStates(
            Context context, List<LoginState> loginStates, int currentPage, int pageSize) throws Exception {
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
        int lengthOfLine = CommandUtils.renderTitle(stringBuilder, countLength, maxAccountLength);
        stringBuilder.append(System.lineSeparator());
        CommandUtils.renderSeparator(stringBuilder, lengthOfLine);
        stringBuilder.append(System.lineSeparator());
        int startIndex = currentPage * pageSize;
        for (int i = 0; i < pageSize; i++) {
            int index = startIndex + i;
            if (index < loginStates.size()) {
                CommandUtils.renderSingleLoginState(stringBuilder, loginStates.get(index), index + 1,
                        countLength, maxAccountLength);
                stringBuilder.append(System.lineSeparator());
            }
        }
        CommandUtils.renderSeparator(stringBuilder, lengthOfLine);
        stringBuilder.append(System.lineSeparator());
        CommandUtils.renderBrief(stringBuilder, currentPage, pageSize, loginStates.size());
        stringBuilder.append(System.lineSeparator());
        context.sendMessage(stringBuilder.toString());
    }
}
