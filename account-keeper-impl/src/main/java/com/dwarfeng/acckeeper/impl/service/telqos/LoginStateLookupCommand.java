package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson2.JSON;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputLoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateLookupQosService;
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
import java.util.Objects;

@TelqosCommand
public class LoginStateLookupCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "nsl";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "lookup";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final LoginStateLookupQosService loginStateLookupQosService;

    public LoginStateLookupCommand(LoginStateLookupQosService loginStateLookupQosService) {
        super(IDENTITY);
        this.loginStateLookupQosService = loginStateLookupQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_OPTION_LOOKUP)).get()
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
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        LoginStateLookupInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 LoginStateLookupInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputLoginStateLookupInfo.toStackBean(
                    JSON.parseObject(json, WebInputLoginStateLookupInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 LoginStateLookupInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputLoginStateLookupInfo.toStackBean(
                        JSON.parseObject(in, WebInputLoginStateLookupInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 登录状态查询。
        LoginStateLookupResult result = loginStateLookupQosService.lookup(info);

        // 输出结果。
        if (Objects.isNull(result)) {
            context.sendMessage(
                    ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_RESULT_QUERY_RESULT_NULL)
            );
        } else {
            List<LoginState> loginStates = result.getLoginStates();
            if (Objects.isNull(loginStates)) {
                context.sendMessage(
                        ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_RESULT_QUERY_RESULT_HEADER)
                );
                context.sendMessage(
                        ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_RESULT_LOGIN_STATES_NULL)
                );
            } else {
                processLoginStateList(context, loginStates);
            }
        }
    }

    private void processLoginStateList(CommandExecutor.Context context, List<LoginState> loginStates)
            throws Exception {
        while (true) {
            CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                    context, loginStates, ImplMessages.message(
                            ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_RESULT_DATA_TOTAL, loginStates.size()
                    )
            );
            if (cropResult.exitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.beginIndex(); i < cropResult.endIndex(); i++) {
                LoginState loginState = loginStates.get(i);
                printLoginState(context, i, cropResult.endIndex(), loginState);
            }
        }
    }

    private void printLoginState(CommandExecutor.Context context, int i, int endIndex, LoginState loginState)
            throws Exception {
        context.sendMessage(String.format(
                ImplMessages.message(ImplMessageKey.TELQOS_LOGIN_STATE_LOOKUP_RESULT_INDEX), i, endIndex
        ));
        if (Objects.isNull(loginState)) {
            context.sendMessage("null");
        } else {
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, loginState.getKey()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, loginState.getAccountKey()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, loginState.getExpireDate()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_SERIAL_VERSION, loginState.getSerialVersion()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, loginState.getGeneratedDate()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, loginState.getType()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, loginState.getRemark()
            ));
        }
        context.sendMessage("");
    }
}
