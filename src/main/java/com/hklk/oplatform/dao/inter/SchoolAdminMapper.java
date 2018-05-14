package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SchoolAdmin;

public interface SchoolAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolAdmin record);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    int updateByPrimaryKey(SchoolAdmin record);
}