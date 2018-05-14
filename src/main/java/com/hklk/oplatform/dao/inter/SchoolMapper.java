package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolKey;

public interface SchoolMapper {
    int deleteByPrimaryKey(SchoolKey key);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(SchoolKey key);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
}