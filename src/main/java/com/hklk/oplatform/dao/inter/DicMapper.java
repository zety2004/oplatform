package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Dic;

public interface DicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dic record);

    int insertSelective(Dic record);

    Dic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dic record);

    int updateByPrimaryKey(Dic record);
}