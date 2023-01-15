package com.dwarfeng.acckeeper.impl.service.telqos;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class KickCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(KickCommand.class);

    private static final String COMMAND_OPTION_KICK_FOR_ID = "i";
    private static final String COMMAND_OPTION_KICK_FOR_ACCOUNT = "n";
    private static final String COMMAND_OPTION_KICK_ALL = "a";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_KICK_FOR_ID,
            COMMAND_OPTION_KICK_FOR_ACCOUNT,
            COMMAND_OPTION_KICK_ALL
    };

    private static final String IDENTITY = "kick";
    private static final String DESCRIPTION = "用户登出";
    private static final String CMD_LINE_SYNTAX_KICK_FOR_ID = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_KICK_FOR_ID) + " login-state-id";
    private static final String CMD_LINE_SYNTAX_KICK_FOR_NAME = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_KICK_FOR_ACCOUNT) + " account-id";
    private static final String CMD_LINE_SYNTAX_KICK_ALL = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_KICK_ALL);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_KICK_FOR_ID,
            CMD_LINE_SYNTAX_KICK_FOR_NAME,
            CMD_LINE_SYNTAX_KICK_ALL
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final LoginService loginService;
    private final LoginStateMaintainService loginStateMaintainService;

    public KickCommand(
            LoginService loginService,
            LoginStateMaintainService loginStateMaintainService
    ) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.loginService = loginService;
        this.loginStateMaintainService = loginStateMaintainService;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_KICK_FOR_ID).optionalArg(true).type(Number.class).hasArg(true)
                .desc("登录状态 ID").build());
        list.add(Option.builder(COMMAND_OPTION_KICK_FOR_ACCOUNT).optionalArg(true).type(String.class).hasArg(true)
                .desc("登录账号 ID").build());
        list.add(Option.builder(COMMAND_OPTION_KICK_ALL).optionalArg(true).hasArg(false).desc("全部已登录实例")
                .build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
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
                case COMMAND_OPTION_KICK_FOR_ID:
                    kickForId(context, cmd);
                    break;
                case COMMAND_OPTION_KICK_FOR_ACCOUNT:
                    kickForName(context, cmd);
                    break;
                case COMMAND_OPTION_KICK_ALL:
                    kickAll(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void kickForId(Context context, CommandLine cmd) throws Exception {
        LongIdKey key = new LongIdKey(((Number) cmd.getParsedOptionValue(COMMAND_OPTION_KICK_FOR_ID)).longValue());
        List<LoginState> loginStates;
        if (loginStateMaintainService.exists(key)) {
            loginStates = Collections.singletonList(loginStateMaintainService.get(key));
        } else {
            loginStates = Collections.emptyList();
        }
        processLoginStateList(context, loginStates);
    }

    private void kickForName(Context context, CommandLine cmd) throws Exception {
        StringIdKey accountKey = new StringIdKey((String) cmd.getParsedOptionValue(COMMAND_OPTION_KICK_FOR_ACCOUNT));
        List<LoginState> loginStates = loginStateMaintainService.lookupAsList(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT, new Object[]{accountKey}
        );
        processLoginStateList(context, loginStates);
    }

    private void kickAll(Context context) throws Exception {
        List<LoginState> loginStates = loginStateMaintainService.lookupAsList();
        processLoginStateList(context, loginStates);
    }

    private void processLoginStateList(Context context, List<LoginState> loginStates) throws Exception {
        int successCount = 0;
        for (LoginState loginState : loginStates) {
            try {
                loginService.logout(loginState.getKey());
                successCount++;
            } catch (Exception e) {
                LOGGER.warn("ID " + loginState.getKey().getLongId() + " 登出失败, 异常信息如下: ", e);
            }
        }
        context.sendMessage(String.format("登出 %d/%d", successCount, loginStates.size()));
    }
}
