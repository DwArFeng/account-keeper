package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.DeriveQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TelqosCommand
public class DeriveCommand extends CliCommand {

    private static final String COMMAND_OPTION_DYNAMIC_DERIVE = "dynamic";
    private static final String COMMAND_OPTION_STATIC_DERIVE = "static";

    private static final String COMMAND_OPTION_LOGIN_STATE_ID = "i";
    private static final String COMMAND_OPTION_REMARK = "r";
    private static final String COMMAND_OPTION_EXPIRE_DATE = "d";
    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_LONG_OPTION_JSON_FILE = "json-file";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_DYNAMIC_DERIVE,
            COMMAND_OPTION_STATIC_DERIVE
    };

    private static final String IDENTITY = "derive";
    private static final String DESCRIPTION = "用户派生服务";

    private static final String CMD_LINE_SYNTAX_DYNAMIC_DERIVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_DERIVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOGIN_STATE_ID) + " login-state-id] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REMARK) + " remark]";
    private static final String CMD_LINE_SYNTAX_STATIC_DERIVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_DERIVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOGIN_STATE_ID) + " login-state-id] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_EXPIRE_DATE) + " expire-date] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REMARK) + " remark]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_DYNAMIC_DERIVE,
            CMD_LINE_SYNTAX_STATIC_DERIVE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final DeriveQosService deriveQosService;

    public DeriveCommand(DeriveQosService deriveQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.deriveQosService = deriveQosService;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_DYNAMIC_DERIVE).desc("动态派生").build());
        list.add(Option.builder(COMMAND_OPTION_STATIC_DERIVE).desc("静态派生").build());
        list.add(
                Option.builder(COMMAND_OPTION_LOGIN_STATE_ID).optionalArg(true).type(Number.class).hasArg(true)
                        .desc("登录状态 ID").build()
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
                case COMMAND_OPTION_DYNAMIC_DERIVE:
                    dynamicDerive(context, cmd);
                    break;
                case COMMAND_OPTION_STATIC_DERIVE:
                    staticDerive(context, cmd);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void dynamicDerive(Context context, CommandLine cmd) throws Exception {
        DynamicDeriveInfo deriveInfo = parseDynamicDeriveInfo(context, cmd);
        try {
            LoginState loginState = deriveQosService.dynamicDerive(deriveInfo);
            String token = Long.toString(loginState.getKey().getLongId());
            String expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginState.getExpireDate());
            context.sendMessage(
                    "派生成功! token: " + token + ", 过期时间: " + expiredDate
            );
        } catch (ServiceException e) {
            String code = Integer.toString(e.getCode().getCode());
            String tip = e.getCode().getTip();
            context.sendMessage("派生失败! 异常代码: " + code + ", 异常提示: " + tip);
        }
    }

    private void staticDerive(Context context, CommandLine cmd) throws Exception {
        StaticDeriveInfo deriveInfo = parseStaticDeriveInfo(context, cmd);
        try {
            LoginState loginState = deriveQosService.staticDerive(deriveInfo);
            String token = Long.toString(loginState.getKey().getLongId());
            String expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginState.getExpireDate());
            context.sendMessage(
                    "派生成功! token: " + token + ", 过期时间: " + expiredDate
            );
        } catch (ServiceException e) {
            String code = Integer.toString(e.getCode().getCode());
            String tip = e.getCode().getTip();
            context.sendMessage("派生失败! 异常代码: " + code + ", 异常提示: " + tip);
        }
    }

    private DynamicDeriveInfo parseDynamicDeriveInfo(Context context, CommandLine cmd) throws Exception {
        // 如果有 -json 选项，则从选项中获取 JSON，解析为 DynamicDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            return WebInputDynamicDeriveInfo.toStackBean(JSON.parseObject(json, WebInputDynamicDeriveInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，解析为 DynamicDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                return WebInputDynamicDeriveInfo.toStackBean(JSON.parseObject(in, WebInputDynamicDeriveInfo.class));
            }
        }
        // 如果没有 -json 和 --json-file 选项，则从命令行获取或交互式解析获取所需参数，构造 DynamicDeriveInfo。
        else {
            Long deriveStateId = parseDeriveStateId(context, cmd);
            String remark = parseRemark(context, cmd);
            return new DynamicDeriveInfo(new LongIdKey(deriveStateId), remark);
        }
    }

    private StaticDeriveInfo parseStaticDeriveInfo(Context context, CommandLine cmd) throws Exception {
        // 如果有 -json 选项，则从选项中获取 JSON，解析为 StaticDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            return JSON.parseObject(json, StaticDeriveInfo.class);
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，解析为 StaticDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                return WebInputStaticDeriveInfo.toStackBean(JSON.parseObject(in, WebInputStaticDeriveInfo.class));
            }
        }
        // 如果没有 -json 和 --json-file 选项，则从命令行获取或交互式解析获取所需参数，构造 StaticDeriveInfo。
        else {
            Long deriveStateId = parseDeriveStateId(context, cmd);
            Date expireDate = parseExpireDate(context, cmd);
            String remark = parseRemark(context, cmd);
            return new StaticDeriveInfo(new LongIdKey(deriveStateId), expireDate, remark);
        }
    }

    private Long parseDeriveStateId(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_LOGIN_STATE_ID)) {
            Number deriveStateIdNumber = (Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOGIN_STATE_ID);
            return deriveStateIdNumber.longValue();
        } else {
            context.sendMessage("请输入登录状态 ID: ");
            return Long.parseLong(context.receiveMessage());
        }
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
