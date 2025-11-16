package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 登录状态查询信息。
 *
 * <p>
 * 登录状态查询过程按照以下逻辑进行：<br>
 * <ol>
 *     <li>如果 {@link #loginStateKey} 字段不为 <code>null</code>，则查询指定的登录状态。</li>
 *     <li>否则，如果 {@link #accountKey} 字段不为 <code>null</code>，则查询指定账号下的所有登录状态。</li>
 *     <li>否则，查询所有登录状态。</li>
 * </ol>
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LoginStateLookupInfo implements Dto {

    private static final long serialVersionUID = -1630122144672429679L;

    /**
     * 登录状态主键。
     *
     * <p>
     * 如果该字段不为 <code>null</code>，则表示查询指定的登录状态。
     */
    private StringIdKey loginStateKey;

    /**
     * 账号主键。
     *
     * <p>
     * 如果 {@link #loginStateKey} 字段为 <code>null</code>，且该字段不为 <code>null</code>，
     * 则表示查询指定账号下的所有登录状态。
     */
    private StringIdKey accountKey;

    public LoginStateLookupInfo() {
    }

    public LoginStateLookupInfo(StringIdKey loginStateKey, StringIdKey accountKey) {
        this.loginStateKey = loginStateKey;
        this.accountKey = accountKey;
    }

    public StringIdKey getLoginStateKey() {
        return loginStateKey;
    }

    public void setLoginStateKey(StringIdKey loginStateKey) {
        this.loginStateKey = loginStateKey;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    @Override
    public String toString() {
        return "LoginStateLookupInfo{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
