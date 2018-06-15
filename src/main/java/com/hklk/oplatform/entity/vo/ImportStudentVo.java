package com.hklk.oplatform.entity.vo;

import java.io.Serializable;

public class ImportStudentVo implements Serializable {

    private String fullName;

    private String sNum;

    private String reason;

    private static final long serialVersionUID = 1L;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getsNum() {
        return sNum;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum == null ? null : sNum.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}