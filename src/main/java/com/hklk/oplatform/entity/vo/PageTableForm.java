package com.hklk.oplatform.entity.vo;

import com.github.pagehelper.Page;

import java.util.List;

public class PageTableForm<T> {
    private int currentPage;
    private int pageSize;
    private int beginIndex;
    private int endIndex;
    private long pageCount;
    private long totleCount;
    private List<T> objList;

    public PageTableForm(Page<T> page) {
        this.currentPage = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.beginIndex = page.getStartRow();
        this.endIndex = page.getEndRow();
        this.pageCount = page.getPages();
        this.totleCount = page.getTotal();
        this.objList = page.getResult();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotleCount() {
        return totleCount;
    }

    public void setTotleCount(long totleCount) {
        this.totleCount = totleCount;
    }

    public List<T> getObjList() {
        return objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }
}
