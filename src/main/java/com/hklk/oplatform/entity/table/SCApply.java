package com.hklk.oplatform.entity.table;

import java.io.Serializable;
import java.util.Date;

public class SCApply implements Serializable {
    private Integer id;

    private Integer curriculumId;

    private Date applyTime;

    private String applyRemark;

    private Integer teacherId;

    private Integer status;

    private Integer operatorId;

    private Date beginOfSelectTime;

    private Date endOfSelectTime;

    private Date currStartTime;

    private String classPlace;

    private Integer maxNum;

    private Integer schoolId;

    private Integer orderOpUserId;
    private Integer isFineQuality;

    private String orderRemark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Integer curriculumId) {
        this.curriculumId = curriculumId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getBeginOfSelectTime() {
        return beginOfSelectTime;
    }

    public void setBeginOfSelectTime(Date beginOfSelectTime) {
        this.beginOfSelectTime = beginOfSelectTime;
    }

    public Date getEndOfSelectTime() {
        return endOfSelectTime;
    }

    public void setEndOfSelectTime(Date endOfSelectTime) {
        this.endOfSelectTime = endOfSelectTime;
    }

    public Date getCurrStartTime() {
        return currStartTime;
    }

    public void setCurrStartTime(Date currStartTime) {
        this.currStartTime = currStartTime;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace == null ? null : classPlace.trim();
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getOrderOpUserId() {
        return orderOpUserId;
    }

    public void setOrderOpUserId(Integer orderOpUserId) {
        this.orderOpUserId = orderOpUserId;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Integer getIsFineQuality() {
        return isFineQuality;
    }

    public void setIsFineQuality(Integer isFineQuality) {
        this.isFineQuality = isFineQuality;
    }
}