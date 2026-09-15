package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson2.JSON;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.acckeeper.impl.internal.i18n.ImplMessages;
import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccountQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.web.sdk.bean.key.WebInputStringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class AccountCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "account";

    // region 指令选项

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

    // endregion

    private final AccountQosService accountQosService;

    public AccountCommand(AccountQosService accountQosService) {
        super(IDENTITY);
        this.accountQosService = accountQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return _ -> ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_REGISTER) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DELETE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CHECK_PASSWORD) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_UPDATE_PASSWORD) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_PASSWORD) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_INVALID) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_REGISTER)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_REGISTER)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_UPDATE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_UPDATE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_DELETE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_DELETE)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_CHECK_PASSWORD).longOpt(COMMAND_OPTION_CHECK_PASSWORD_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_CHECK_PASSWORD)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_UPDATE_PASSWORD).longOpt(COMMAND_OPTION_UPDATE_PASSWORD_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_UPDATE_PASSWORD)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_RESET_PASSWORD).longOpt(COMMAND_OPTION_RESET_PASSWORD_LONG_OPT)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_RESET_PASSWORD)).get()
        );
        list.add(
                Option.builder(COMMAND_OPTION_INVALID)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_OPTION_INVALID)).get()
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
            default:
                throw new IllegalStateException(
                        ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_INTERNAL_UNREACHABLE)
                );
        }
    }

    private void handleRegister(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        AccountRegisterInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AccountRegisterInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAccountRegisterInfo.toStackBean(
                    JSON.parseObject(json, WebInputAccountRegisterInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AccountRegisterInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAccountRegisterInfo.toStackBean(
                        JSON.parseObject(in, WebInputAccountRegisterInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.register(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_REGISTER_SUCCEEDED)
        );
    }

    private void handleUpdate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        AccountUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 AccountUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputAccountUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputAccountUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 AccountUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputAccountUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputAccountUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.update(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_UPDATE_SUCCEEDED)
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDelete(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        StringIdKey key;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StringIdKey。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            key = WebInputStringIdKey.toStackBean(
                    JSON.parseObject(json, WebInputStringIdKey.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StringIdKey。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                key = WebInputStringIdKey.toStackBean(
                        JSON.parseObject(in, WebInputStringIdKey.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.delete(key);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_DELETE_SUCCEEDED)
        );
    }

    private void handleCheckPassword(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PasswordCheckInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordCheckInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordCheckInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordCheckInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordCheckInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordCheckInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordCheckInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        boolean result = accountQosService.checkPassword(info);

        // 输出结果。
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_RESULT,
                ImplMessages.message(
                        result ? ImplMessageKey.TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_PASSED :
                                ImplMessageKey.TELQOS_ACCOUNT_RESULT_PASSWORD_CHECK_FAILED
                )
        ));
    }

    private void handleUpdatePassword(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PasswordUpdateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordUpdateInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordUpdateInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordUpdateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordUpdateInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordUpdateInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordUpdateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.updatePassword(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_PASSWORD_UPDATE_SUCCEEDED)
        );
    }

    private void handleResetPassword(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        PasswordResetInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 PasswordResetInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputPasswordResetInfo.toStackBean(
                    JSON.parseObject(json, WebInputPasswordResetInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 PasswordResetInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputPasswordResetInfo.toStackBean(
                        JSON.parseObject(in, WebInputPasswordResetInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.resetPassword(info);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_PASSWORD_RESET_SUCCEEDED)
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleInvalid(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        StringIdKey key;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StringIdKey。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            key = WebInputStringIdKey.toStackBean(
                    JSON.parseObject(json, WebInputStringIdKey.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StringIdKey。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                key = WebInputStringIdKey.toStackBean(
                        JSON.parseObject(in, WebInputStringIdKey.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException(
                    ImplMessages.message(ImplMessageKey.TELQOS_COMMON_RESULT_UNSUPPORTED)
            );
        }

        // 调用服务。
        accountQosService.invalid(key);

        // 输出结果。
        context.sendMessage(
                ImplMessages.message(ImplMessageKey.TELQOS_ACCOUNT_RESULT_INVALID_SUCCEEDED)
        );
    }
}
