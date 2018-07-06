package com.hklk.oplatform.comm;

import java.io.Serializable;

/**
 * 登录成功用户对象
 */
public class LoginTeacher implements Serializable {

    // 登录成功ID
    private Integer teacherId;
    // 登录成功用户名
    private String account;

    private String nickName;

    private Integer schoolId;

    private String schoolName;

    private String schoolLogo;

    private String headIco;

    private String rolePage;

    public LoginTeacher() {
    }

    public LoginTeacher(Integer teacherId, String account, String nickName, String rolePage, Integer schoolId,String schoolName, String schoolLogo, String headIco) {
        super();
        this.teacherId = teacherId;
        this.account = account;
        this.nickName = nickName;
        this.rolePage = rolePage;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolLogo = schoolLogo;
        this.headIco = headIco;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginTeacher other = (LoginTeacher) obj;
        if (teacherId == null) {
            if (other.teacherId != null)
                return false;
        } else if (!teacherId.equals(other.teacherId))
            return false;
        return true;
    }

    public String getRolePage() {
        return rolePage;
    }

    public void setRolePage(String rolePage) {
        this.rolePage = rolePage;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getHeadIco() {
        return headIco;
    }

    public void setHeadIco(String headIco) {
        this.headIco = headIco;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}