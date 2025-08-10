package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicLoginInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@TelqosCommand
public class LoginCommand extends CliCommand {

    private static final String COMMAND_OPTION_DYNAMIC_LOGIN = "dynamic";
    private static final String COMMAND_OPTION_STATIC_LOGIN = "static";

    private static final String COMMAND_OPTION_ACCOUNT_ID = "a";
    private static final String COMMAND_OPTION_PASSWORD = "p";
    private static final String COMMAND_OPTION_EXTRA_PARAMS = "e";
    private static final String COMMAND_OPTION_REMARK = "r";
    private static final String COMMAND_OPTION_EXPIRE_DATE = "d";
    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_LONG_OPTION_JSON_FILE = "json-file";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_DYNAMIC_LOGIN,
            COMMAND_OPTION_STATIC_LOGIN
    };

    private static final String IDENTITY = "login";
    private static final String DESCRIPTION = "用户登录服务，仅用于测试！测试完成后必须立即更改密码！";

    private static final String CMD_LINE_SYNTAX_DYNAMIC_LOGIN = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_LOGIN) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ACCOUNT_ID) + " account-id] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_PASSWORD) + " password] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_EXTRA_PARAMS) + " extra-params] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REMARK) + " remark]";
    private static final String CMD_LINE_SYNTAX_STATIC_LOGIN = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_LOGIN) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ACCOUNT_ID) + " account-id] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_PASSWORD) + " password] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_EXTRA_PARAMS) + " extra-params] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_EXPIRE_DATE) + " expire-date] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REMARK) + " remark]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_DYNAMIC_LOGIN,
            CMD_LINE_SYNTAX_STATIC_LOGIN
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final LoginQosService loginQosService;

    public LoginCommand(LoginQosService loginQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginQosService = loginQosService;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_DYNAMIC_LOGIN).desc("动态登录").build());
        list.add(Option.builder(COMMAND_OPTION_STATIC_LOGIN).desc("静态登录").build());
        list.add(
                Option.builder(COMMAND_OPTION_ACCOUNT_ID).optionalArg(true).type(String.class).hasArg(true)
                        .desc("用户名").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_PASSWORD).optionalArg(true).type(String.class).hasArg(true)
                        .desc("密码").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_EXTRA_PARAMS).optionalArg(true).desc("额外参数").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_REMARK).optionalArg(true).type(String.class).hasArg(true)
                        .desc("备注").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_EXPIRE_DATE).optionalArg(true).type(String.class).hasArg(true)
                        .desc("过期时间").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_LONG_OPTION_JSON_FILE).desc("JSON文件")
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
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_DYNAMIC_LOGIN:
                    dynamicLogin(context, cmd);
                    break;
                case COMMAND_OPTION_STATIC_LOGIN:
                    staticLogin(context, cmd);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void dynamicLogin(Context context, CommandLine cmd) throws Exception {
        DynamicLoginInfo loginInfo = parseDynamicLoginInfo(context, cmd);
        try {
            LoginState loginState = loginQosService.dynamicLogin(loginInfo);
            String token = Long.toString(loginState.getKey().getLongId());
            String expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginState.getExpireDate());
            context.sendMessage(
                    "登录成功! token: " + token + ", 过期时间: " + expiredDate
            );
        } catch (ServiceException e) {
            String code = Integer.toString(e.getCode().getCode());
            String tip = e.getCode().getTip();
            context.sendMessage("登录失败! 异常代码: " + code + ", 异常提示: " + tip);
        }
    }

    private void staticLogin(Context context, CommandLine cmd) throws Exception {
        StaticLoginInfo loginInfo = parseStaticLoginInfo(context, cmd);
        try {
            LoginState loginState = loginQosService.staticLogin(loginInfo);
            String token = Long.toString(loginState.getKey().getLongId());
            String expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginState.getExpireDate());
            context.sendMessage(
                    "登录成功! token: " + token + ", 过期时间: " + expiredDate
            );
        } catch (ServiceException e) {
            String code = Integer.toString(e.getCode().getCode());
            String tip = e.getCode().getTip();
            context.sendMessage("登录失败! 异常代码: " + code + ", 异常提示: " + tip);
        }
    }

    private DynamicLoginInfo parseDynamicLoginInfo(Context context, CommandLine cmd) throws Exception {
        // 如果有 -json 选项，则从选项中获取 JSON，解析为 DynamicLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            return WebInputDynamicLoginInfo.toStackBean(JSON.parseObject(json, WebInputDynamicLoginInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，解析为 DynamicLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                return WebInputDynamicLoginInfo.toStackBean(JSON.parseObject(in, WebInputDynamicLoginInfo.class));
            }
        }
        // 如果没有 -json 和 --json-file 选项，则从命令行获取或交互式解析获取所需参数，构造 DynamicLoginInfo。
        else {
            String accountId = parseAccountId(context, cmd);
            String password = parsePassword(context, cmd);
            Map<String, String> extraParams = parseExtraParams(context, cmd);
            String remark = parseRemark(context, cmd);
            return new DynamicLoginInfo(new StringIdKey(accountId), password, remark, extraParams);
        }
    }

    private StaticLoginInfo parseStaticLoginInfo(Context context, CommandLine cmd) throws Exception {
        // 如果有 -json 选项，则从选项中获取 JSON，解析为 StaticLoginInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            return JSON.parseObject(json, StaticLoginInfo.class);
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，解析为 StaticLoginInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                return WebInputStaticLoginInfo.toStackBean(JSON.parseObject(in, WebInputStaticLoginInfo.class));
            }
        }
        // 如果没有 -json 和 --json-file 选项，则从命令行获取或交互式解析获取所需参数，构造 StaticLoginInfo。
        else {
            String accountId = parseAccountId(context, cmd);
            String password = parsePassword(context, cmd);
            Map<String, String> extraParams = parseExtraParams(context, cmd);
            Date expireDate = parseExpireDate(context, cmd);
            String remark = parseRemark(context, cmd);
            return new StaticLoginInfo(new StringIdKey(accountId), password, expireDate, remark, extraParams);
        }
    }

    private String parseAccountId(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_ACCOUNT_ID)) {
            return (String) cmd.getParsedOptionValue(COMMAND_OPTION_ACCOUNT_ID);
        } else {
            context.sendMessage("请输入账户 ID: ");
            return context.receiveMessage();
        }
    }

    private String parsePassword(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_PASSWORD)) {
            return (String) cmd.getParsedOptionValue(COMMAND_OPTION_PASSWORD);
        } else {
            context.sendMessage("请输入密码: ");
            return context.receiveMessage();
        }
    }

    private Map<String, String> parseExtraParams(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_EXTRA_PARAMS)) {
            return interactiveParseExtraParams(context);
        } else {
            return new HashMap<>();
        }
    }

    private static final String CONTROL_STRING_NEXT = "N";
    private static final String CONTROL_STRING_QUIT = "Q";

    private Map<String, String> interactiveParseExtraParams(Context context) throws Exception {
        Map<String, String> result = new HashMap<>();

        boolean continueFlag = true;
        do {
            context.sendMessage("请输入参数键: ");
            String key = context.receiveMessage();
            context.sendMessage("请输入参数值: ");
            String value = context.receiveMessage();

            result.put(key, value);

            boolean mismatchFlag;
            do {
                context.sendMessage(String.format(
                        "输入 %s 继续, 输入 %s 退出", CONTROL_STRING_NEXT, CONTROL_STRING_QUIT
                ));
                String controlString = context.receiveMessage();
                mismatchFlag = true;
                if (Strings.CI.equals(controlString.toUpperCase(), CONTROL_STRING_NEXT)) {
                    mismatchFlag = false;
                } else if (Strings.CI.equals(controlString.toUpperCase(), CONTROL_STRING_QUIT)) {
                    mismatchFlag = false;
                    continueFlag = false;
                }
                if (mismatchFlag) {
                    context.sendMessage("输入有误, 请重新输入");
                }
            } while (mismatchFlag);
        } while (continueFlag);

        return result;
    }

    private String parseRemark(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_REMARK)) {
            return (String) cmd.getParsedOptionValue(COMMAND_OPTION_REMARK);
        } else {
            context.sendMessage("请输入备注: ");
            return context.receiveMessage();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private Date parseExpireDate(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_EXPIRE_DATE)) {
            String expireDateString = (String) cmd.getParsedOptionValue(COMMAND_OPTION_EXPIRE_DATE);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireDateString);
        } else {
            context.sendMessage("请输入过期时间（yyyy-MM-dd HH:mm:ss）: ");
            String expireDateString = context.receiveMessage();
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireDateString);
        }
    }
}
