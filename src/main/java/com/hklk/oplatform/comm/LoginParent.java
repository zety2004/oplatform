package com.hklk.oplatform.comm;

import java.io.Serializable;

/**
 * 登录成功用户对象
 */
public class LoginParent implements Serializable {

    // 登录成功ID
    private Integer studentId;
    // 登录成功用户名
    private String phone;

    private String childName;

    private Integer classId;

    private String className;

    private Integer schoolId;

    private String schoolName;

    private Integer grade;

    private String schoolLogo;

    private String openid;

    private String session_key;

    public LoginParent() {
    }

    public LoginParent(Integer studentId, String phone, String childName, Integer classId, String className, Integer schoolId, String schoolName, Integer grade, String schoolLogo, String openid, String session_key) {
        this.studentId = studentId;
        this.phone = phone;
        this.childName = childName;
        this.classId = classId;
        this.className = className;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.grade = grade;
        this.schoolLogo = schoolLogo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginParent other = (LoginParent) obj;
        if (studentId == null) {
            if (other.studentId != null)
                return false;
        } else if (!studentId.equals(other.studentId))
            return false;
        return true;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}