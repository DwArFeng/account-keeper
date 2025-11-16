package com.dwarfeng.acckeeper.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputDynamicDeriveInfo;
import com.dwarfeng.acckeeper.sdk.bean.dto.WebInputStaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.acckeeper.stack.service.DeriveQosService;
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

@TelqosCommand
public class DeriveCommand extends CliCommand {

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

    private static final String IDENTITY = "derive";
    private static final String DESCRIPTION = "派生服务";

    private static final String CMD_LINE_SYNTAX_DYNAMIC_DERIVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_DYNAMIC_DERIVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
    private static final String CMD_LINE_SYNTAX_STATIC_DERIVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATIC_DERIVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

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

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_DYNAMIC_DERIVE).longOpt(COMMAND_OPTION_DYNAMIC_DERIVE_LONG_OPT)
                        .desc("动态派生").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_STATIC_DERIVE).longOpt(COMMAND_OPTION_STATIC_DERIVE_LONG_OPT)
                        .desc("静态派生").build()
        );
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
                case COMMAND_OPTION_DYNAMIC_DERIVE:
                    handleDynamicDerive(context, cmd);
                    break;
                case COMMAND_OPTION_STATIC_DERIVE:
                    handleStaticDerive(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDynamicDerive(Context context, CommandLine cmd) throws Exception {
        DynamicDeriveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 DynamicDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputDynamicDeriveInfo.toStackBean(
                    JSON.parseObject(json, WebInputDynamicDeriveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 DynamicDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputDynamicDeriveInfo.toStackBean(
                        JSON.parseObject(in, WebInputDynamicDeriveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用动态派生服务。
        DynamicDeriveResult result = deriveQosService.dynamicDerive(info);

        // 输出结果。
        context.sendMessage("动态派生结果: ");
        context.sendMessage("  loginStateKey: " + result.getLoginStateKey());
        context.sendMessage("  accountKey: " + result.getAccountKey().getStringId());
        context.sendMessage("  expireDate: " + result.getExpireDate());
        context.sendMessage("  generatedDate: " + result.getGeneratedDate());
        context.sendMessage("  type: " + result.getType());
        context.sendMessage("  remark: " + result.getRemark());
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleStaticDerive(Context context, CommandLine cmd) throws Exception {
        StaticDeriveInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 StaticDeriveInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            info = WebInputStaticDeriveInfo.toStackBean(
                    JSON.parseObject(json, WebInputStaticDeriveInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 StaticDeriveInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputStaticDeriveInfo.toStackBean(
                        JSON.parseObject(in, WebInputStaticDeriveInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 调用静态派生服务。
        StaticDeriveResult result = deriveQosService.staticDerive(info);

        // 输出结果。
        context.sendMessage("静态派生结果: ");
        context.sendMessage("  loginStateKey: " + result.getLoginStateKey());
        context.sendMessage("  accountKey: " + result.getAccountKey().getStringId());
        context.sendMessage("  expireDate: " + result.getExpireDate());
        context.sendMessage("  generatedDate: " + result.getGeneratedDate());
        context.sendMessage("  type: " + result.getType());
        context.sendMessage("  remark: " + result.getRemark());
    }
}
