package com.dwarfeng.acckeeper.sdk.handler.lskgen;

import com.dwarfeng.acckeeper.stack.handler.LoginStateKeyGenerator;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Objects;

/**
 * 抽象登录状态主键生成器。
 *
 * <p>
 * 登录状态主键生成器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractLoginStateKeyGenerator implements LoginStateKeyGenerator {

    protected String loginStateKeyGenerateType;

    public AbstractLoginStateKeyGenerator() {
    }

    public AbstractLoginStateKeyGenerator(String loginStateKeyGenerateType) {
        this.loginStateKeyGenerateType = loginStateKeyGenerateType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(loginStateKeyGenerateType, type);
    }

    @Override
    public StringIdKey generate() throws HandlerException {
        try {
            return doGenerate();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    /**
     * 生成登录状态主键。
     *
     * <p>
     * 该方法需要尽量保证生成的登录状态主键的唯一性，在可能的情况下，生成的登录状态主键不应当重复。
     *
     * <p>
     * 在极端情况下，生成的登录状态主键允许以低概率重复，如果发生重复，系统会继续调用该方法重新生成，
     * 直到生成不重复的登录状态主键为止。
     *
     * @return 生成的登录状态主键。
     * @throws Exception 执行过程中抛出的任何异常。
     * @see #generate()
     */
    protected abstract StringIdKey doGenerate() throws Exception;

    public String getLoginStateKeyGenerateType() {
        return loginStateKeyGenerateType;
    }

    public void setLoginStateKeyGenerateType(String loginStateKeyGenerateType) {
        this.loginStateKeyGenerateType = loginStateKeyGenerateType;
    }

    @Override
    public String toString() {
        return "AbstractLoginStateKeyGenerator{" +
                "loginStateKeyGenerateType='" + loginStateKeyGenerateType + '\'' +
                '}';
    }
}
