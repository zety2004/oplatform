package com.hklk.oplatform.entity.table;

import java.io.Serializable;

public class CurriculumInsertVo implements Serializable {
    private Integer id;

    private String cover;

    private String name;

    private String subject;

    private String grade;

    private String learningStyle;

    private String classHours;

    private Integer collectionNum;

    private String author;

    private String enclosure;

    private String encDes;

    private Integer status;

    private String uniqueNum;

    private byte[] des;

    private byte[] wxdes;

    public CurriculumInsertVo(Curriculum curriculum) {
        this.id = curriculum.getId();
        this.cover = curriculum.getCover();
        this.name = curriculum.getName();
        this.subject = curriculum.getSubject();
        this.grade = curriculum.getGrade();
        this.learningStyle = curriculum.getLearningStyle();
        this.classHours = curriculum.getClassHours();
        this.collectionNum = curriculum.getCollectionNum();
        this.author = curriculum.getAuthor();
        this.enclosure = curriculum.getEnclosure();
        this.encDes = curriculum.getEncDes();
        this.status = curriculum.getStatus();
        this.uniqueNum = curriculum.getUniqueNum();
        this.des = curriculum.getDes() == null ? null : curriculum.getDes().getBytes();
        this.wxdes = curriculum.getWxdes() == null ? null : curriculum.getWxdes().getBytes();
    }

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
        this.cover = cover == null ? null : cover.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle == null ? null : learningStyle.trim();
    }

    public String getClassHours() {
        return classHours;
    }

    public void setClassHours(String classHours) {
        this.classHours = classHours == null ? null : classHours.trim();
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
        this.author = author == null ? null : author.trim();
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure == null ? null : enclosure.trim();
    }

    public String getEncDes() {
        return encDes;
    }

    public void setEncDes(String encDes) {
        this.encDes = encDes == null ? null : encDes.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUniqueNum() {
        return uniqueNum;
    }

    public void setUniqueNum(String uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public byte[] getDes() {
        return des;
    }

    public void setDes(byte[] des) {
        this.des = des;
    }

    public byte[] getWxdes() {
        return wxdes;
    }

    public void setWxdes(byte[] wxdes) {
        this.wxdes = wxdes;
    }
}