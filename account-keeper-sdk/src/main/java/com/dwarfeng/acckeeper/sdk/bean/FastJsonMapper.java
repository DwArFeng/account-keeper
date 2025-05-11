package com.dwarfeng.acckeeper.sdk.bean;

import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * <p>
 * 该映射器中的实体类型不全面，仅包含 <code>FastJson</code> 类实体，因此使用 {@link BeanMapper} 代替。
 *
 * @author DwArFeng
 * @see BeanMapper
 * @since 1.5.0
 * @deprecated 使用 {@link BeanMapper} 代替。
 */
// 基于 MapStruct Processor 生成的实现类还在使用该接口，故忽略相关警告。
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    FastJsonProtectorVariableKey protectorVariableKeyToFastJson(ProtectorVariableKey protectorVariableKey);

    FastJsonRecordKey recordKeyToFastJson(RecordKey recordKey);

    @InheritInverseConfiguration
    RecordKey recordKeyFromFastJson(FastJsonRecordKey fastJsonRecordKey);

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

    FastJsonLoginParamRecord loginParamRecordToFastJson(LoginParamRecord loginParamRecord);

    @InheritInverseConfiguration
    LoginParamRecord loginParamRecordFromFastJson(FastJsonLoginParamRecord fastJsonLoginParamRecord);

    FastJsonProtectDetailRecord protectDetailRecordToFastJson(ProtectDetailRecord protectDetailRecord);

    @InheritInverseConfiguration
    ProtectDetailRecord protectDetailRecordFromFastJson(FastJsonProtectDetailRecord fastJsonProtectDetailRecord);

    FastJsonDeriveHistory deriveHistoryToFastJson(DeriveHistory deriveHistory);

    @InheritInverseConfiguration
    DeriveHistory deriveHistoryFromFastJson(FastJsonDeriveHistory fastJsonDeriveHistory);
}
