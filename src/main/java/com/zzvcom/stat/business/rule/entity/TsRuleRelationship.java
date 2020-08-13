package com.zzvcom.stat.business.rule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author boz
 * @since 2020-05-20
 */
@TableName("ts_rule_relationship")
public class TsRuleRelationship implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 预留字段
     */
    private String ruleCode;

    /**
     * 执行本规则前需要执行完成的规则
     */
    private String beforeRuleCode;

    private String groupCode;

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


    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getBeforeRuleCode() {
        return beforeRuleCode;
    }

    public void setBeforeRuleCode(String beforeRuleCode) {
        this.beforeRuleCode = beforeRuleCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    @Override
    public String toString() {
        return "TsRuleRelationship{" +
        "ruleCode=" + ruleCode +
        ", beforeRuleCode=" + beforeRuleCode +
        ", groupCode=" + groupCode +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
