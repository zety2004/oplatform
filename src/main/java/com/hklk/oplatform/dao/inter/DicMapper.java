package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Dic;

import java.util.List;
import java.util.Map;

public interface DicMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Dic record);

    List<Map<String, Object>> queryForList(String typeCode);

    int updateByPrimaryKeySelective(Dic record);

}