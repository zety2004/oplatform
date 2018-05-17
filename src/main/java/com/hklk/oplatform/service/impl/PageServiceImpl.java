package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.PageMapper;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PageServiceImpl implements PageService {

    @Autowired
    PageMapper pageMapper;

    @Override
    public List<PPage> queryPages() {
        return pageMapper.queryPages();
    }

    @Override
    public int addPage(PPage PPage) {
        return pageMapper.insertSelective(PPage);
    }

    @Override
    public int updatePage(PPage PPage) {
        return pageMapper.updateByPrimaryKeySelective(PPage);
    }

    @Override
    public int deletePage(Integer id) {
        return pageMapper.deleteByPrimaryKey(id);
    }
}
