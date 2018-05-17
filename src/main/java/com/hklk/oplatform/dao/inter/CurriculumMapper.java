package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Curriculum;

import java.util.List;

public interface CurriculumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Curriculum record);

    int insertSelective(Curriculum record);

    Curriculum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Curriculum record);

    int updateByPrimaryKeyWithBLOBs(Curriculum record);

    int updateByPrimaryKey(Curriculum record);

    List<Curriculum> queryCurriculums();
}