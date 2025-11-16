package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.service.LoginStateKeyGenerateQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录状态主键生成命令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class LoginStateKeyGenerateCommand extends CliCommand {

    private static final String COMMAND_OPTION_TEST = "t";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_TEST,
    };

    private static final String COMMAND_OPTION_SIZE = "s";

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "lskgen";
    private static final String DESCRIPTION = "登录状态主键生成";

    private static final String CMD_LINE_SYNTAX_TEST = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_TEST) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_SIZE) + " size]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_TEST,
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final LoginStateKeyGenerateQosService loginStateKeyGenerateQosService;

    public LoginStateKeyGenerateCommand(LoginStateKeyGenerateQosService loginStateKeyGenerateQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginStateKeyGenerateQosService = loginStateKeyGenerateQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_TEST).desc("测试生成").build());
        list.add(Option.builder(COMMAND_OPTION_SIZE).desc("生成数量").hasArg().type(Number.class).build());
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(super.cmdLineSyntax);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_TEST:
                    handleTest(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleTest(Context context, CommandLine cmd) throws Exception {
        int size;

        // 如果有 -s 选项，则从选项中获取 size，转化为。
        if (cmd.hasOption(COMMAND_OPTION_SIZE)) {
            size = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_SIZE)).intValue();
        } else {
            size = interactiveParseSize(context);
        }

        // 调用 QoS 服务获取结果。
        List<StringIdKey> loginStateKeys = loginStateKeyGenerateQosService.generate(size);

        // 输出结果。
        int digits = digits(loginStateKeys.size() - 1);
        for (int i = 0; i < loginStateKeys.size(); i++) {
            context.sendMessage(String.format("%-" + digits + "d: %s", i, loginStateKeys.get(i).toString()));
        }
    }

    private int interactiveParseSize(Context context) throws Exception {
        int size;
        context.sendMessage("请输入需要生成的登录状态主键的数量: ");
        while (true) {
            try {
                size = Integer.parseInt(context.receiveMessage());
            } catch (NumberFormatException e) {
                context.sendMessage("输入的数量格式不正确，请输入一个整数: ");
                continue;
            }
            if (size <= 0) {
                context.sendMessage("输入的数量必须是正数，请重新输入: ");
                continue;
            }
            break;
        }
        return size;
    }

    private int digits(int target) {
        int digits = 0;
        do {
            digits++;
        } while ((target /= 10) > 0);
        return digits;
    }
}
