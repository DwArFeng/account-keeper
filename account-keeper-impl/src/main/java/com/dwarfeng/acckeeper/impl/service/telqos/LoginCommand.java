package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
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
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@TelqosCommand
public class LoginCommand extends CliCommand {

    private static final String COMMAND_OPTION_ACCOUNT_ID = "a";
    private static final String COMMAND_OPTION_PASSWORD = "p";
    private static final String COMMAND_OPTION_EXTRA_PARAMS = "e";

    private static final String IDENTITY = "login";
    private static final String DESCRIPTION = "用户登陆服务，仅用于测试！测试完成后必须立即更改密码！";
    private static final String CMD_LINE_SYNTAX = String.format(
            "%s [%s account-id] [%s password] [%s]",
            IDENTITY,
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ACCOUNT_ID),
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_PASSWORD),
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_EXTRA_PARAMS)
    );

    private final LoginQosService loginQosService;

    public LoginCommand(LoginQosService loginQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginQosService = loginQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_ACCOUNT_ID).optionalArg(true).type(String.class).hasArg(true)
                .desc("用户名").build());
        list.add(Option.builder(COMMAND_OPTION_PASSWORD).optionalArg(true).type(String.class).hasArg(true)
                .desc("密码").build());
        list.add(Option.builder(COMMAND_OPTION_EXTRA_PARAMS).optionalArg(true).desc("额外参数").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            String accountId;
            if (cmd.hasOption(COMMAND_OPTION_ACCOUNT_ID)) {
                accountId = (String) cmd.getParsedOptionValue(COMMAND_OPTION_ACCOUNT_ID);
            } else {
                context.sendMessage("请输入账户 ID: ");
                accountId = context.receiveMessage();
            }

            String password;
            if (cmd.hasOption(COMMAND_OPTION_PASSWORD)) {
                password = (String) cmd.getParsedOptionValue(COMMAND_OPTION_PASSWORD);
            } else {
                context.sendMessage("请输入密码: ");
                password = context.receiveMessage();
            }

            Map<String, String> extraParamMap;
            if (cmd.hasOption(COMMAND_OPTION_EXTRA_PARAMS)) {
                extraParamMap = receiveExtraParamMap(context);
            } else {
                extraParamMap = Collections.emptyMap();
            }

            try {
                LoginState loginState = loginQosService.login(
                        new LoginInfo(new StringIdKey(accountId), password, extraParamMap)
                );
                String token = Long.toString(loginState.getKey().getLongId());
                String expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginState.getExpireDate());
                context.sendMessage(
                        "登陆成功! token: " + token + ", 过期时间: " + expiredDate
                );
            } catch (ServiceException e) {
                String code = Integer.toString(e.getCode().getCode());
                String tip = e.getCode().getTip();
                context.sendMessage(
                        "登陆失败! 异常代码: " + code + ", 异常提示: " + tip
                );
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private static final String CONTROL_STRING_NEXT = "N";
    private static final String CONTROL_STRING_QUIT = "Q";

    private Map<String, String> receiveExtraParamMap(Context context) throws Exception {
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
                if (StringUtils.equalsIgnoreCase(controlString.toUpperCase(), CONTROL_STRING_NEXT)) {
                    mismatchFlag = false;
                } else if (StringUtils.equalsIgnoreCase(controlString.toUpperCase(), CONTROL_STRING_QUIT)) {
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
}
