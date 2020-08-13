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
public class DsColumnRule implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String columnId;

    /**
     * 规则的英文标识
     */
    private String ruleCode;

    /**
     * json格式参数对象，例如：规则需要对应多个列名参数则可配置{"cloum":["c1","c2"]}，便于不同规则处理需要的扩展
     */
    private String ruleParam;

    /**
     * 1可用,0停用
     */
    private String status;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleParam() {
        return ruleParam;
    }

    public void setRuleParam(String ruleParam) {
        this.ruleParam = ruleParam;
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

    @Override
    public String toString() {
        return "DsColumnRule{" +
        "id=" + id +
        ", columnId=" + columnId +
        ", ruleCode=" + ruleCode +
        ", ruleParam=" + ruleParam +
        ", status=" + status +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        "}";
    }
}
