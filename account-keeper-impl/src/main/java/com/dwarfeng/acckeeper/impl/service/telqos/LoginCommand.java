package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginCommand extends CliCommand {

    private static final String COMMAND_OPTION_ACCOUNT_ID = "a";
    private static final String COMMAND_OPTION_PASSWORD = "p";
    private static final String COMMAND_OPTION_IP_ADDRESS = "ip";

    private static final String IDENTITY = "login";
    private static final String DESCRIPTION = "用户登陆服务，仅用于测试！测试完成后必须立即更改密码！";
    private static final String CMD_LINE_SYNTAX = String.format(
            "%s [%s account-id] [%s password] [%s ip-address]",
            IDENTITY,
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ACCOUNT_ID),
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_PASSWORD),
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_IP_ADDRESS)
    );

    private final LoginQosService loginQosService;

    public LoginCommand(LoginQosService loginQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginQosService = loginQosService;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_ACCOUNT_ID).optionalArg(true).type(String.class).hasArg(true)
                .desc("用户名").build());
        list.add(Option.builder(COMMAND_OPTION_PASSWORD).optionalArg(true).type(String.class).hasArg(true)
                .desc("密码").build());
        list.add(Option.builder(COMMAND_OPTION_IP_ADDRESS).optionalArg(true).type(String.class).hasArg(true)
                .desc("用于测试的 IP 地址, 如不指定该选项则取 telnet 客户端的 IP 地址").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
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

            String ipAddress = context.getAddress();
            if (cmd.hasOption(COMMAND_OPTION_IP_ADDRESS)) {
                ipAddress = (String) cmd.getParsedOptionValue(COMMAND_OPTION_IP_ADDRESS);
            }

            try {
                LoginState loginState = loginQosService.login(
                        new LoginInfo(new StringIdKey(accountId), password, ipAddress)
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
}
