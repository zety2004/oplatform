package com.hklk.oplatform.entity.table;

import java.io.Serializable;
import java.util.Date;

public class SSyllabus implements Serializable {
    private Integer id;

    private Integer scaId;

    private Integer schoolId;

    private Integer timeType;

    private Integer weekType;

    private Date classTime;



    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScaId() {
        return scaId;
    }

    public void setScaId(Integer scaId) {
        this.scaId = scaId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getWeekType() {
        return weekType;
    }

    public void setWeekType(Integer weekType) {
        this.weekType = weekType;
    }

    public Date getClassTime() {
        return classTime;
    }

    public void setClassTime(Date classTime) {
        this.classTime = classTime;
    }
}