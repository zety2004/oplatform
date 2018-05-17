package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.CurriculumMapper;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    CurriculumMapper curriculumMapper;

    @Override
    public PageTableForm<Curriculum> queryCurriculums(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        curriculumMapper.queryCurriculums();
        PageTableForm<Curriculum> pageTableForm = new PageTableForm(page);
        return pageTableForm;
    }

    @Override
    public Curriculum selectByPrimaryKey(Integer id) {
        return curriculumMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addCurriculum(Curriculum curriculum) {
        return curriculumMapper.insertSelective(curriculum);
    }

    @Override
    public int updateCurriculum(Curriculum curriculum) {
        return curriculumMapper.updateByPrimaryKeySelective(curriculum);
    }

    @Override
    public int deleteCurriculum(Integer id) {
        return curriculumMapper.deleteByPrimaryKey(id);
    }
}
