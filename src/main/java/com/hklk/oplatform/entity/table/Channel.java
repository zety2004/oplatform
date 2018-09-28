package com.hklk.oplatform.entity.table;

import java.io.Serializable;

public class Channel implements Serializable {
    private static final long serialVersionUID = 7313659403786010724L;
    private Integer id;

    private String channelName;

    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}