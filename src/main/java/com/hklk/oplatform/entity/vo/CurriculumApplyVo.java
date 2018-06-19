package com.hklk.oplatform.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class CurriculumApplyVo implements Serializable {
    private Integer id;

    private String applyPerson;

    private String teacherId;

    private String applyCurriculum;

    private String totalPrice;

    private String grade;

    private String classHours;

    private String classPlace;

    private String applyRemark;

    private Date applyTime;

    private Integer maxNum;

    private Date beginOfSelectTime;

    private Date endOfSelectTime;

    private Date currStartTime;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getApplyCurriculum() {
        return applyCurriculum;
    }

    public void setApplyCurriculum(String applyCurriculum) {
        this.applyCurriculum = applyCurriculum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassHours() {
        return classHours;
    }

    public void setClassHours(String classHours) {
        this.classHours = classHours;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
}