package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;

public interface STeacherService {

    int deleteByPrimaryKey(Integer id);

    STeacher selectBySTeacher(STeacher sTeacher);

    int insertOrUpdateByPrimaryKeySelective(STeacher sTeacher);

    PageTableForm<STeacher> queryTeacherBySchoolId(Integer schoolId, int pageNum, int pageSize);
}
