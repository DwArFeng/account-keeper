package com.dwarfeng.acckeeper.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 清除结束结果。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public class FastJsonPurgeFinishedResult implements Dto {

    private static final long serialVersionUID = -3554449345858890667L;

    public static FastJsonPurgeFinishedResult of(PurgeFinishedResult purgeFinishResult) {
        if (Objects.isNull(purgeFinishResult)) {
            return null;
        } else {
            return new FastJsonPurgeFinishedResult(
                    purgeFinishResult.getDeriveHistoryDeletionCount(),
                    purgeFinishResult.isDeriveHistoryDivergent(),
                    purgeFinishResult.getLoginHistoryDeletionCount(),
                    purgeFinishResult.isLoginHistoryDivergent()
            );
        }
    }

    @JSONField(name = "derive_history_deletion_count", ordinal = 1)
    private int deriveHistoryDeletionCount;

    @JSONField(name = "derive_history_divergent", ordinal = 2)
    private boolean deriveHistoryDivergent;

    @JSONField(name = "login_history_deletion_count", ordinal = 3)
    private int loginHistoryDeletionCount;

    @JSONField(name = "login_history_divergent", ordinal = 4)
    private boolean loginHistoryDivergent;

    public FastJsonPurgeFinishedResult() {
    }

    public FastJsonPurgeFinishedResult(
            int deriveHistoryDeletionCount, boolean deriveHistoryDivergent,
            int loginHistoryDeletionCount, boolean loginHistoryDivergent
    ) {
        this.deriveHistoryDeletionCount = deriveHistoryDeletionCount;
        this.deriveHistoryDivergent = deriveHistoryDivergent;
        this.loginHistoryDeletionCount = loginHistoryDeletionCount;
        this.loginHistoryDivergent = loginHistoryDivergent;
    }

    public int getDeriveHistoryDeletionCount() {
        return deriveHistoryDeletionCount;
    }

    public void setDeriveHistoryDeletionCount(int deriveHistoryDeletionCount) {
        this.deriveHistoryDeletionCount = deriveHistoryDeletionCount;
    }

    public boolean isDeriveHistoryDivergent() {
        return deriveHistoryDivergent;
    }

    public void setDeriveHistoryDivergent(boolean deriveHistoryDivergent) {
        this.deriveHistoryDivergent = deriveHistoryDivergent;
    }

    public int getLoginHistoryDeletionCount() {
        return loginHistoryDeletionCount;
    }

    public void setLoginHistoryDeletionCount(int loginHistoryDeletionCount) {
        this.loginHistoryDeletionCount = loginHistoryDeletionCount;
    }

    public boolean isLoginHistoryDivergent() {
        return loginHistoryDivergent;
    }

    public void setLoginHistoryDivergent(boolean loginHistoryDivergent) {
        this.loginHistoryDivergent = loginHistoryDivergent;
    }

    @Override
    public String toString() {
        return "FastJsonPurgeFinishedResult{" +
                "deriveHistoryDeletionCount=" + deriveHistoryDeletionCount +
                ", deriveHistoryDivergent=" + deriveHistoryDivergent +
                ", loginHistoryDeletionCount=" + loginHistoryDeletionCount +
                ", loginHistoryDivergent=" + loginHistoryDivergent +
                '}';
    }
}
