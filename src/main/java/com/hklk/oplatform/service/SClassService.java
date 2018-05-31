package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.Map;

public interface SClassService {

    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateByPrimaryKeySelective(SClass sClass);

    PageTableForm<SClass> queryClasses(Integer schoolId, int pageNum, int pageSize);

    SClass selectByNameForValidate(String name,Integer schoolId);
}
