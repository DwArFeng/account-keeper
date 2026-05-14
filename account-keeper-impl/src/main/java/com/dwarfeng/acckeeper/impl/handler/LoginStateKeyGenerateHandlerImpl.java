package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.handler.LoginStateKeyGenerateHandler;
import com.dwarfeng.acckeeper.stack.handler.LoginStateKeyGenerator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class LoginStateKeyGenerateHandlerImpl implements LoginStateKeyGenerateHandler {

    private final List<LoginStateKeyGenerator> loginStateKeyGenerators;

    @Value("${com.dwarfeng.acckeeper.lskgen.type}")
    private String loginStateKeyGeneratorType;

    private LoginStateKeyGenerator loginStateKeyGenerator;

    public LoginStateKeyGenerateHandlerImpl(List<LoginStateKeyGenerator> loginStateKeyGenerators) {
        this.loginStateKeyGenerators = Optional.ofNullable(loginStateKeyGenerators).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.loginStateKeyGenerator = loginStateKeyGenerators.stream()
                .filter(p -> p.supportType(loginStateKeyGeneratorType)).findAny()
                .orElseThrow(
                        () -> new HandlerException("未知的 loginStateKeyGenerator 类型: " + loginStateKeyGeneratorType)
                );
    }

    @Override
    public StringIdKey generate() throws HandlerException {
        return loginStateKeyGenerator.generate();
    }
}
