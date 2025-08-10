package com.dwarfeng.acckeeper.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 清除结束结果。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public class PurgeFinishedResult implements Dto {

    private static final long serialVersionUID = 7924186813886907080L;

    /**
     * 派生历史实体的删除数量。
     */
    private int deriveHistoryDeletionCount;

    /**
     * 派生历史是否发散。
     *
     * <p>
     * 发散的意思是，在任务执行间隔中，生成的实体数量大于清除的数量。<br>
     * 如果不处理发散情况，会导致数据的积压。
     */
    private boolean deriveHistoryDivergent;

    /**
     * 登录历史实体的删除数量。
     */
    private int loginHistoryDeletionCount;

    /**
     * 登录历史是否发散。
     *
     * <p>
     * 发散的意思是，在任务执行间隔中，生成的实体数量大于清除的数量。<br>
     * 如果不处理发散情况，会导致数据的积压。
     */
    private boolean loginHistoryDivergent;

    public PurgeFinishedResult() {
    }

    public PurgeFinishedResult(
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
        return "PurgeFinishedResult{" +
                "deriveHistoryDeletionCount=" + deriveHistoryDeletionCount +
                ", deriveHistoryDivergent=" + deriveHistoryDivergent +
                ", loginHistoryDeletionCount=" + loginHistoryDeletionCount +
                ", loginHistoryDivergent=" + loginHistoryDivergent +
                '}';
    }
}
