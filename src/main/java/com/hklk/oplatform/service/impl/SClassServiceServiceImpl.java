package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.SClassMapper;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;
import com.hklk.oplatform.service.SClassService;
import org.apache.commons.collections.map.HashedMap;
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
        SClass sClass = new SClass();
        sClass.setId(id);
        sClass.setStatus(0);
        return sClassMapper.updateByPrimaryKeySelective(sClass);
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
    public PageTableForm<SClassVo> queryClasses(String param, Integer schoolId, Integer teacherId, int pageNum, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        params.put("param", param);
        params.put("createBy", teacherId);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        sClassMapper.queryClasses(params);
        PageTableForm<SClassVo> sClassPageTableForm = new PageTableForm<>(page);
        return sClassPageTableForm;
    }

    @Override
    public SClass selectByNameForValidate(String name, Integer schoolId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("schoolId", schoolId);
        return sClassMapper.selectByNameForValidate(param);
    }

    @Override
    public SClass selectByPrimaryKey(Integer id) {
        return sClassMapper.selectByPrimaryKey(id);
    }
}
