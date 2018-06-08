package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Dic;

import java.util.List;

public interface DicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dic record);

    int insertSelective(Dic record);

    List<Dic> queryForList(String typeCode);

    int updateByPrimaryKeySelective(Dic record);

    int updateByPrimaryKey(Dic record);
}