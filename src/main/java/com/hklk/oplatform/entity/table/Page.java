package com.hklk.oplatform.entity.table;

import java.io.Serializable;

public class Page implements Serializable {
    private Integer id;

    private String pageName;

    private Integer parentId;

    private String pageSrc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPageSrc() {
        return pageSrc;
    }

    public void setPageSrc(String pageSrc) {
        this.pageSrc = pageSrc == null ? null : pageSrc.trim();
    }
}