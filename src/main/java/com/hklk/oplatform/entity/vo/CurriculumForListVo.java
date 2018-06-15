package com.hklk.oplatform.entity.vo;

import java.io.Serializable;

public class CurriculumForListVo implements Serializable {
    private Integer id;

    private String cover;

    private String name;

    private String subject;

    private String grade;

    private String learningStyle;

    private String classHours;

    private Integer collectionNum;

    private String author;

    private String encDes;

    private Integer status;

    private Boolean ishc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle;
    }

    public String getClassHours() {
        return classHours;
    }

    public void setClassHours(String classHours) {
        this.classHours = classHours;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEncDes() {
        return encDes;
    }

    public void setEncDes(String encDes) {
        this.encDes = encDes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIshc() {
        return ishc;
    }

    public void setIshc(Boolean ishc) {
        this.ishc = ishc;
    }
}