package com.hklk.oplatform.entity.table;

import java.io.Serializable;
import java.util.Date;

public class StudentChoice implements Serializable {
    private Integer id;

    private Integer studentId;

    private Integer scaId;

    private Date createTime;

    private Integer payState;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getScaId() {
        return scaId;
    }

    public void setScaId(Integer scaId) {
        this.scaId = scaId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }
}