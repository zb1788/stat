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
public class DsKafkaTopicConf implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * kafka的platid
     */
    private String platid;
    /**
     * kafka的topic
     */
    private String topic;

    /**
     * 每个topic的分区号，如果只有一个分区，该值为0，不能为负值
     */
    private Integer topicPartition;

    /**
     * 每个分区对应的offset
     */
    private Integer topicOffset;

    /**
     * 更新时间
     */
    private LocalDateTime updatetime;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatid() {
        return platid;
    }

    public void setPlatid(String platid) {
        this.platid = platid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTopicPartition() {
        return topicPartition;
    }

    public void setTopicPartition(Integer topicPartition) {
        this.topicPartition = topicPartition;
    }

    public Integer getTopicOffset() {
        return topicOffset;
    }

    public void setTopicOffset(Integer topicOffset) {
        this.topicOffset = topicOffset;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "DsKafkaTopicConf{" +
                "id='" + id + '\'' +
                ", platid='" + platid + '\'' +
                ", topic='" + topic + '\'' +
                ", topicPartition=" + topicPartition +
                ", topicOffset=" + topicOffset +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }
}
