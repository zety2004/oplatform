package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.School;

import java.util.List;

public interface SchoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

    List<School> querySchools(String param);

    School selectSchoolByName(String name);
}