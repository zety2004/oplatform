package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;

import java.util.List;
import java.util.Map;

public interface SCApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SCApply record);

    SCApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SCApply record);

    List<CurriculumApplyVo> queryCurriculumApply(Map<String, Integer> param);

    List<CurriculumChoiceVo> queryCurriculumChoice(Integer schoolId);

    List<SStudent> queryStudentBySCAId(Integer scaId);
}