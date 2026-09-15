package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.service.LoginStateKeyGenerateQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
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
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_DESCRIPTION);
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
        list.add(
                Option.builder(COMMAND_OPTION_TEST)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_OPTION_TEST)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_SIZE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_OPTION_SIZE))
                        .hasArg().type(Number.class).get()
        );
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
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
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
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_PROMPT)
        );
        while (true) {
            try {
                size = Integer.parseInt(context.receiveMessage());
            } catch (NumberFormatException e) {
                context.sendMessage(
                        ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_FORMAT_ERROR)
                );
                continue;
            }
            if (size <= 0) {
                context.sendMessage(
                        ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_KEY_GENERATE_RESULT_SIZE_NOT_POSITIVE)
                );
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
