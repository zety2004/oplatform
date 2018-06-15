package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;
import java.util.Map;

public interface SStudentService {

    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateByPrimaryKeySelective(SStudent sStudent);

    SStudent selectByPrimaryKey(Integer id);

    List<SStudent> queryStudentByClassId(String param,Integer classId);

    SStudent selectBySNumForValidate(Integer schoolId, String sNum);
}
