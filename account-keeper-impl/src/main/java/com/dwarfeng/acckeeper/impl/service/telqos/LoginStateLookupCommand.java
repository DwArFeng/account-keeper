package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputLoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginStateLookupResult;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateLookupQosService;
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
public class LoginStateLookupCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "lookup";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "nsl";
    private static final String DESCRIPTION = "登录状态查询服务";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final LoginStateLookupQosService loginStateLookupQosService;

    public LoginStateLookupCommand(LoginStateLookupQosService loginStateLookupQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginStateLookupQosService = loginStateLookupQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).desc("登录状态查询").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON 字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON 文件")
                        .hasArg().type(File.class).build()
        );
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
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
                case COMMAND_OPTION_LOOKUP:
                    handleLookup(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        LoginStateLookupInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 LoginStateLookupInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputLoginStateLookupInfo.toStackBean(
                    JSON.parseObject(json, WebInputLoginStateLookupInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 LoginStateLookupInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputLoginStateLookupInfo.toStackBean(
                        JSON.parseObject(in, WebInputLoginStateLookupInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 登录状态查询。
        LoginStateLookupResult result = loginStateLookupQosService.lookup(info);

        // 输出结果。
        if (Objects.isNull(result)) {
            context.sendMessage("查询结果: null");
        } else {
            List<LoginState> loginStates = result.getLoginStates();
            if (Objects.isNull(loginStates)) {
                context.sendMessage("查询结果: ");
                context.sendMessage("  loginStates: null");
            } else {
                processLoginStateList(context, loginStates);
            }
        }
    }

    private void processLoginStateList(Context context, List<LoginState> loginStates) throws Exception {
        while (true) {
            CommandUtil.CropResult cropResult = CommandUtil.cropData(
                    context, loginStates, "数据总数: " + loginStates.size(), "输入 q 退出"
            );
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                LoginState loginState = loginStates.get(i);
                printLoginState(context, i, cropResult.getEndIndex(), loginState);
            }
        }
    }

    private void printLoginState(Context context, int i, int endIndex, LoginState loginState) throws Exception {
        context.sendMessage(String.format("索引: %d/%d", i, endIndex));
        if (Objects.isNull(loginState)) {
            context.sendMessage("null");
        } else {
            context.sendMessage("  loginStateKey: " + loginState.getKey());
            context.sendMessage("  accountKey: " + loginState.getAccountKey());
            context.sendMessage("  expireDate: " + loginState.getExpireDate());
            context.sendMessage("  serialVersion: " + loginState.getSerialVersion());
            context.sendMessage("  generatedDate: " + loginState.getGeneratedDate());
            context.sendMessage("  type: " + loginState.getType());
            context.sendMessage("  remark: " + loginState.getRemark());
        }
        context.sendMessage("");
    }
}
