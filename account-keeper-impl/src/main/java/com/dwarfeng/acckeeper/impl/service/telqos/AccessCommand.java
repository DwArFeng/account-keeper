package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson2.JSON;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccessQosService;
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
public class AccessCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "access";

    // region 指令选项

    private static final String COMMAND_OPTION_AUTH_INSPECT = "ai";
    private static final String COMMAND_OPTION_AUTH_INSPECT_LONG_OPT = "auth-inspect";
    private static final String COMMAND_OPTION_DYNAMIC_LOGIN = "dl";
    private static final String COMMAND_OPTION_DYNAMIC_LOGIN_LONG_OPT = "dynamic-login";
    private static final String COMMAND_OPTION_STATIC_LOGIN = "sl";
    private static final String COMMAND_OPTION_STATIC_LOGIN_LONG_OPT = "static-login";
    private static final String COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN = "tdl";
    private static final String COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN_LONG_OPT = "trusted-dynamic-login";
    private static final String COMMAND_OPTION_TRUSTED_STATIC_LOGIN = "tsl";
    private static final String COMMAND_OPTION_TRUSTED_STATIC_LOGIN_LONG_OPT = "trusted-static-login";
    private static final String COMMAND_OPTION_LOGOUT = "logout";
    private static final String COMMAND_OPTION_POSTPONE = "postpone";
    private static final String COMMAND_OPTION_KICK = "kick";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_AUTH_INSPECT,
            COMMAND_OPTION_DYNAMIC_LOGIN,
            COMMAND_OPTION_STATIC_LOGIN,
            COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN,
            COMMAND_OPTION_TRUSTED_STATIC_LOGIN,
            COMMAND_OPTION_LOGOUT,
            COMMAND_OPTION_POSTPONE,
            COMMAND_OPTION_KICK
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final AccessQosService accessQosService;

    public AccessCommand(AccessQosService accessQosService) {
        super(IDENTITY);
        this.accessQosService = accessQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_AUTH_INSPECT) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_LOGIN) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_LOGIN) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_TRUSTED_STATIC_LOGIN) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOGOUT) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_POSTPONE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_KICK) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_AUTH_INSPECT).longOpt(COMMAND_OPTION_AUTH_INSPECT_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_AUTH_INSPECT)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_DYNAMIC_LOGIN).longOpt(COMMAND_OPTION_DYNAMIC_LOGIN_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_DYNAMIC_LOGIN)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STATIC_LOGIN).longOpt(COMMAND_OPTION_STATIC_LOGIN_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_STATIC_LOGIN)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN)
                        .longOpt(COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_TRUSTED_DYNAMIC_LOGIN)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_TRUSTED_STATIC_LOGIN)
                        .longOpt(COMMAND_OPTION_TRUSTED_STATIC_LOGIN_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_TRUSTED_STATIC_LOGIN)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_LOGOUT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_LOGOUT)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_POSTPONE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_POSTPONE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_KICK)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_OPTION_KICK)).get()
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
            case COMMAND_OPTION_AUTH_INSPECT:
                handleAuthInspect(context, cmd);
                break;
            case COMMAND_OPTION_DYNAMIC_LOGIN:
                handleDynamicLogin(context, cmd);
                break;
            case COMMAND_OPTION_STATIC_LOGIN:
                handleStaticLogin(context, cmd);
                break;
            case COMMAND_OPTION_TRUSTED_DYNAMIC_LOGIN:
                handleTrustedDynamicLogin(context, cmd);
                break;
            case COMMAND_OPTION_TRUSTED_STATIC_LOGIN:
                handleTrustedStaticLogin(context, cmd);
                break;
            case COMMAND_OPTION_LOGOUT:
                handleLogout(context, cmd);
                break;
            case COMMAND_OPTION_POSTPONE:
                handlePostpone(context, cmd);
                break;
            case COMMAND_OPTION_KICK:
                handleKick(context, cmd);
                break;
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void handleAuthInspect(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        AuthInspectInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AuthInspectInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAuthInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputAuthInspectInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AuthInspectInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAuthInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputAuthInspectInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 授权查看。
        AuthInspectResult result = accessQosService.authInspect(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_AUTH_INSPECT_RESULT_HEADER));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_FIELD_IS_LOGIN, result.isLogin()));
        if (Objects.isNull(result.getLoginState())) {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_LOGIN_STATE_NULL));
        } else {
            LoginState loginState = result.getLoginState();
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_FIELD_LOGIN_STATE));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_LOGIN_STATE_KEY, loginState.getKey()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_ACCOUNT_KEY, loginState.getAccountKey()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_EXPIRE_DATE, loginState.getExpireDate()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_SERIAL_VERSION, loginState.getSerialVersion()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_GENERATED_DATE, loginState.getGeneratedDate()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_TYPE, loginState.getType()
            ));
            context.sendMessage(ImplMessages.message(
                    ImplMessageKey.TELQOS_ACCESS_FIELD_ITEM_REMARK, loginState.getRemark()
            ));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDynamicLogin(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        DynamicLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 DynamicLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputDynamicLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputDynamicLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 DynamicLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputDynamicLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputDynamicLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用动态登录服务。
        DynamicLoginResult result = accessQosService.dynamicLogin(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_DYNAMIC_LOGIN_RESULT_HEADER));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey())
        );
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate())
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()));
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleStaticLogin(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        StaticLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StaticLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputStaticLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputStaticLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StaticLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputStaticLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputStaticLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用静态登录服务。
        StaticLoginResult result = accessQosService.staticLogin(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_STATIC_LOGIN_RESULT_HEADER));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey())
        );
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate())
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()));
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleTrustedDynamicLogin(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        TrustedDynamicLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 TrustedDynamicLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputTrustedDynamicLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputTrustedDynamicLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 TrustedDynamicLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputTrustedDynamicLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputTrustedDynamicLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用可信动态登录服务。
        DynamicLoginResult result = accessQosService.trustedDynamicLogin(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_TRUSTED_DYNAMIC_LOGIN_RESULT_HEADER)
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey())
        );
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate())
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()));
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleTrustedStaticLogin(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        TrustedStaticLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 TrustedStaticLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputTrustedStaticLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputTrustedStaticLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 TrustedStaticLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputTrustedStaticLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputTrustedStaticLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用可信静态登录服务。
        StaticLoginResult result = accessQosService.trustedStaticLogin(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_TRUSTED_STATIC_LOGIN_RESULT_HEADER)
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey())
        );
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_EXPIRE_DATE, result.getExpireDate())
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_GENERATED_DATE, result.getGeneratedDate()
        ));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_TYPE, result.getType()));
        context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_COMMON_FIELD_REMARK, result.getRemark()));
    }

    private void handleLogout(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        LogoutInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 LogoutInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputLogoutInfo.toStackBean(
                    JSON.parseObject(json, WebInputLogoutInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 LogoutInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputLogoutInfo.toStackBean(
                        JSON.parseObject(in, WebInputLogoutInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 登出。
        accessQosService.logout(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_LOGOUT_SUCCEEDED)
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private void handlePostpone(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PostponeInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PostponeInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPostponeInfo.toStackBean(
                    JSON.parseObject(json, WebInputPostponeInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PostponeInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPostponeInfo.toStackBean(
                        JSON.parseObject(in, WebInputPostponeInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 延期。
        PostponeResult result = accessQosService.postpone(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_POSTPONE_RESULT_HEADER)
        );
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_LOGIN_STATE_KEY, result.getLoginStateKey()
        ));
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_COMMON_FIELD_ACCOUNT_KEY, result.getAccountKey()
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

    private void handleKick(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        KickInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 KickInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputKickInfo.toStackBean(
                    JSON.parseObject(json, WebInputKickInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 KickInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputKickInfo.toStackBean(
                        JSON.parseObject(in, WebInputKickInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 踢出。
        accessQosService.kick(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCESS_RESULT_KICK_SUCCEEDED)
        );
    }
}
