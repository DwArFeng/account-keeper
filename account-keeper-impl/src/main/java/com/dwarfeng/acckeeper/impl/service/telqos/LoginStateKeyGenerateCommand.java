package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.service.LoginStateKeyGenerateQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
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

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "lskgen";

    // region 指令选项

    private static final String COMMAND_OPTION_TEST = "t";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_TEST,
    };

    private static final String COMMAND_OPTION_SIZE = "s";

    // endregion

    private final LoginStateKeyGenerateQosService loginStateKeyGenerateQosService;

    public LoginStateKeyGenerateCommand(LoginStateKeyGenerateQosService loginStateKeyGenerateQosService) {
        super(IDENTITY);
        this.loginStateKeyGenerateQosService = loginStateKeyGenerateQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "登录状态主键生成";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_TEST) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_SIZE) + " size]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_TEST).desc("测试生成").build());
        list.add(Option.builder(COMMAND_OPTION_SIZE).desc("生成数量").hasArg().type(Number.class).build());
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_TEST:
                handleTest(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleTest(CommandExecutor.Context context, CommandLine cmd) throws Exception {
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

    private int interactiveParseSize(CommandExecutor.Context context) throws Exception {
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
