package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.service.CleanQosService;
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
public class CleanCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "clean";

    // region 指令选项

    private static final String COMMAND_OPTION_ONLINE = "online";
    private static final String COMMAND_OPTION_OFFLINE = "offline";
    private static final String COMMAND_OPTION_START = "start";
    private static final String COMMAND_OPTION_STOP = "stop";
    private static final String COMMAND_OPTION_STATUS = "status";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_ONLINE,
            COMMAND_OPTION_OFFLINE,
            COMMAND_OPTION_START,
            COMMAND_OPTION_STOP,
            COMMAND_OPTION_STATUS
    };

    // endregion

    private final CleanQosService cleanQosService;

    public CleanCommand(CleanQosService cleanQosService) {
        super(IDENTITY);
        this.cleanQosService = cleanQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    @SuppressWarnings("DuplicatedCode")
    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_ONLINE),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_OFFLINE),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_START),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STOP),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_ONLINE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_OPTION_ONLINE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_OFFLINE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_OPTION_OFFLINE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_START)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_OPTION_START)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STOP)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_OPTION_STOP)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STATUS)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_OPTION_STATUS)).get()
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
            case COMMAND_OPTION_ONLINE:
                cleanQosService.online();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_RESULT_ONLINE));
                break;
            case COMMAND_OPTION_OFFLINE:
                cleanQosService.offline();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_RESULT_OFFLINE));
                break;
            case COMMAND_OPTION_START:
                cleanQosService.start();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_RESULT_STARTED));
                break;
            case COMMAND_OPTION_STOP:
                cleanQosService.stop();
                context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_RESULT_STOPPED));
                break;
            case COMMAND_OPTION_STATUS:
                printStatus(context);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void printStatus(CommandExecutor.Context context) throws Exception {
        boolean onlineFlag = cleanQosService.isOnline();
        boolean latchHoldingFlag = cleanQosService.isLockHolding();
        boolean startedFlag = cleanQosService.isStarted();
        boolean workingFlag = cleanQosService.isWorking();

        context.sendMessage(String.format(
                ImplMessages.message(ImplMessageKey.TELQOS_CLEAN_FIELD_STATUS),
                onlineFlag, latchHoldingFlag, startedFlag, workingFlag
        ));
    }
}
