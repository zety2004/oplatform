package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SchoolAdmin;

import java.util.List;

public interface
SchoolAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolAdmin record);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    int updateByPrimaryKey(SchoolAdmin record);

    List<SchoolAdmin> querySchoolAdminsBySchoolId(Integer schoolId);

    int querySchoolAdminsForCount(Integer schoolId);

    SchoolAdmin querySchoolAdminsByName(String account);
}