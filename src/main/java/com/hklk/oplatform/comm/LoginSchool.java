package com.hklk.oplatform.comm;

import java.io.Serializable;

/**
 * 登录成功用户对象
 */
public class LoginSchool implements Serializable {

    // 登录成功ID
    private Integer schoolAdminId;
    // 登录成功用户名
    private String account;

    private String nickName;

    private String rolePage;

    public LoginSchool(Integer schoolAdminId, String account, String nickName, String rolePage) {
        super();
        this.schoolAdminId = schoolAdminId;
        this.account = account;
        this.nickName = nickName;
        this.rolePage = rolePage;
    }

    public Integer getSchoolAdminId() {
        return schoolAdminId;
    }

    public void setSchoolAdminId(Integer schoolAdminId) {
        this.schoolAdminId = schoolAdminId;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginSchool other = (LoginSchool) obj;
        if (schoolAdminId == null) {
            if (other.schoolAdminId != null)
                return false;
        } else if (!schoolAdminId.equals(other.schoolAdminId))
            return false;
        return true;
    }

    public String getRolePage() {
        return rolePage;
    }

    public void setRolePage(String rolePage) {
        this.rolePage = rolePage;
    }
}