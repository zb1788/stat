package com.zzvcom.stat.business.rule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author boz
 * @date 2020/5/21
 */
public class TsRuleInfo {
    private static final long serialVersionUID=1L;

    /**
     * 规则编码
     */
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 分组编码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 任务类型;p定时任务
     */
    private String taskType;

    /**
     * 任务内容
     */
    private String taskContent;

    /**
     * 能重复执行的最大次数
     */
    private Integer maxTimes;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 执行本规则前需要执行完成的规则
     */
    private String beforeRuleCode;

    private String beforeRuleName;

    private Integer ruleState;

    public Integer getRuleState() {
        return ruleState;
    }

    public void setRuleState(Integer ruleState) {
        this.ruleState = ruleState;
    }

    /**
     * 预留字段
     */
    private String remark1;

    /**
     * 预留字段
     */
    private String remark2;

    /**
     * 预留字段
     */
    private String remark3;


    @Override
    public String toString() {
        return "TsRuleInfo{" +
                "ruleCode='" + ruleCode + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", groupName='" + groupName + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", maxTimes=" + maxTimes +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", beforeRuleCode='" + beforeRuleCode + '\'' +
                ", beforeRuleName='" + beforeRuleName + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Integer getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getBeforeRuleCode() {
        return beforeRuleCode;
    }

    public void setBeforeRuleCode(String beforeRuleCode) {
        this.beforeRuleCode = beforeRuleCode;
    }

    public String getBeforeRuleName() {
        return beforeRuleName;
    }

    public void setBeforeRuleName(String beforeRuleName) {
        this.beforeRuleName = beforeRuleName;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}
