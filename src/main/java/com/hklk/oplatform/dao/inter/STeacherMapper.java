package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.TeacherVo;

import java.util.List;
import java.util.Map;

public interface STeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(STeacher record);

    STeacher selectBySTeacher(STeacher sTeacher);

    int updateByPrimaryKeySelective(STeacher sTeacher);

    int queryCurriculumByTeacherId(Integer id);

    List<STeacher> queryTeacherBySchoolId(Map<String,Object> param);

    TeacherVo loginTeacher(Map<String,Object> param);
}