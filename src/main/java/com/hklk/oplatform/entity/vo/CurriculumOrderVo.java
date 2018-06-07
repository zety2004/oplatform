package com.hklk.oplatform.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class CurriculumOrderVo implements Serializable {
    private Integer id;

    private String curriculumName;

    private String teacherName;

    private String grade;

    private Date beginOfSelectTime;

    private Date endOfSelectTime;

    private Integer maxNum;

    private Integer studentNum;

    private Integer isSign;

    private String orderRemark;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}