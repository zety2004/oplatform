package com.hklk.oplatform.entity.table;

import java.io.Serializable;

public class ChannelSchool implements Serializable {
    private static final long serialVersionUID = -6477121796037632425L;
    private Integer id;

    private Integer channelId;

    private Integer schoolId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}