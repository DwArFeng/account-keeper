package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccessQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
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

    private static final String COMMAND_OPTION_AUTH_INSPECT = "ai";
    private static final String COMMAND_OPTION_AUTH_INSPECT_LONG_OPT = "auth-inspect";
    private static final String COMMAND_OPTION_DYNAMIC_LOGIN = "dl";
    private static final String COMMAND_OPTION_DYNAMIC_LOGIN_LONG_OPT = "dynamic-login";
    private static final String COMMAND_OPTION_STATIC_LOGIN = "sl";
    private static final String COMMAND_OPTION_STATIC_LOGIN_LONG_OPT = "static-login";
    private static final String COMMAND_OPTION_LOGOUT = "logout";
    private static final String COMMAND_OPTION_POSTPONE = "postpone";
    private static final String COMMAND_OPTION_KICK = "kick";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_AUTH_INSPECT,
            COMMAND_OPTION_DYNAMIC_LOGIN,
            COMMAND_OPTION_STATIC_LOGIN,
            COMMAND_OPTION_LOGOUT,
            COMMAND_OPTION_POSTPONE,
            COMMAND_OPTION_KICK
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    private static final String IDENTITY = "access";
    private static final String DESCRIPTION = "访问服务";

    private static final String CMD_LINE_SYNTAX_AUTH = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_AUTH_INSPECT) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_DYNAMIC_LOGIN = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_LOGIN) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_STATIC_LOGIN = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_LOGIN) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_LOGOUT = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOGOUT) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_POSTPONE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_POSTPONE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_KICK = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_KICK) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_AUTH,
            CMD_LINE_SYNTAX_DYNAMIC_LOGIN,
            CMD_LINE_SYNTAX_STATIC_LOGIN,
            CMD_LINE_SYNTAX_LOGOUT,
            CMD_LINE_SYNTAX_POSTPONE,
            CMD_LINE_SYNTAX_KICK
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final AccessQosService accessQosService;

    public AccessCommand(AccessQosService accessQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.accessQosService = accessQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_AUTH_INSPECT).longOpt(COMMAND_OPTION_AUTH_INSPECT_LONG_OPT)
                        .desc("授权查看").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_DYNAMIC_LOGIN).longOpt(COMMAND_OPTION_DYNAMIC_LOGIN_LONG_OPT)
                        .desc("动态登录").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STATIC_LOGIN).longOpt(COMMAND_OPTION_STATIC_LOGIN_LONG_OPT)
                        .desc("静态登录").build()
        );
        list.add(Option.builder(COMMAND_OPTION_LOGOUT).desc("登出").build());
        list.add(Option.builder(COMMAND_OPTION_POSTPONE).desc("延期").build());
        list.add(Option.builder(COMMAND_OPTION_KICK).desc("踢出").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON 字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON 文件")
                        .hasArg().type(File.class).build()
        );
        return list;
    }

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
                case COMMAND_OPTION_AUTH_INSPECT:
                    handleAuthInspect(context, cmd);
                    break;
                case COMMAND_OPTION_DYNAMIC_LOGIN:
                    handleDynamicLogin(context, cmd);
                    break;
                case COMMAND_OPTION_STATIC_LOGIN:
                    handleStaticLogin(context, cmd);
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
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleAuthInspect(Context context, CommandLine cmd) throws Exception {
        AuthInspectInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AuthInspectInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAuthInspectInfo.toStackBean(
                    JSON.parseObject(json, WebInputAuthInspectInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AuthInspectInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAuthInspectInfo.toStackBean(
                        JSON.parseObject(in, WebInputAuthInspectInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 授权查看。
        AuthInspectResult result = accessQosService.authInspect(info);

        // 输出结果。
        context.sendMessage("授权查看结果: ");
        context.sendMessage("  isLogin: " + result.isLogin());
        if (Objects.isNull(result.getLoginState())) {
            context.sendMessage("  loginState: null");
        } else {
            LoginState loginState = result.getLoginState();
            context.sendMessage("  loginState: ");
            context.sendMessage("    loginStateKey: " + loginState.getKey());
            context.sendMessage("    accountKey: " + loginState.getAccountKey());
            context.sendMessage("    expireDate: " + loginState.getExpireDate());
            context.sendMessage("    serialVersion: " + loginState.getSerialVersion());
            context.sendMessage("    generatedDate: " + loginState.getGeneratedDate());
            context.sendMessage("    type: " + loginState.getType());
            context.sendMessage("    remark: " + loginState.getRemark());
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDynamicLogin(Context context, CommandLine cmd) throws Exception {
        DynamicLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 DynamicLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputDynamicLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputDynamicLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 DynamicLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputDynamicLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputDynamicLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用动态登录服务。
        DynamicLoginResult result = accessQosService.dynamicLogin(info);

        // 输出结果。
        context.sendMessage("动态登录结果: ");
        context.sendMessage("  loginStateKey: " + result.getLoginStateKey());
        context.sendMessage("  accountKey: " + result.getAccountKey());
        context.sendMessage("  expireDate: " + result.getExpireDate());
        context.sendMessage("  generatedDate: " + result.getGeneratedDate());
        context.sendMessage("  type: " + result.getType());
        context.sendMessage("  remark: " + result.getRemark());
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleStaticLogin(Context context, CommandLine cmd) throws Exception {
        StaticLoginInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StaticLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputStaticLoginInfo.toStackBean(
                    JSON.parseObject(json, WebInputStaticLoginInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StaticLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputStaticLoginInfo.toStackBean(
                        JSON.parseObject(in, WebInputStaticLoginInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用静态登录服务。
        StaticLoginResult result = accessQosService.staticLogin(info);

        // 输出结果。
        context.sendMessage("静态登录结果: ");
        context.sendMessage("  loginStateKey: " + result.getLoginStateKey());
        context.sendMessage("  accountKey: " + result.getAccountKey());
        context.sendMessage("  expireDate: " + result.getExpireDate());
        context.sendMessage("  generatedDate: " + result.getGeneratedDate());
        context.sendMessage("  type: " + result.getType());
        context.sendMessage("  remark: " + result.getRemark());
    }

    private void handleLogout(Context context, CommandLine cmd) throws Exception {
        LogoutInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 LogoutInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputLogoutInfo.toStackBean(
                    JSON.parseObject(json, WebInputLogoutInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 LogoutInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputLogoutInfo.toStackBean(
                        JSON.parseObject(in, WebInputLogoutInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 登出。
        accessQosService.logout(info);

        // 输出结果。
        context.sendMessage("登出成功");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handlePostpone(Context context, CommandLine cmd) throws Exception {
        PostponeInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PostponeInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPostponeInfo.toStackBean(
                    JSON.parseObject(json, WebInputPostponeInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PostponeInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPostponeInfo.toStackBean(
                        JSON.parseObject(in, WebInputPostponeInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 延期。
        PostponeResult result = accessQosService.postpone(info);

        // 输出结果。
        context.sendMessage("延期结果: ");
        context.sendMessage("  loginStateKey: " + result.getLoginStateKey());
        context.sendMessage("  accountKey: " + result.getAccountKey());
        context.sendMessage("  expireDate: " + result.getExpireDate());
        context.sendMessage("  generatedDate: " + result.getGeneratedDate());
        context.sendMessage("  type: " + result.getType());
        context.sendMessage("  remark: " + result.getRemark());
    }

    private void handleKick(Context context, CommandLine cmd) throws Exception {
        KickInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 KickInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputKickInfo.toStackBean(
                    JSON.parseObject(json, WebInputKickInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 KickInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputKickInfo.toStackBean(
                        JSON.parseObject(in, WebInputKickInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 踢出。
        accessQosService.kick(info);

        // 输出结果。
        context.sendMessage("踢出成功");
    }
}
