package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.StudentChoice;

import java.util.List;
import java.util.Map;

public interface StudentChoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentChoice record);

    StudentChoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentChoice record);

    int queryParentApplyForVerification(Map<String, Object> param);

    List<Map<String, Object>> queryMyCurriculum(Map<String, Object> param);

    int queryMyCurriculumVerification(Map<String, Object> param);

    Map<String, Object> queryParentApplyForIsApply(Map<String, Object> param);

    Map<String, Object> selectByOrderId(String orderId);
}