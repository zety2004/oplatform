package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Curriculum;

public interface CurriculumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Curriculum record);

    int insertSelective(Curriculum record);

    Curriculum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Curriculum record);

    int updateByPrimaryKeyWithBLOBs(Curriculum record);

    int updateByPrimaryKey(Curriculum record);
}