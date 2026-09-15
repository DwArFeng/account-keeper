package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.service.SupportQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class SupportCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "support";

    // region 指令选项

    private static final String COMMAND_OPTION_RESET_PROTECTOR = "reset-protector";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESET_PROTECTOR,
    };

    // endregion

    private final SupportQosService supportQosService;

    public SupportCommand(SupportQosService supportQosService) {
        super(IDENTITY);
        this.supportQosService = supportQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_SUPPORT_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_PROTECTOR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_RESET_PROTECTOR)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_SUPPORT_OPTION_RESET_PROTECTOR)).get()
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
            case COMMAND_OPTION_RESET_PROTECTOR:
                supportQosService.resetProtector();
                context.sendMessage(
                        ImplMessages.message(ImplMessageKey.TELQOS_SUPPORT_RESULT_RESET_PROTECTOR_SUCCEEDED)
                );
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }
}
