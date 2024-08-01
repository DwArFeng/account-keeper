package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccountQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class AccountCommand extends CliCommand {

    private static final String COMMAND_OPTION_REGISTER = "register";
    private static final String COMMAND_OPTION_UPDATE = "update";
    private static final String COMMAND_OPTION_DELETE = "delete";
    private static final String COMMAND_OPTION_CHECK_PASSWORD = "cp";
    private static final String COMMAND_OPTION_CHECK_PASSWORD_LONG_OPT = "check-password";
    private static final String COMMAND_OPTION_UPDATE_PASSWORD = "up";
    private static final String COMMAND_OPTION_UPDATE_PASSWORD_LONG_OPT = "update-password";
    private static final String COMMAND_OPTION_RESET_PASSWORD = "rp";
    private static final String COMMAND_OPTION_RESET_PASSWORD_LONG_OPT = "reset-password";
    private static final String COMMAND_OPTION_INVALID = "invalid";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_REGISTER,
            COMMAND_OPTION_UPDATE,
            COMMAND_OPTION_DELETE,
            COMMAND_OPTION_CHECK_PASSWORD,
            COMMAND_OPTION_UPDATE_PASSWORD,
            COMMAND_OPTION_RESET_PASSWORD,
            COMMAND_OPTION_INVALID
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    private static final String IDENTITY = "account";
    private static final String DESCRIPTION = "账户操作";

    private static final String CMD_LINE_SYNTAX_REGISTER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_REGISTER) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_UPDATE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_DELETE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_DELETE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_CHECK_PASSWORD = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CHECK_PASSWORD) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_UPDATE_PASSWORD = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE_PASSWORD) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_RESET_PASSWORD = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_PASSWORD) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_INVALID = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_INVALID) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_REGISTER,
            CMD_LINE_SYNTAX_UPDATE,
            CMD_LINE_SYNTAX_DELETE,
            CMD_LINE_SYNTAX_CHECK_PASSWORD,
            CMD_LINE_SYNTAX_UPDATE_PASSWORD,
            CMD_LINE_SYNTAX_RESET_PASSWORD,
            CMD_LINE_SYNTAX_INVALID
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final AccountQosService accountQosService;

    public AccountCommand(AccountQosService accountQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.accountQosService = accountQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_REGISTER).desc("注册账户").build());
        list.add(Option.builder(COMMAND_OPTION_UPDATE).desc("更新账户").build());
        list.add(Option.builder(COMMAND_OPTION_DELETE).desc("删除账户").build());
        list.add(
                Option.builder(COMMAND_OPTION_CHECK_PASSWORD).longOpt(COMMAND_OPTION_CHECK_PASSWORD_LONG_OPT)
                        .desc("检查密码").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_UPDATE_PASSWORD).longOpt(COMMAND_OPTION_UPDATE_PASSWORD_LONG_OPT)
                        .desc("更新密码").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_RESET_PASSWORD).longOpt(COMMAND_OPTION_RESET_PASSWORD_LONG_OPT)
                        .desc("重置密码").build()
        );
        list.add(Option.builder(COMMAND_OPTION_INVALID).desc("使账户之前的登录信息无效").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON文件")
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
                case COMMAND_OPTION_REGISTER:
                    handleRegister(context, cmd);
                    break;
                case COMMAND_OPTION_UPDATE:
                    handleUpdate(context, cmd);
                    break;
                case COMMAND_OPTION_DELETE:
                    handleDelete(context, cmd);
                    break;
                case COMMAND_OPTION_CHECK_PASSWORD:
                    handleCheckPassword(context, cmd);
                    break;
                case COMMAND_OPTION_UPDATE_PASSWORD:
                    handleUpdatePassword(context, cmd);
                    break;
                case COMMAND_OPTION_RESET_PASSWORD:
                    handleResetPassword(context, cmd);
                    break;
                case COMMAND_OPTION_INVALID:
                    handleInvalid(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleRegister(Context context, CommandLine cmd) throws Exception {
        AccountRegisterInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AccountRegisterInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAccountRegisterInfo.toStackBean(
                    JSON.parseObject(json, WebInputAccountRegisterInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AccountRegisterInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAccountRegisterInfo.toStackBean(
                        JSON.parseObject(in, WebInputAccountRegisterInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.register(info);

        // 输出结果。
        context.sendMessage("账户注册成功");
    }

    private void handleUpdate(Context context, CommandLine cmd) throws Exception {
        AccountUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AccountUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAccountUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputAccountUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AccountUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAccountUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputAccountUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.update(info);

        // 输出结果。
        context.sendMessage("账户更新成功");
    }

    private void handleDelete(Context context, CommandLine cmd) throws Exception {
        StringIdKey key;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StringIdKey。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            key = WebInputStringIdKey.toStackBean(
                    JSON.parseObject(json, WebInputStringIdKey.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StringIdKey。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                key = WebInputStringIdKey.toStackBean(
                        JSON.parseObject(in, WebInputStringIdKey.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.delete(key);

        // 输出结果。
        context.sendMessage("账户删除成功");
    }

    private void handleCheckPassword(Context context, CommandLine cmd) throws Exception {
        PasswordCheckInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordCheckInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordCheckInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordCheckInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordCheckInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordCheckInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordCheckInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        boolean result = accountQosService.checkPassword(info);

        // 输出结果。
        context.sendMessage("账户密码检查结果：" + (result ? "正确" : "错误"));
    }

    private void handleUpdatePassword(Context context, CommandLine cmd) throws Exception {
        PasswordUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.updatePassword(info);

        // 输出结果。
        context.sendMessage("账户密码更新成功");
    }

    private void handleResetPassword(Context context, CommandLine cmd) throws Exception {
        PasswordResetInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordResetInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordResetInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordResetInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordResetInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordResetInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordResetInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.resetPassword(info);

        // 输出结果。
        context.sendMessage("账户密码重置成功");
    }

    private void handleInvalid(Context context, CommandLine cmd) throws Exception {
        StringIdKey key;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StringIdKey。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            key = WebInputStringIdKey.toStackBean(
                    JSON.parseObject(json, WebInputStringIdKey.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StringIdKey。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                key = WebInputStringIdKey.toStackBean(
                        JSON.parseObject(in, WebInputStringIdKey.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用服务。
        accountQosService.invalid(key);

        // 输出结果。
        context.sendMessage("账户登录信息无效化成功");
    }
}
