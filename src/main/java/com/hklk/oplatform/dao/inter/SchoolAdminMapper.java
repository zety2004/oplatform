package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.SchoolAdminVo;

import java.util.List;
import java.util.Map;

public interface
SchoolAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolAdmin record);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    SchoolAdminVo loginSchool(Map<String,String> map);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    int updateByPrimaryKey(SchoolAdmin record);

    List<SchoolAdmin> querySchoolAdminsBySchoolId(Integer schoolId);

    int querySchoolAdminsForCount(Integer schoolId);

    SchoolAdmin querySchoolAdminsByName(String account);
}