package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson2.JSON;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.acckeeper.stack.service.DeriveQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class DeriveCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "derive";

    // region 指令选项

    private static final String COMMAND_OPTION_DYNAMIC_DERIVE = "dd";
    private static final String COMMAND_OPTION_DYNAMIC_DERIVE_LONG_OPT = "dynamic-derive";
    private static final String COMMAND_OPTION_STATIC_DERIVE = "sd";
    private static final String COMMAND_OPTION_STATIC_DERIVE_LONG_OPT = "static-derive";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_DYNAMIC_DERIVE,
            COMMAND_OPTION_STATIC_DERIVE
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final DeriveQosService deriveQosService;

    public DeriveCommand(DeriveQosService deriveQosService) {
        super(IDENTITY);
        this.deriveQosService = deriveQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_DERIVE_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_DERIVE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_DERIVE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_DYNAMIC_DERIVE).longOpt(COMMAND_OPTION_DYNAMIC_DERIVE_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_DERIVE_OPTION_DYNAMIC_DERIVE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STATIC_DERIVE).longOpt(COMMAND_OPTION_STATIC_DERIVE_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_DERIVE_OPTION_STATIC_DERIVE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_OPTION_JSON_STRING))
                        .hasArg().type(String.class).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_OPTION_JSON_FILE))
                        .hasArg().type(File.class).get()
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
            case COMMAND_OPTION_DYNAMIC_DERIVE:
                handleDynamicDerive(context, cmd);
                break;
            case COMMAND_OPTION_STATIC_DERIVE:
                handleStaticDerive(context, cmd);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDynamicDerive(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        DynamicDeriveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 DynamicDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputDynamicDeriveInfo.toStackBean(
                    JSON.parseObject(json, WebInputDynamicDeriveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 DynamicDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputDynamicDeriveInfo.toStackBean(
                        JSON.parseObject(in, WebInputDynamicDeriveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用动态派生服务。
        DynamicDeriveResult result = deriveQosService.dynamicDerive(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_DERIVE_RESULT_DYNAMIC_DERIVE_RESULT_HEADER
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey().getStringId()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()
        ));
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleStaticDerive(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        StaticDeriveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StaticDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputStaticDeriveInfo.toStackBean(
                    JSON.parseObject(json, WebInputStaticDeriveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StaticDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputStaticDeriveInfo.toStackBean(
                        JSON.parseObject(in, WebInputStaticDeriveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用静态派生服务。
        StaticDeriveResult result = deriveQosService.staticDerive(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_DERIVE_RESULT_STATIC_DERIVE_RESULT_HEADER
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey().getStringId()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()
        ));
    }
}
