package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SClass;

import java.util.List;
import java.util.Map;

public interface SClassMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(SClass record);

    SClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SClass record);

    List<SClass> queryClasses(Integer schoolId);

    SClass selectByNameForValidate(Map<String, Object> param);
}