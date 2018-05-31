package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.SCApplyMapper;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.SCApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SCApplyServiceServiceImpl implements SCApplyService {

    @Autowired
    SCApplyMapper scApplyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return scApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SCApply scApply) {
        return scApplyMapper.updateByPrimaryKeySelective(scApply);
    }

    @Override
    public SCApply selectByPrimaryKey(Integer id) {
        return scApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(SCApply scApply) {
        return scApplyMapper.insertSelective(scApply);
    }

    @Override
    public PageTableForm<CurriculumApplyVo> queryCurriculumApply(Integer schoolId, Integer status, int pageNum, int pageSize) {
        Map<String, Integer> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("status", status);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryCurriculumApply(param);
        PageTableForm<CurriculumApplyVo> curriculumApplyVoPageTableForm = new PageTableForm<>(page);
        return curriculumApplyVoPageTableForm;
    }

    @Override
    public PageTableForm<CurriculumChoiceVo> queryCurriculumChoice(Integer schoolId, int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryCurriculumChoice(schoolId);
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm = new PageTableForm<>(page);
        return curriculumChoiceVoPageTableForm;
    }

    @Override
    public List<SStudent> queryStudentBySCAId(Integer scaId) {
        return scApplyMapper.queryStudentBySCAId(scaId);
    }
}
