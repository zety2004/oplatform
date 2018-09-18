package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.StudentChoice;

import java.util.List;
import java.util.Map;

public interface StudentChoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentChoice record);

    StudentChoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentChoice record);

    Map<String, Object> queryCurriculumForParentApply(Map<String, Object> param);

    int queryParentApplyForVerificationTime(Map<String, Object> param);

    List<Map<String, Object>> queryMyCurriculum(Map<String, Object> param);

    List<Map<String, Object>> queryMyCurriculumList(Map<String, Object> param);

    int queryMyCurriculumVerification(Map<String, Object> param);

    int queryParentApplyForIsApply(Map<String, Object> param);

    Map<String, Object> selectByOrderId(String orderId);

    int queryParentApplyForIsQualified(Map<String, Object> param);

    int queryIsCanRefund(Integer scaId);
}