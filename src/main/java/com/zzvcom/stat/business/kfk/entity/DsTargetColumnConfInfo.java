package com.zzvcom.stat.business.kfk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author boz
 * @date 2020/5/26
 */
public class DsTargetColumnConfInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 所属表
     */
    private String tabId;

    /**
     * 列名
     */
    private String colName;

    /**
     * 字段类型
     */
//    private String colType;

    /**
     * 字段长度
     */
    private Integer colLength;

    /**
     * 列写入规则，比如源表中改列清空时，目标表中该列不清空
     */
    private String colWriteModel;

    /**
     * 状态，标识是否需要写入该字段
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    private List<DsColumnRule> columnRuleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

//    public String getColType() {
//        return colType;
//    }
//
//    public void setColType(String colType) {
//        this.colType = colType;
//    }

    public Integer getColLength() {
        return colLength;
    }

    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    public String getColWriteModel() {
        return colWriteModel;
    }

    public void setColWriteModel(String colWriteModel) {
        this.colWriteModel = colWriteModel;
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

    public List<DsColumnRule> getColumnRuleList() {
        return columnRuleList;
    }

    public void setColumnRuleList(List<DsColumnRule> columnRuleList) {
        this.columnRuleList = columnRuleList;
    }

    @Override
    public String toString() {
        return "DsTargetColumnConfInfo{" +
                "id='" + id + '\'' +
                ", tabId=" + tabId +
                ", colName='" + colName + '\'' +
//                ", colType='" + colType + '\'' +
                ", colLength=" + colLength +
                ", colWriteModel='" + colWriteModel + '\'' +
                ", status=" + status +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", columnRuleList=" + columnRuleList +
                '}';
    }
}
