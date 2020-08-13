package com.zzvcom.stat.business.kfk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author boz
 * @since 2020-05-26
 */
public class DsRule implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 行规则：R,
列规则：C
     */
    private String ruleType;

    /**
     * 规则的英文标识
     */
    private String ruleCode;

    /**
     * 规则中文名称
     */
    private String ruleName;

    /**
     * 1可用,0停用
     */
    private String status;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;

    /**
     * 规则实现对象
     */
    private String ruleHandle;

    /**
     * 供处理用数据格式描述，例如：{"column":""}
     */
    private String ruleDataDesc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    public String getRuleHandle() {
        return ruleHandle;
    }

    public void setRuleHandle(String ruleHandle) {
        this.ruleHandle = ruleHandle;
    }

    public String getRuleDataDesc() {
        return ruleDataDesc;
    }

    public void setRuleDataDesc(String ruleDataDesc) {
        this.ruleDataDesc = ruleDataDesc;
    }

    @Override
    public String toString() {
        return "DsRule{" +
        "id=" + id +
        ", ruleType=" + ruleType +
        ", ruleCode=" + ruleCode +
        ", ruleName=" + ruleName +
        ", status=" + status +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", ruleHandle=" + ruleHandle +
        ", ruleDataDesc=" + ruleDataDesc +
        "}";
    }
}
