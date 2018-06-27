package com.hklk.oplatform.entity.vo;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SchoolVo implements Serializable {
    private Integer id;

    private String name;

    private Date createTime;

    private String remarks;

    private Integer status;

    private String schoolNum;

    private String schoolLogo;

    private List<SchoolAdmin> list;

    public SchoolVo(School school, List<SchoolAdmin> list) {
        this.id = school.getId();
        this.name = school.getName();
        this.createTime = school.getCreateTime();
        this.remarks = school.getRemarks();
        this.status = school.getStatus();
        this.schoolNum = school.getSchoolNum();
        this.list = list;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public List<SchoolAdmin> getList() {
        return list;
    }

    public void setList(List<SchoolAdmin> list) {
        this.list = list;
    }
}