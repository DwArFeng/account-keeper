package com.dwarfeng.acckeeper.sdk.bean.entity;

import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    FastJsonProtectorVariableKey protectorVariableKeyToFastJson(ProtectorVariableKey protectorVariableKey);

    @InheritInverseConfiguration
    ProtectorVariableKey protectorVariableKeyFromFastJson(FastJsonProtectorVariableKey fastJsonProtectorVariableKey);

    FastJsonAccount accountToFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromFastJson(FastJsonAccount fastJsonAccount);

    FastJsonLoginState loginStateToFastJson(LoginState loginState);

    @InheritInverseConfiguration
    LoginState loginStateFromFastJson(FastJsonLoginState fastJsonLoginState);

    FastJsonLoginHistory loginHistoryToFastJson(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromFastJson(FastJsonLoginHistory fastJsonLoginHistory);

    FastJsonProtectorInfo protectorInfoToFastJson(ProtectorInfo protectorInfo);

    @InheritInverseConfiguration
    ProtectorInfo protectorInfoFromFastJson(FastJsonProtectorInfo fastJsonProtectorInfo);

    FastJsonProtectorSupport protectorSupportToFastJson(ProtectorSupport protectorSupport);

    @InheritInverseConfiguration
    ProtectorSupport protectorSupportFromFastJson(FastJsonProtectorSupport fastJsonProtectorSupport);

    FastJsonProtectorVariable protectorVariableToFastJson(ProtectorVariable protectorVariable);

    @InheritInverseConfiguration
    ProtectorVariable protectorVariableFromFastJson(FastJsonProtectorVariable fastJsonProtectorVariable);
}
