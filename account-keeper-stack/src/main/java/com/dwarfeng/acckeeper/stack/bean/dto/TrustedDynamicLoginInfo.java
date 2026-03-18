package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Map;

/**
 * 可信动态登录信息。
 *
 * <p>
 * 用于与第三方登录系统（如 OAuth2、SAML、企业 SSO）集成时的登录场景。<br>
 * 当用户已通过外部身份提供者完成认证后，调用方可将账户标识等信息传入本 DTO，
 * 系统将信任该认证结果，跳过密码校验直接创建登录状态。<br>
 * 典型使用场景包括：OAuth2 授权码流程回调、SAML 断言消费、企业统一身份认证等。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class TrustedDynamicLoginInfo implements Dto {

    private static final long serialVersionUID = -6112100337914192426L;

    private StringIdKey accountKey;
    private String remark;
    private Map<String, String> extraParamMap;

    public TrustedDynamicLoginInfo() {
    }

    public TrustedDynamicLoginInfo(
            StringIdKey accountKey, String remark, Map<String, String> extraParamMap
    ) {
        this.accountKey = accountKey;
        this.remark = remark;
        this.extraParamMap = extraParamMap;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
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
        return "TrustedDynamicLoginInfo{" +
                "accountKey=" + accountKey +
                ", remark='" + remark + '\'' +
                ", extraParamMap=" + extraParamMap +
                '}';
    }
}
