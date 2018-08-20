package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Dic;

import java.util.List;
import java.util.Map;

public interface DicService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Dic record);

    List<Map<String,Object>> queryForList(String typeCode);

    int updateByPrimaryKeySelective(Dic record);
}
