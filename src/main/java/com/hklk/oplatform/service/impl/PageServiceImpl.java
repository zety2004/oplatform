package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.PageMapper;
import com.hklk.oplatform.dao.inter.RoleMapper;
import com.hklk.oplatform.entity.table.Page;
import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.service.PageService;
import com.hklk.oplatform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PageServiceImpl implements PageService {

    @Autowired
    PageMapper pageMapper;

    @Override
    public List<Page> queryPages() {
        return pageMapper.queryPages();
    }

    @Override
    public int addPage(Page page) {
        return pageMapper.insertSelective(page);
    }

    @Override
    public int updatePage(Page page) {
        return pageMapper.updateByPrimaryKeySelective(page);
    }

    @Override
    public int deletePage(Integer id) {
        return pageMapper.deleteByPrimaryKey(id);
    }
}
