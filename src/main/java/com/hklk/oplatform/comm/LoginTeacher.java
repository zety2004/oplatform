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

    private String remark;

    private String tag;

    private String openid;

    private String session_key;


    public LoginTeacher() {
    }

    public LoginTeacher(Integer teacherId, String account, String nickName, String rolePage, Integer schoolId, String schoolName, String schoolLogo, String headIco, String remark, String tag, String openid, String session_key) {
        super();
        this.teacherId = teacherId;
        this.account = account;
        this.nickName = nickName;
        this.rolePage = rolePage;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolLogo = schoolLogo;
        this.headIco = headIco;
        this.remark = remark;
        this.tag = tag;
        this.openid = openid;
        this.session_key = session_key;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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