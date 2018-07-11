package com.hklk.oplatform.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class CurriculumChoiceVo implements Serializable {
    private Integer id;

    private String curriculumName;

    private String teacherName;

    private String grade;

    private Date currStartTime;

    private Integer maxNum;

    private Integer studentNum;

    private String chooseTime;

    private String classPlace;

    private Integer applyStatus;

    private String classTime;

    private Integer curriculumId;

    private Integer isFineQuality;

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

    public Date getCurrStartTime() {
        return currStartTime;
    }

    public void setCurrStartTime(Date currStartTime) {
        this.currStartTime = currStartTime;
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

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public Integer getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Integer curriculumId) {
        this.curriculumId = curriculumId;
    }

    public Integer getIsFineQuality() {
        return isFineQuality;
    }

    public void setIsFineQuality(Integer isFineQuality) {
        this.isFineQuality = isFineQuality;
    }
}