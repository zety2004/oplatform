package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.StudentChoice;

import java.util.Map;

public interface StudentChoiceService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentChoice record);

    StudentChoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentChoice record);

    int queryParentApplyForVerification(Integer schoolId, Integer scaId, Integer studentId);
}
