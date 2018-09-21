package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.ConsumablesMapper;
import com.hklk.oplatform.dao.inter.SCApplyMapper;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.*;
import com.hklk.oplatform.service.SCApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Service
public class SCApplyServiceServiceImpl implements SCApplyService {

    @Autowired
    SCApplyMapper scApplyMapper;
    @Autowired
    ConsumablesMapper consumablesMapper;

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
    public List<Map<String, Object>> queryCurriculumListForTask() {
        return scApplyMapper.queryCurriculumListForTask();
    }

    @Override
    public Map<String, Object> selectByTeacherApplyForAuditing(Integer id) {
        return scApplyMapper.selectByTeacherApplyForAuditing(id);
    }

    @Override
    public int insertSelective(SCApply scApply) {
        return scApplyMapper.insertSelective(scApply);
    }

    @Override
    public List<CurriculumApplyVo> queryCurriculumApply(Integer schoolId, Integer status) {
        Map<String, Integer> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("status", status);
        return scApplyMapper.queryCurriculumApply(param);
    }

    @Override
    public PageTableForm<CurriculumApplyVo> queryCurriculumApplyForPage(Integer schoolId, Integer status, int pageNum, int pageSize) {
        Map<String, Integer> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("status", status);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryCurriculumApply(param);
        PageTableForm<CurriculumApplyVo> curriculumApplyVoPageTableForm = new PageTableForm<>(page);
        return curriculumApplyVoPageTableForm;
    }

    @Override
    public PageTableForm<Map<String, Object>> queryCurriculumApplyForExamine(Integer schoolId, int pageNum, int pageSize) {
        Map<String, Integer> param = new HashMap<>();
        param.put("schoolId", schoolId);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryCurriculumApplyForExamine(param);
        PageTableForm<Map<String, Object>> curriculumApplyVoPageTableForm = new PageTableForm<>(page);
        return curriculumApplyVoPageTableForm;
    }

    @Override
    public PageTableForm<CurriculumChoiceVo> queryCurriculumChoice(Integer schoolId, String param, Integer isEnd, int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        params.put("param", param);
        params.put("isEnd", isEnd);
        scApplyMapper.queryCurriculumChoice(params);
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm = new PageTableForm<>(page);
        return curriculumChoiceVoPageTableForm;
    }

    @Override
    public List<StudentPay> queryStudentBySCAId(Integer scaId) {
        return scApplyMapper.queryStudentBySCAId(scaId);
    }

    @Override
    public PageTableForm<CurriculumOrderVo> queryCurriculumOrder(String queryParam, Integer isHandle, Integer ishc, int pageNum, int pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("queryParam", queryParam);
        param.put("isHandle", isHandle);
        param.put("ishc", ishc);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryCurriculumOrder(param);
        PageTableForm<CurriculumOrderVo> curriculumOrderVoPageTableForm = new PageTableForm<>(page);
        return curriculumOrderVoPageTableForm;
    }

    public List<CurriculumOrderVo> queryCurriculumOrderForList(String queryParam, Integer isHandle, Integer ishc) {
        Map<String, Object> param = new HashMap<>();
        param.put("queryParam", queryParam);
        param.put("isHandle", isHandle);
        param.put("ishc", ishc);
        List<CurriculumOrderVo> curriculumOrderVos = scApplyMapper.queryCurriculumOrder(param);
        return curriculumOrderVos;
    }

    @Override
    public PageTableForm<Map<String, Object>> queryByTeacherId(Integer teacherId, Integer status, String searchParam, Integer pageNum, Integer pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("teacherId", teacherId);
        param.put("status", status);
        param.put("searchParam", searchParam);
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        scApplyMapper.queryByTeacherId(param);
        PageTableForm<Map<String, Object>> curriculumChoiceVoPageTableForm = new PageTableForm<>(page);
        return curriculumChoiceVoPageTableForm;
    }

    @Override
    public Map<String, List<Map<String, Object>>> queryHotCurriculumForParent(Integer schoolId, Integer grade) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("isFineQuality", 1);
        List<Map<String, Object>> jpList = scApplyMapper.queryCurriculumForParent(param);
        if (jpList.size() == 0 || jpList.get(0).get("id") == null) {
            jpList = null;
        }
        param.put("isFineQuality", -1);
        param.put("grade", grade);
        List<Map<String, Object>> allList = scApplyMapper.queryCurriculumForParent(param);
        if (allList.size() == 0 || allList.get(0).get("id") == null) {
            allList = null;
        } else {
            int hotNum = allList.size() / 3;
            for (int i = 1; i <= allList.size(); i++) {
                if (i < hotNum) {
                    allList.get(i - 1).put("hot", 1);
                } else {
                    allList.get(i - 1).put("hot", 0);
                }
            }
        }
        result.put("fineQuality", jpList);
        result.put("all", allList);

        return result;
    }

    @Override
    public List<Map<String, Object>> queryAllCurriculumForParent(Integer schoolId, Integer grade, Integer weekType) {
        Map<String, Object> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("grade", grade);
        param.put("weekType", weekType);
        param.put("isFineQuality", -1);
        List<Map<String, Object>> allList = scApplyMapper.queryCurriculumForParent(param);
        if (allList.size() == 0 || allList.get(0).get("id") == null) {
            return null;
        } else {
            if (grade == null) {
                int hotNum = allList.size() / 3;
                for (int i = 1; i <= allList.size(); i++) {
                    if (i < hotNum) {
                        allList.get(i - 1).put("hot", 1);
                    } else {
                        allList.get(i - 1).put("hot", 0);
                    }
                }
            }

            return allList;

        }

    }

    @Override
    public Map<String, Object> selectByApplyCurriculmForParentById(Integer id) {
        Map<String, Object> result = scApplyMapper.selectByApplyCurriculmForParentById(id);
        if (result.get("wxdes") != null) {
            result.put("wxdes", new String((byte[]) result.get("wxdes")));
        }
        List<Consumables> consumables = consumablesMapper.queryConsumablesByCurId((Integer) result.get("curriculumId"));
        List<Map<String, Object>> mapList = scApplyMapper.queryStudentBySCAIdForParent(id);
        result.put("consumables", consumables);
        result.put("students", mapList);
        return result;
    }
}
