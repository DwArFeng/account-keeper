package com.dwarfeng.acckeeper.impl.service;

import com.dwarfeng.acckeeper.stack.handler.LoginStateKeyGenerateHandler;
import com.dwarfeng.acckeeper.stack.service.LoginStateKeyGenerateQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginStateKeyGenerateQosServiceImpl implements LoginStateKeyGenerateQosService {

    private final LoginStateKeyGenerateHandler loginStateKeyGenerateHandler;

    private final ServiceExceptionMapper sem;

    public LoginStateKeyGenerateQosServiceImpl(
            LoginStateKeyGenerateHandler loginStateKeyGenerateHandler,
            ServiceExceptionMapper sem
    ) {
        this.loginStateKeyGenerateHandler = loginStateKeyGenerateHandler;
        this.sem = sem;
    }

    @Override
    public StringIdKey generate() throws ServiceException {
        try {
            return loginStateKeyGenerateHandler.generate();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成登录状态主键时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public List<StringIdKey> generate(int size) throws ServiceException {
        try {
            List<StringIdKey> lsks = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                lsks.add(loginStateKeyGenerateHandler.generate());
            }
            return lsks;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("生成登录状态主键时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
