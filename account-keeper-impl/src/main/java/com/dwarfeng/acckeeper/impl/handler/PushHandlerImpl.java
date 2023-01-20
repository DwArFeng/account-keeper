package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginHistoryRecordInfo;
import com.dwarfeng.acckeeper.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PushHandlerImpl implements PushHandler {

    @Autowired(required = false)
    private List<Pusher> pushers = new ArrayList<>();

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + pusherType));
    }

    @Override
    public void loginHistoryRecorded(LoginHistoryRecordInfo loginHistoryRecordInfo) throws HandlerException {
        pusher.loginHistoryRecorded(loginHistoryRecordInfo);
    }

    @Override
    public void protectReset() throws HandlerException {
        pusher.protectReset();
    }
}
