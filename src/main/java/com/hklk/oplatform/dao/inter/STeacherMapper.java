package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.STeacher;

import java.util.List;

public interface STeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(STeacher record);

    int insertSelective(STeacher record);

    STeacher selectBySTeacher(STeacher sTeacher);

    int updateByPrimaryKeySelective(STeacher sTeacher);

    int updateByPrimaryKey(STeacher sTeacher);

    List<STeacher> queryTeacherBySchoolId(Integer schoolId);
}