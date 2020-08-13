package com.zzvcom.stat.business.kfk.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author boz
 * @since 2020-05-21
 */
public class DsTargetTableConf implements Serializable {

    private static final long serialVersionUID=1L;

    private String tabId;

    /**
     * 表名
     */
    private String tabName;

    /**
     * 表描述
     */
    private String tabDescription;

    /**
     * 所属数据库
     */
    private String tabDatabase;

    /**
     * 行写入规则，比如源表某行删除时，指定目标表的改行做标记删除
     */
//    private String tabWriteModel;

    private String status;


    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 更新时间
     */
    private LocalDateTime updatetime;

    /**
     * 目标表名称
     */
    private String targetTabName;

    public String getTargetTabName() {
        return targetTabName;
    }

    public void setTargetTabName(String targetTabName) {
        this.targetTabName = targetTabName;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabDescription() {
        return tabDescription;
    }

    public void setTabDescription(String tabDescription) {
        this.tabDescription = tabDescription;
    }

    public String getTabDatabase() {
        return tabDatabase;
    }

    public void setTabDatabase(String tabDatabase) {
        this.tabDatabase = tabDatabase;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
//    public String getTabWriteModel() {
//        return tabWriteModel;
//    }
//
//    public void setTabWriteModel(String tabWriteModel) {
//        this.tabWriteModel = tabWriteModel;
//    }

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
        return "DsTargetTableConf{" +
        "tabId=" + tabId +
        ", tabName=" + tabName +
        ", tabDescription=" + tabDescription +
        ", tabDatabase=" + tabDatabase +
        ", status=" + status +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", targetTabName=" + targetTabName +
        "}";
    }
}
