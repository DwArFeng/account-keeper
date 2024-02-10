package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicLoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticLoginInfo;
import com.dwarfeng.acckeeper.stack.exception.ProtectorException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保护器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface Protector {

    /**
     * 执行保护动作。
     *
     * @param context 保护器上下文。
     * @return 保护响应。
     * @throws ProtectorException 保护器异常。
     */
    Response execProtect(Context context) throws ProtectorException;

    /**
     * 重置保护器。
     *
     * @param context 保护器上下文。
     * @throws ProtectorException 保护器异常。
     */
    void resetProtect(Context context) throws ProtectorException;

    /**
     * 上下文。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    interface Context {

        /**
         * 获取账户的元数据。
         *
         * @return 账户的元数据。
         * @throws ProtectorException 保护器异常。
         */
        AccountMeta getAccountMeta() throws ProtectorException;

        /**
         * 获取本次登录的登录信息。
         *
         * <p>
         * 该方法已经被废弃，新版本中使用 {@link #isDynamicLogin()} 或者 {@link #isStaticLogin()}。
         *
         * @return 本次登录的登录信息。
         * @throws ProtectorException 保护器异常。
         * @deprecated 使用 {@link #isDynamicLogin()} 或者 {@link #isStaticLogin()}。
         */
        @Deprecated
        LoginInfo getLoginInfo() throws ProtectorException;

        /**
         * 获取本次登录是否为动态登录。
         *
         * @return 本次登录是否为动态登录。
         * @throws ProtectorException 保护器异常。
         * @since 1.7.0
         */
        boolean isDynamicLogin() throws ProtectorException;

        /**
         * 获取动态登录信息。
         *
         * <p>
         * 该方法在 @{link #isDynamicLogin()} 返回 true 时有效。
         *
         * @return 动态登录信息。
         * @throws ProtectorException 保护器异常。
         * @since 1.7.0
         */
        DynamicLoginInfo getDynamicLoginInfo() throws ProtectorException;

        /**
         * 获取本次登录是否为静态登录。
         *
         * @return 本次登录是否为静态登录。
         * @throws ProtectorException 保护器异常。
         * @since 1.7.0
         */
        boolean isStaticLogin() throws ProtectorException;

        /**
         * 获取静态登录信息。
         *
         * <p>
         * 该方法在 @{link #isStaticLogin()} 返回 true 时有效。
         *
         * @return 静态登录信息。
         * @throws ProtectorException 保护器异常。
         * @since 1.7.0
         */
        StaticLoginInfo getStaticLoginInfo() throws ProtectorException;

        /**
         * 查询登录响应。
         *
         * <p>
         * 查询的结果根据发生时间降序排列。
         *
         * @param param 查询参数。
         * @return 登录响应查询结果。
         * @throws ProtectorException 保护器异常。
         */
        List<LoginRecord> inspectRecord(RecordInspectParam param) throws ProtectorException;

        /**
         * 是否存在指定的变量。
         *
         * @param variableId 变量 ID。
         * @return 指定的变量是否存在。
         * @throws ProtectorException 保护器异常。
         */
        boolean existsVariable(String variableId) throws ProtectorException;

        /**
         * 获取指定的变量。
         *
         * @param variableId 变量 ID。
         * @return 指定的变量。
         * @throws ProtectorException 保护器异常。
         */
        Variable getVariable(String variableId) throws ProtectorException;

        /**
         * 设置指定的变量。
         *
         * @param variableId 变量 ID。
         * @param variable   变量。
         * @throws ProtectorException 保护器异常。
         */
        void setVariable(String variableId, Variable variable) throws ProtectorException;

        /**
         * 移除指定的变量。
         *
         * @param variableId 变量 ID。
         * @throws ProtectorException 保护器异常。
         */
        void removeVariable(String variableId) throws ProtectorException;

        /**
         * 获取本次登录是否密码正确。
         *
         * @return 本次登录是否密码正确。
         * @throws ProtectorException 保护器异常。
         */
        boolean passwordCorrect() throws ProtectorException;
    }

    /**
     * 账户元数据结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    final class AccountMeta {

        private final long serialVersion;
        private final Date registeredDate;
        private final int loginCount;
        private final int passwordUpdateCount;

        /**
         * @since 1.7.1
         */
        private final int deriveCount;

        public AccountMeta(
                long serialVersion, Date registeredDate, int loginCount, int passwordUpdateCount, int deriveCount
        ) {
            this.serialVersion = serialVersion;
            this.registeredDate = registeredDate;
            this.loginCount = loginCount;
            this.passwordUpdateCount = passwordUpdateCount;
            this.deriveCount = deriveCount;
        }

        public long getSerialVersion() {
            return serialVersion;
        }

        public Date getRegisteredDate() {
            return registeredDate;
        }

        public int getLoginCount() {
            return loginCount;
        }

        public int getPasswordUpdateCount() {
            return passwordUpdateCount;
        }

        public int getDeriveCount() {
            return deriveCount;
        }

        @Override
        public String toString() {
            return "AccountMeta{" +
                    "serialVersion=" + serialVersion +
                    ", registeredDate=" + registeredDate +
                    ", loginCount=" + loginCount +
                    ", passwordUpdateCount=" + passwordUpdateCount +
                    ", deriveCount=" + deriveCount +
                    '}';
        }
    }

    /**
     * 查询参数结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    final class RecordInspectParam {

        /**
         * 查询的起始时间，为 null 代表没有起始时间（将时间戳 0 对应的时间传入查询接口）。
         */
        private final Date startDate;

        /**
         * 查询的中止时间，为 null 代表没有结束时间（将当前时间传入查询接口）。
         */
        private final Date endDate;

        /**
         * 响应代码的值空间。
         *
         * <p>
         * 该参数为 null 时，查询时忽略响应空间。<br>
         * 该参数不为 null 时，只查询响应代码在该空间内的登录响应。<br>
         * 特殊地，当该值为一个空列表时，整个查询将不会返回任何结果。
         */
        private final List<Integer> responseCodes;

        /**
         * 最大结果。
         *
         * <p>
         * 查询时按照发生时间倒序进行查询，数量到达最大结果时中断查询，返回结果。
         */
        private final Integer maxResult;

        public RecordInspectParam(Date startDate, Date endDate, List<Integer> responseCodes, Integer maxResult) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.responseCodes = responseCodes;
            this.maxResult = maxResult;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public List<Integer> getResponseCodes() {
            return responseCodes;
        }

        public Integer getMaxResult() {
            return maxResult;
        }

        @Override
        public String toString() {
            return "RecordInspectParam{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", responseCodes=" + responseCodes +
                    ", maxResult=" + maxResult +
                    '}';
        }
    }

    /**
     * 登录记录结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    final class LoginRecord {

        private final String accountId;
        private final Date happenedDate;
        private final int responseCode;
        private final String message;
        private final Integer alarmLevel;
        private final Map<String, String> extraParamMap;
        private final Map<String, String> protectDetailMap;

        public LoginRecord(
                String accountId, Date happenedDate, int responseCode, String message, Integer alarmLevel,
                Map<String, String> extraParamMap, Map<String, String> protectDetailMap
        ) {
            this.accountId = accountId;
            this.happenedDate = happenedDate;
            this.responseCode = responseCode;
            this.message = message;
            this.alarmLevel = alarmLevel;
            this.extraParamMap = extraParamMap;
            this.protectDetailMap = protectDetailMap;
        }

        public String getAccountId() {
            return accountId;
        }

        public Date getHappenedDate() {
            return happenedDate;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getMessage() {
            return message;
        }

        public Integer getAlarmLevel() {
            return alarmLevel;
        }

        public Map<String, String> getExtraParamMap() {
            return extraParamMap;
        }

        public Map<String, String> getProtectDetailMap() {
            return protectDetailMap;
        }

        @Override
        public String toString() {
            return "LoginRecord{" +
                    "accountId='" + accountId + '\'' +
                    ", happenedDate=" + happenedDate +
                    ", responseCode=" + responseCode +
                    ", message='" + message + '\'' +
                    ", alarmLevel=" + alarmLevel +
                    ", extraParamMap=" + extraParamMap +
                    ", protectDetailMap=" + protectDetailMap +
                    '}';
        }
    }

    final class Variable {

        private final String value;
        private final String remark;

        public Variable(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public String getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }

        @Override
        public String toString() {
            return "Variable{" +
                    "value='" + value + '\'' +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }

    /**
     * 响应结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    final class Response {

        private final boolean passed;
        private final String message;
        private final int alarmLevel;
        private final Map<String, String> protectDetail;

        public Response(boolean passed, String message, int alarmLevel, Map<String, String> protectDetail) {
            this.passed = passed;
            this.message = message;
            this.alarmLevel = alarmLevel;
            this.protectDetail = protectDetail;
        }

        public boolean isPassed() {
            return passed;
        }

        public String getMessage() {
            return message;
        }

        public int getAlarmLevel() {
            return alarmLevel;
        }

        public Map<String, String> getProtectDetail() {
            return protectDetail;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "passed=" + passed +
                    ", message='" + message + '\'' +
                    ", alarmLevel=" + alarmLevel +
                    ", protectDetailMap=" + protectDetail +
                    '}';
        }
    }
}
