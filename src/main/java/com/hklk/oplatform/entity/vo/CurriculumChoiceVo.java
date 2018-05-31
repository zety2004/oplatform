package com.hklk.oplatform.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class CurriculumChoiceVo implements Serializable {
    private Integer id;

    private String curriculumName;

    private String teacherName;

    private Date currStartTime;

    private Integer maxNum;

    private Integer studentNum;

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
}