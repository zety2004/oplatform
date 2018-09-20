package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.StudentChoice;

import java.util.List;
import java.util.Map;

public interface StudentChoiceService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentChoice record);

    StudentChoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentChoice record);

    Map<String, Object> queryCurriculumForParentApply(Integer scaId);

    int queryParentApplyForVerificationTime(Integer scaId);

    int queryParentApplyForIsQualified(Integer schoolId, String grade, Integer scaId);

    int queryParentApplyForIsApply(Integer scaId, Integer studentId);

    int queryParentApplyForIsCollision(Integer scaId, Integer studentId);

    List<Map<String, Object>> queryMyCurriculum(Integer studentId, Integer weekType);

    List<Map<String, Object>> queryMyCurriculumList(Integer studentId, Integer state);

    Map<String, Object> selectByOrderId(String orderId);

    int queryIsCanRefund(Integer scaId);
}
