package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.SchoolMapper;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return schoolMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(School record) {
        return schoolMapper.insertSelective(record);
    }

    @Override
    public School selectByPrimaryKey(Integer id) {
        return schoolMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(School record) {
        return schoolMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageTableForm<School> querySchools(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        schoolMapper.querySchools();
        PageTableForm<School> pageTableForm = new PageTableForm<School>(page);

        return pageTableForm;
    }
}
