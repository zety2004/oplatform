package com.hklk.oplatform.entity.table;

import java.io.Serializable;
import java.util.Date;

public class FeedBack implements Serializable {
    private Integer id;

    private Integer category;

    private String content;

    private String forTable;

    private Integer feedbackUser;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getForTable() {
        return forTable;
    }

    public void setForTable(String forTable) {
        this.forTable = forTable == null ? null : forTable.trim();
    }

    public Integer getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(Integer feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}