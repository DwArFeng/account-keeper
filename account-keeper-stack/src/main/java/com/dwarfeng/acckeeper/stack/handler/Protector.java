package com.dwarfeng.acckeeper.stack.handler;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.LoginResponse;
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
     * 响应结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    class Response {

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
                    ", protectDetail=" + protectDetail +
                    '}';
        }
    }

    /**
     * 上下文。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    interface Context {

        /**
         * 获取本次登陆的登陆信息。
         *
         * @return 本次登陆的登陆信息。
         * @throws ProtectorException 保护器异常。
         */
        LoginInfo getLoginInfo() throws ProtectorException;

        /**
         * 查询登陆响应。
         *
         * <p>
         * 查询的结果根据发生时间降序排列。
         *
         * @param param 查询参数。
         * @return 登陆响应查询结果。
         * @throws ProtectorException 保护器异常。
         */
        List<LoginResponse> inspectResponse(ResponseInspectParam param) throws ProtectorException;

        /**
         * 是否存在指定的变量。
         *
         * @param variableId 变量 ID。
         * @return 指定的变量是否存在。
         * @throws ProtectorException 保护器异常。
         */
        boolean existsVariable(String variableId) throws ProtectorException;

        /**
         * 获取指定的变量的值。
         *
         * @param variableId 变量 ID。
         * @return 指定的变量的值。
         * @throws ProtectorException 保护器异常。
         */
        String getVariableValue(String variableId) throws ProtectorException;

        /**
         * 获取指定的变量的备注。
         *
         * @param variableId 变量 ID。
         * @return 指定的变量的备注。
         * @throws ProtectorException 保护器异常。
         */
        String getVariableRemark(String variableId) throws ProtectorException;

        /**
         * 设置指定的变量的值。
         *
         * @param variableId 变量 ID。
         * @param value      变量的值。
         * @throws ProtectorException 保护器异常。
         */
        void setVariableValue(String variableId, String value) throws ProtectorException;

        /**
         * 设置指定的变量的备注。
         *
         * @param variableId 变量 ID。
         * @param remark     指定的变量的备注。
         * @throws ProtectorException 保护器异常。
         */
        void setVariableRemark(String variableId, String remark) throws ProtectorException;
    }

    /**
     * 查询参数结构体。
     *
     * @author DwArFeng
     * @since 1.6.0
     */
    final class ResponseInspectParam {

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
         * 该参数不为 null 时，只查询响应代码在该空间内的登陆响应。<br>
         * 特殊地，当该值为一个空列表时，整个查询将不会返回任何结果。
         */
        private final List<Integer> responseCodes;

        /**
         * 最大结果。
         *
         * <p>
         * 查询时按照发生时间倒序进行查询，数量到达最大结果时中断查询，返回结果。
         */
        private final int maxResult;

        public ResponseInspectParam(Date startDate, Date endDate, List<Integer> responseCodes, int maxResult) {
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

        public int getMaxResult() {
            return maxResult;
        }

        @Override
        public String toString() {
            return "ResponseInspectParam{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", responseCodes=" + responseCodes +
                    ", maxResult=" + maxResult +
                    '}';
        }
    }
}
