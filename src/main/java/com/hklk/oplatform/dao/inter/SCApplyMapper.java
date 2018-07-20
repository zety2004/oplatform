package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.StudentPay;

import java.util.List;
import java.util.Map;

public interface SCApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SCApply record);

    SCApply selectByPrimaryKey(Integer id);

    List<SCApply> selectByTeacherId(Map<String, Object> param);

    int updateByPrimaryKeySelective(SCApply record);

    List<CurriculumApplyVo> queryCurriculumApply(Map<String, Integer> param);

    List<CurriculumChoiceVo> queryCurriculumChoice(Map<String, Object> param);

    List<StudentPay> queryStudentBySCAId(Integer scaId);

    List<CurriculumOrderVo> queryCurriculumOrder(Map<String, Object> param);

    List<CurriculumChoiceVo> queryByTeacherId(Map<String, Object> param);

    List<Map<String, Object>> queryCurriculumForParent(Map<String, Object> param);

    Map<String, Object> selectByApplyCurriculmForParentById(Integer id);
}