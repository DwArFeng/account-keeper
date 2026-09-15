package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.stack.handler.Protector;
import com.dwarfeng.acckeeper.stack.service.ProtectQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class ProtectLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectLocalCacheCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "plc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final ProtectQosService protectQosService;

    public ProtectLocalCacheCommand(ProtectQosService protectQosService) {
        super(IDENTITY);
        this.protectQosService = protectQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " account-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(String.class)
                        .argName("account-id")
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_OPTION_LOOKUP)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_OPTION_CLEAR)).get()
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
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                handleClear(context);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String accountId;
        try {
            accountId = cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP);
        } catch (ParseException e) {
            LOGGER.warn(ImplMessages.message(ImplMessageKey.LOG_COMMAND_OPTION_PARSE_FAILED), e);
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_RESULT_COMMAND_FORMAT_ERROR,
                    context.getRuntimeIdentity() + " " +
                            CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " account-id"
            ));
            context.sendMessage(
                    ImplMessages.message(ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_RESULT_ARGUMENT_TYPE_HINT)
            );
            return;
        }
        Protector protector = protectQosService.getProtector(new StringIdKey(accountId));
        if (Objects.isNull(protector)) {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_NOT_EXISTS));
            return;
        }
        context.sendMessage(Objects.toString(protector));
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        protectQosService.clearLocalCache();
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_PROTECT_LOCAL_CACHE_RESULT_CLEARED)
        );
    }
}
