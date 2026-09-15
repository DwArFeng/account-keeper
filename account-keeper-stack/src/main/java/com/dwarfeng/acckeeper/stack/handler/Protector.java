package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.subgrade.basic.stack.bean.key.StringIdKey;
import org.jetbrains.annotations.NotNull;

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
     * 动态登录信息（不含密码）。
     *
     * @author DwArFeng
     * @since 2.1.0
     */
    record DynamicLoginInfo(StringIdKey accountKey, String remark, Map<String, String> extraParamMap) {

        @Override
        public @NotNull String toString() {
            return "DynamicLoginInfo{" +
                    "accountKey=" + accountKey +
                    ", remark='" + remark + '\'' +
                    ", extraParamMap=" + extraParamMap +
                    '}';
        }
    }

    /**
     * 静态登录信息（不含密码）。
     *
     * @author DwArFeng
     * @since 2.1.0
     */
    record StaticLoginInfo(
            StringIdKey accountKey, Date expireDate, String remark, Map<String, String> extraParamMap
    ) {

        @Override
        public @NotNull String toString() {
            return "StaticLoginInfo{" +
                    "accountKey=" + accountKey +
                    ", expireDate=" + expireDate +
                    ", remark='" + remark + '\'' +
                    ", extraParamMap=" + extraParamMap +
                    '}';
        }
    }

    /**
     * 账户元数据结构体。
     *
     * @param deriveCount
     * @author DwArFeng
     * @since 1.6.0
     */
    record AccountMeta(
            long serialVersion, Date registeredDate, int loginCount, int passwordUpdateCount, int deriveCount
    ) {

        @Override
        public @NotNull String toString() {
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
     * @param startDate     查询的起始时间，为 null 代表没有起始时间（将时间戳 0 对应的时间传入查询接口）。
     * @param endDate       查询的中止时间，为 null 代表没有结束时间（将当前时间传入查询接口）。
     * @param responseCodes 响应代码的值空间。
     *
     *                      <p>
     *                      该参数为 null 时，查询时忽略响应空间。<br>
     *                      该参数不为 null 时，只查询响应代码在该空间内的登录响应。<br>
     *                      特殊地，当该值为一个空列表时，整个查询将不会返回任何结果。
     * @param maxResult     最大结果。
     *
     *                      <p>
     *                      查询时按照发生时间倒序进行查询，数量到达最大结果时中断查询，返回结果。
     * @author DwArFeng
     * @since 1.6.0
     */
    record RecordInspectParam(Date startDate, Date endDate, List<Integer> responseCodes, Integer maxResult) {

        @Override
        public @NotNull String toString() {
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
    record LoginRecord(
            String accountId, Date happenedDate, int responseCode, String message, Integer alarmLevel,
            Map<String, String> extraParamMap, Map<String, String> protectDetailMap
    ) {

        @Override
        public @NotNull String toString() {
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

    record Variable(String value, String remark) {

        @Override
        public @NotNull String toString() {
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
    record Response(boolean passed, String message, int alarmLevel, Map<String, String> protectDetail) {

        @Override
        public @NotNull String toString() {
            return "Response{" +
                    "passed=" + passed +
                    ", message='" + message + '\'' +
                    ", alarmLevel=" + alarmLevel +
                    ", protectDetailMap=" + protectDetail +
                    '}';
        }
    }
}
