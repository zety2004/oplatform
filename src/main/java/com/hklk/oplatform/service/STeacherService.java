package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.TeacherVo;

import java.util.List;
import java.util.Map;

public interface STeacherService {

    int deleteByPrimaryKey(Integer id);

    STeacher selectBySTeacher(STeacher sTeacher);

    int insertOrUpdateByPrimaryKeySelective(STeacher sTeacher);

    Map<String, Object> insertBatchTeacher(List<STeacher> sTeachers, Integer schoolId);

    PageTableForm<STeacher> queryTeacherBySchoolId(Integer schoolId, String param, int pageNum, int pageSize);

    List<STeacher> queryTeacherBySchoolIdForList(Integer schoolId);

    TeacherVo loginTeacher(String account, String pwd);

    StringBuffer getTeacherTag();
}
