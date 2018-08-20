package com.hklk.oplatform.entity.table;

import java.io.Serializable;

public class Curriculum implements Serializable {
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

    private String des;

    private String wxdes;

    private String uniqueNum;

    private Integer teacherId;

    private Integer isPublic;

    private String twxdes1;

    private String twxdes2;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getUniqueNum() {
        return uniqueNum;
    }

    public void setUniqueNum(String uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public String getWxdes() {
        return wxdes;
    }

    public void setWxdes(String wxdes) {
        this.wxdes = wxdes;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public String getTwxdes1() {
        return twxdes1;
    }

    public void setTwxdes1(String twxdes1) {
        this.twxdes1 = twxdes1;
    }

    public String getTwxdes2() {
        return twxdes2;
    }

    public void setTwxdes2(String twxdes2) {
        this.twxdes2 = twxdes2;
    }
}

