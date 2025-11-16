package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 踢出信息。
 *
 * <p>
 * 踢出过程按照以下逻辑进行：<br>
 * <ol>
 *     <li>如果 {@link #loginStateKey} 字段不为 <code>null</code>，则踢出指定的登录状态。</li>
 *     <li>否则，如果 {@link #accountKey} 字段不为 <code>null</code>，则踢出指定账号下的所有登录状态。</li>
 *     <li>否则，踢出所有登录状态。</li>
 * </ol>
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class KickInfo implements Dto {

    private static final long serialVersionUID = -6564983261303399699L;

    /**
     * 登录状态主键。
     *
     * <p>
     * 如果该字段不为 <code>null</code>，则表示踢出指定的登录状态。
     */
    private StringIdKey loginStateKey;

    /**
     * 账号主键。
     *
     * <p>
     * 如果 {@link #loginStateKey} 字段为 <code>null</code>，且该字段不为 <code>null</code>，
     * 则表示踢出指定账号下的所有登录状态。
     */
    private StringIdKey accountKey;

    public KickInfo() {
    }

    public KickInfo(StringIdKey loginStateKey, StringIdKey accountKey) {
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
        return "KickInfo{" +
                "loginStateKey=" + loginStateKey +
                ", accountKey=" + accountKey +
                '}';
    }
}
