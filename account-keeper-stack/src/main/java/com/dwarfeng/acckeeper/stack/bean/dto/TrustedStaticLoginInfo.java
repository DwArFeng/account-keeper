package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Map;

/**
 * 可信静态登录信息。
 *
 * <p>
 * 用于与第三方登录系统（如 OAuth2、SAML、企业 SSO）集成时的长期登录场景。<br>
 * 当用户已通过外部身份提供者完成认证后，调用方可将账户标识及期望的过期时间传入本 DTO，
 * 系统将信任该认证结果，跳过密码校验直接创建登录状态。<br>
 * 典型使用场景包括：OAuth2 长期令牌兑换、SAML 单点登录后的持久会话、企业统一身份认证的"记住我"等。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public class TrustedStaticLoginInfo implements Dto {

    private static final long serialVersionUID = -8653710402839637695L;

    private StringIdKey accountKey;
    private Date expireDate;
    private String remark;
    private Map<String, String> extraParamMap;

    public TrustedStaticLoginInfo() {
    }

    public TrustedStaticLoginInfo(
            StringIdKey accountKey, Date expireDate, String remark, Map<String, String> extraParamMap
    ) {
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.remark = remark;
        this.extraParamMap = extraParamMap;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getExtraParamMap() {
        return extraParamMap;
    }

    public void setExtraParamMap(Map<String, String> extraParamMap) {
        this.extraParamMap = extraParamMap;
    }

    @Override
    public String toString() {
        return "TrustedStaticLoginInfo{" +
                "accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
