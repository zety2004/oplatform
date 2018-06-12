package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;

import java.util.Map;

public interface SClassService {

    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateByPrimaryKeySelective(SClass sClass);

    PageTableForm<SClassVo> queryClasses(String param,Integer schoolId, int pageNum, int pageSize);

    SClass selectByNameForValidate(String name,Integer schoolId);

    SClass selectByPrimaryKey(Integer id);
}
