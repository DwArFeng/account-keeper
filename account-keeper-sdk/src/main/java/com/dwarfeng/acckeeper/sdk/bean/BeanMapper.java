package com.dwarfeng.acckeeper.sdk.bean;

import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.acckeeper.sdk.bean.key.JSFixedFastJsonRecordKey;
import com.dwarfeng.acckeeper.sdk.bean.key.WebInputProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.sdk.bean.key.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@Mapper
public interface BeanMapper {

    // region Subgrade Key

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    WebInputStringIdKey stringIdKeyToWebInput(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromWebInput(WebInputStringIdKey webInputStringIdKey);

    // endregion

    // region Acckeeper Key

    FastJsonProtectorVariableKey protectorVariableKeyToFastJson(ProtectorVariableKey protectorVariableKey);

    @InheritInverseConfiguration
    ProtectorVariableKey protectorVariableKeyFromFastJson(FastJsonProtectorVariableKey fastJsonProtectorVariableKey);

    FastJsonRecordKey recordKeyToFastJson(RecordKey recordKey);

    @InheritInverseConfiguration
    RecordKey recordKeyFromFastJson(FastJsonRecordKey fastJsonRecordKey);

    JSFixedFastJsonRecordKey recordKeyToJSFixedFastJson(RecordKey recordKey);

    @InheritInverseConfiguration
    RecordKey recordKeyFromJSFixedFastJson(JSFixedFastJsonRecordKey jSFixedFastJsonRecordKey);

    WebInputProtectorVariableKey protectorVariableKeyToWebInput(ProtectorVariableKey protectorVariableKey);

    @InheritInverseConfiguration
    ProtectorVariableKey protectorVariableKeyFromWebInput(WebInputProtectorVariableKey webInputProtectorVariableKey);

    // endregion

    // region Acckeeper Entity

    FastJsonAccount accountToFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromFastJson(FastJsonAccount fastJsonAccount);

    FastJsonDeriveHistory deriveHistoryToFastJson(DeriveHistory deriveHistory);

    @InheritInverseConfiguration
    DeriveHistory deriveHistoryFromFastJson(FastJsonDeriveHistory fastJsonDeriveHistory);

    FastJsonLoginHistory loginHistoryToFastJson(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromFastJson(FastJsonLoginHistory fastJsonLoginHistory);

    FastJsonLoginParamRecord loginParamRecordToFastJson(LoginParamRecord loginParamRecord);

    @InheritInverseConfiguration
    LoginParamRecord loginParamRecordFromFastJson(FastJsonLoginParamRecord fastJsonLoginParamRecord);

    FastJsonLoginState loginStateToFastJson(LoginState loginState);

    @InheritInverseConfiguration
    LoginState loginStateFromFastJson(FastJsonLoginState fastJsonLoginState);

    FastJsonProtectDetailRecord protectDetailRecordToFastJson(ProtectDetailRecord protectDetailRecord);

    @InheritInverseConfiguration
    ProtectDetailRecord protectDetailRecordFromFastJson(FastJsonProtectDetailRecord fastJsonProtectDetailRecord);

    FastJsonProtectorInfo protectorInfoToFastJson(ProtectorInfo protectorInfo);

    @InheritInverseConfiguration
    ProtectorInfo protectorInfoFromFastJson(FastJsonProtectorInfo fastJsonProtectorInfo);

    FastJsonProtectorSupport protectorSupportToFastJson(ProtectorSupport protectorSupport);

    @InheritInverseConfiguration
    ProtectorSupport protectorSupportFromFastJson(FastJsonProtectorSupport fastJsonProtectorSupport);

    FastJsonProtectorVariable protectorVariableToFastJson(ProtectorVariable protectorVariable);

    @InheritInverseConfiguration
    ProtectorVariable protectorVariableFromFastJson(FastJsonProtectorVariable fastJsonProtectorVariable);

    JSFixedFastJsonAccount accountToJSFixedFastJson(Account account);

    @InheritInverseConfiguration
    Account accountFromJSFixedFastJson(JSFixedFastJsonAccount jSFixedFastJsonAccount);

    JSFixedFastJsonDeriveHistory deriveHistoryToJSFixedFastJson(DeriveHistory deriveHistory);

    @InheritInverseConfiguration
    DeriveHistory deriveHistoryFromJSFixedFastJson(JSFixedFastJsonDeriveHistory jSFixedFastJsonDeriveHistory);

    JSFixedFastJsonLoginHistory loginHistoryToJSFixedFastJson(LoginHistory loginHistory);

    @InheritInverseConfiguration
    LoginHistory loginHistoryFromJSFixedFastJson(JSFixedFastJsonLoginHistory jSFixedFastJsonLoginHistory);

    JSFixedFastJsonLoginParamRecord loginParamRecordToJSFixedFastJson(LoginParamRecord loginParamRecord);

    @InheritInverseConfiguration
    LoginParamRecord loginParamRecordFromJSFixedFastJson(
            JSFixedFastJsonLoginParamRecord jSFixedFastJsonLoginParamRecord
    );

    JSFixedFastJsonProtectDetailRecord protectDetailRecordToJSFixedFastJson(ProtectDetailRecord protectDetailRecord);

    @InheritInverseConfiguration
    ProtectDetailRecord protectDetailRecordFromJSFixedFastJson(
            JSFixedFastJsonProtectDetailRecord jSFixedFastJsonProtectDetailRecord
    );

    WebInputProtectorInfo protectorInfoToWebInput(ProtectorInfo protectorInfo);

    @InheritInverseConfiguration
    ProtectorInfo protectorInfoFromWebInput(WebInputProtectorInfo webInputProtectorInfo);

    WebInputProtectorVariable protectorVariableToWebInput(ProtectorVariable protectorVariable);

    @InheritInverseConfiguration
    ProtectorVariable protectorVariableFromWebInput(WebInputProtectorVariable webInputProtectorVariable);

    // endregion

    // region Acckeeper DTO

    FastJsonAuthInspectResult authInspectResultToFastJson(AuthInspectResult authInspectResult);

    @InheritInverseConfiguration
    AuthInspectResult authInspectResultFromFastJson(FastJsonAuthInspectResult fastJsonAuthInspectResult);

    FastJsonDeriveHistoryRecordInfo deriveHistoryRecordInfoToFastJson(DeriveHistoryRecordInfo deriveHistoryRecordInfo);

    @InheritInverseConfiguration
    DeriveHistoryRecordInfo deriveHistoryRecordInfoFromFastJson(
            FastJsonDeriveHistoryRecordInfo fastJsonDeriveHistoryRecordInfo
    );

    FastJsonDynamicDeriveResult dynamicDeriveResultToFastJson(DynamicDeriveResult dynamicDeriveResult);

    @InheritInverseConfiguration
    DynamicDeriveResult dynamicDeriveResultFromFastJson(FastJsonDynamicDeriveResult fastJsonDynamicDeriveResult);

    FastJsonDynamicLoginResult dynamicLoginResultToFastJson(DynamicLoginResult dynamicLoginResult);

    @InheritInverseConfiguration
    DynamicLoginResult dynamicLoginResultFromFastJson(FastJsonDynamicLoginResult fastJsonDynamicLoginResult);

    FastJsonLoginHistoryRecordInfo loginHistoryRecordInfoToFastJson(LoginHistoryRecordInfo loginHistoryRecordInfo);

    @InheritInverseConfiguration
    LoginHistoryRecordInfo loginHistoryRecordInfoFromFastJson(
            FastJsonLoginHistoryRecordInfo fastJsonLoginHistoryRecordInfo
    );

    FastJsonPostponeResult postponeResultToFastJson(PostponeResult postponeResult);

    @InheritInverseConfiguration
    PostponeResult postponeResultFromFastJson(FastJsonPostponeResult fastJsonPostponeResult);

    FastJsonPurgeFinishedResult purgeFinishedResultToFastJson(PurgeFinishedResult purgeFinishedResult);

    @InheritInverseConfiguration
    PurgeFinishedResult purgeFinishedResultFromFastJson(FastJsonPurgeFinishedResult fastJsonPurgeFinishedResult);

    FastJsonStaticDeriveResult staticDeriveResultToFastJson(StaticDeriveResult staticDeriveResult);

    @InheritInverseConfiguration
    StaticDeriveResult staticDeriveResultFromFastJson(FastJsonStaticDeriveResult fastJsonStaticDeriveResult);

    FastJsonStaticLoginResult staticLoginResultToFastJson(StaticLoginResult staticLoginResult);

    @InheritInverseConfiguration
    StaticLoginResult staticLoginResultFromFastJson(FastJsonStaticLoginResult fastJsonStaticLoginResult);

    JSFixedFastJsonDeriveHistoryRecordInfo deriveHistoryRecordInfoToJSFixedFastJson(
            DeriveHistoryRecordInfo deriveHistoryRecordInfo
    );

    @InheritInverseConfiguration
    DeriveHistoryRecordInfo deriveHistoryRecordInfoFromJSFixedFastJson(
            JSFixedFastJsonDeriveHistoryRecordInfo jSFixedFastJsonDeriveHistoryRecordInfo
    );

    JSFixedFastJsonLoginHistoryRecordInfo loginHistoryRecordInfoToJSFixedFastJson(
            LoginHistoryRecordInfo loginHistoryRecordInfo
    );

    @InheritInverseConfiguration
    LoginHistoryRecordInfo loginHistoryRecordInfoFromJSFixedFastJson(
            JSFixedFastJsonLoginHistoryRecordInfo jSFixedFastJsonLoginHistoryRecordInfo
    );

    WebInputAccountRegisterInfo accountRegisterInfoToWebInput(AccountRegisterInfo accountRegisterInfo);

    @InheritInverseConfiguration
    AccountRegisterInfo accountRegisterInfoFromWebInput(WebInputAccountRegisterInfo webInputAccountRegisterInfo);

    WebInputAccountUpdateInfo accountUpdateInfoToWebInput(AccountUpdateInfo accountUpdateInfo);

    @InheritInverseConfiguration
    AccountUpdateInfo accountUpdateInfoFromWebInput(WebInputAccountUpdateInfo webInputAccountUpdateInfo);

    WebInputAuthInspectInfo authInspectInfoToWebInput(AuthInspectInfo authInspectInfo);

    @InheritInverseConfiguration
    AuthInspectInfo authInspectInfoFromWebInput(WebInputAuthInspectInfo webInputAuthInspectInfo);

    WebInputDynamicDeriveInfo dynamicDeriveInfoToWebInput(DynamicDeriveInfo dynamicDeriveInfo);

    @InheritInverseConfiguration
    DynamicDeriveInfo dynamicDeriveInfoFromWebInput(WebInputDynamicDeriveInfo webInputDynamicDeriveInfo);

    WebInputDynamicLoginInfo dynamicLoginInfoToWebInput(DynamicLoginInfo dynamicLoginInfo);

    @InheritInverseConfiguration
    DynamicLoginInfo dynamicLoginInfoFromWebInput(WebInputDynamicLoginInfo webInputDynamicLoginInfo);

    WebInputKickInfo kickInfoToWebInput(KickInfo kickInfo);

    @InheritInverseConfiguration
    KickInfo kickInfoFromWebInput(WebInputKickInfo webInputKickInfo);

    WebInputLogoutInfo logoutInfoToWebInput(LogoutInfo logoutInfo);

    @InheritInverseConfiguration
    LogoutInfo logoutInfoFromWebInput(WebInputLogoutInfo webInputLogoutInfo);

    WebInputPasswordCheckInfo passwordCheckInfoToWebInput(PasswordCheckInfo passwordCheckInfo);

    @InheritInverseConfiguration
    PasswordCheckInfo passwordCheckInfoFromWebInput(WebInputPasswordCheckInfo webInputPasswordCheckInfo);

    WebInputPasswordResetInfo passwordResetInfoToWebInput(PasswordResetInfo passwordResetInfo);

    @InheritInverseConfiguration
    PasswordResetInfo passwordResetInfoFromWebInput(WebInputPasswordResetInfo webInputPasswordResetInfo);

    WebInputPasswordUpdateInfo passwordUpdateInfoToWebInput(PasswordUpdateInfo passwordUpdateInfo);

    @InheritInverseConfiguration
    PasswordUpdateInfo passwordUpdateInfoFromWebInput(WebInputPasswordUpdateInfo webInputPasswordUpdateInfo);

    WebInputPostponeInfo postponeInfoToWebInput(PostponeInfo postponeInfo);

    @InheritInverseConfiguration
    PostponeInfo postponeInfoFromWebInput(WebInputPostponeInfo webInputPostponeInfo);

    WebInputStaticDeriveInfo staticDeriveInfoToWebInput(StaticDeriveInfo staticDeriveInfo);

    @InheritInverseConfiguration
    StaticDeriveInfo staticDeriveInfoFromWebInput(WebInputStaticDeriveInfo webInputStaticDeriveInfo);

    WebInputStaticLoginInfo staticLoginInfoToWebInput(StaticLoginInfo staticLoginInfo);

    @InheritInverseConfiguration
    StaticLoginInfo staticLoginInfoFromWebInput(WebInputStaticLoginInfo webInputStaticLoginInfo);

    FastJsonLoginStateLookupResult loginStateLookupResultToFastJson(LoginStateLookupResult loginStateLookupResult);

    @InheritInverseConfiguration
    LoginStateLookupResult loginStateLookupResultFromFastJson(
            FastJsonLoginStateLookupResult fastJsonLoginStateLookupResult
    );

    WebInputLoginStateLookupInfo loginStateLookupInfoToWebInput(LoginStateLookupInfo loginStateLookupInfo);

    @InheritInverseConfiguration
    LoginStateLookupInfo loginStateLookupInfoFromWebInput(WebInputLoginStateLookupInfo webInputLoginStateLookupInfo);

    WebInputTrustedDynamicLoginInfo trustedDynamicLoginInfoToWebInput(TrustedDynamicLoginInfo trustedDynamicLoginInfo);

    @InheritInverseConfiguration
    TrustedDynamicLoginInfo trustedDynamicLoginInfoFromWebInput(
            WebInputTrustedDynamicLoginInfo webInputTrustedDynamicLoginInfo
    );

    WebInputTrustedStaticLoginInfo trustedStaticLoginInfoToWebInput(TrustedStaticLoginInfo trustedStaticLoginInfo);

    @InheritInverseConfiguration
    TrustedStaticLoginInfo trustedStaticLoginInfoFromWebInput(
            WebInputTrustedStaticLoginInfo webInputTrustedStaticLoginInfo
    );

    // endregion
}
