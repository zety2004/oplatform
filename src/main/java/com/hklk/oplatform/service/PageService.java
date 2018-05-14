package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Page;

import java.util.List;

public interface PageService {

    List<Page> queryPages();

    int addPage(Page page);

    int updatePage(Page page);

    int deletePage(Integer id);
}
