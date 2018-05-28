package com.hklk.oplatform.entity.vo;

import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CurriculumVo implements Serializable {
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

    private List<Consumables> consumables;

    private static final long serialVersionUID = 1L;

    public CurriculumVo(Curriculum curriculum, List<Consumables> consumables) {
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
        this.des = curriculum.getDes();
        this.consumables = consumables;
    }

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

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<Consumables> getConsumables() {
        return consumables;
    }

    public void setConsumables(List<Consumables> consumables) {
        this.consumables = consumables;
    }

}