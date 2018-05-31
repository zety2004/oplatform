package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.SClassMapper;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.SClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SClassServiceServiceImpl implements SClassService {

    @Autowired
    SClassMapper sClassMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sClassMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertOrUpdateByPrimaryKeySelective(SClass sClass) {
        if (sClass.getId() != null) {
            return sClassMapper.updateByPrimaryKeySelective(sClass);
        } else {
            return sClassMapper.insertSelective(sClass);
        }
    }

    @Override
    public PageTableForm<SClass> queryClasses(Integer schoolId, int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        sClassMapper.queryClasses(schoolId);
        PageTableForm<SClass> sClassPageTableForm = new PageTableForm<>(page);
        return sClassPageTableForm;
    }

    @Override
    public SClass selectByNameForValidate(String name, Integer schoolId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("schoolId", schoolId);
        return sClassMapper.selectByNameForValidate(param);
    }
}
