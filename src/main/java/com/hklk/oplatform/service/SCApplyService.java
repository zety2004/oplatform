package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface SCApplyService {
    /**
     * @author 曹良峰
     * @Description 课程申报删除
     * @Date 19:09 2018/5/29
     * @Param [id]
     * @Return int
     **/
    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SCApply scApply);

    int insertSelective(SCApply scApply);

    SCApply selectByPrimaryKey(Integer id);

    PageTableForm<CurriculumApplyVo> queryCurriculumApply(Integer schoolId, Integer status, int pageNum, int pageSize);

    PageTableForm<CurriculumChoiceVo> queryCurriculumChoice(Integer schoolId,int pageNum, int pageSize);

    List<SStudent> queryStudentBySCAId(Integer scaId);
}
