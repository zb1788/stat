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
 * @since 2020-05-19
 */
@TableName("ts_rule_group")
public class TsRuleGroup implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 分组唯一编码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组定时时间串：0/10 * * * * ?
     */
    private String groupRule;

    /**
     * 创建分组时间
     */
//    @TableField(updateStrategy = FieldStrategy.NEVER)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改分组时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

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

    public String getGroupRule() {
        return groupRule;
    }

    public void setGroupRule(String groupRule) {
        this.groupRule = groupRule;
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

    @Override
    public String toString() {
        return "TsRuleGroup{" +
        "groupCode=" + groupCode +
        ", groupName=" + groupName +
        ", groupRule=" + groupRule +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", remark1=" + remark1 +
        ", remark2=" + remark2 +
        ", remark3=" + remark3 +
        "}";
    }
}
