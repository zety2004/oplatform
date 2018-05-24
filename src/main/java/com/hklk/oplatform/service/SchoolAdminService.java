package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface SchoolAdminService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    List<SchoolAdmin> querySchoolAdminsBySchoolId(int schoolId);

    int querySchoolAdminsForCount(int schoolId);

    SchoolAdmin querySchoolAdminsByName(String account);
}
