package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.handler.Resetter;
import com.dwarfeng.acckeeper.stack.service.ResetQosService;
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
public class ResetCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "reset";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_START = "start";
    private static final String COMMAND_OPTION_STOP = "stop";
    private static final String COMMAND_OPTION_STATUS = "status";
    private static final String COMMAND_OPTION_RESET_PROTECT = "reset-protect";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_START,
            COMMAND_OPTION_STOP,
            COMMAND_OPTION_STATUS,
            COMMAND_OPTION_RESET_PROTECT
    };

    // endregion

    private final ResetQosService resetQosService;

    public ResetCommand(ResetQosService resetQosService) {
        super(IDENTITY);
        this.resetQosService = resetQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_RESET_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    @SuppressWarnings("DuplicatedCode")
    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_START),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STOP),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_PROTECT)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_LOOKUP)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_RESET_OPTION_INSPECT)).get()
        );
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_START)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_RESET_OPTION_START)).get()
        );
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_STOP)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_RESET_OPTION_STOP)).get()
        );
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_STATUS)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_RESET_OPTION_STATUS)).get()
        );
        list.add(
                Option.builder().longOpt(COMMAND_OPTION_RESET_PROTECT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_RESET_OPTION_RESET)).get()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_LOOKUP:
                printResetters(context);
                break;
            case COMMAND_OPTION_START:
                resetQosService.start();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_RESET_RESULT_STARTED));
                break;
            case COMMAND_OPTION_STOP:
                resetQosService.stop();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_RESET_RESULT_STOPPED));
                break;
            case COMMAND_OPTION_STATUS:
                printStatus(context);
                break;
            case COMMAND_OPTION_RESET_PROTECT:
                resetQosService.resetProtect();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_RESET_RESULT_SUCCEEDED));
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void printResetters(CommandExecutor.Context context) throws Exception {
        List<Resetter> resetters = resetQosService.all();
        for (int i = 0; i < resetters.size(); i++) {
            context.sendMessage(String.format("%02d. %s", i + 1, resetters.get(i)));
        }
    }

    private void printStatus(CommandExecutor.Context context) throws Exception {
        boolean startedFlag = resetQosService.isStarted();
        context.sendMessage(
                String.format(ImplMessages.message(ImplMessageKey.TELQOS_RESET_FIELD_STARTED), startedFlag)
        );
    }
}
