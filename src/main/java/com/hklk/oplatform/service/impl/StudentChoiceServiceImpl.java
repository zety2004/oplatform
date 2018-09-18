package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.StudentChoiceMapper;
import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.StudentChoice;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.StudentChoiceService;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentChoiceServiceImpl implements StudentChoiceService {

    @Autowired
    StudentChoiceMapper studentChoiceMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return studentChoiceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(StudentChoice record) {
        return studentChoiceMapper.insertSelective(record);
    }

    @Override
    public StudentChoice selectByPrimaryKey(Integer id) {
        return studentChoiceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(StudentChoice record) {
        return studentChoiceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int queryParentApplyForIsApply(Integer scaId, Integer studentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("scaId", scaId);
        params.put("studentId", studentId);
        return studentChoiceMapper.queryParentApplyForIsApply(params);
    }

    @Override
    public Map<String, Object> queryCurriculumForParentApply(Integer scaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("scaId", scaId);
        return studentChoiceMapper.queryCurriculumForParentApply(params);
    }

    @Override
    public int queryParentApplyForVerificationTime(Integer scaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("scaId", scaId);
        return studentChoiceMapper.queryParentApplyForVerificationTime(params);
    }

    @Override
    public List<Map<String, Object>> queryMyCurriculum(Integer studentId, Integer weekType) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("weekType", weekType);
        int num = studentChoiceMapper.queryMyCurriculumVerification(params);
        if (num == 0) {
            return null;
        } else {
            List<Map<String, Object>> result = studentChoiceMapper.queryMyCurriculum(params);
            return result;
        }
    }

    @Override
    public List<Map<String, Object>> queryMyCurriculumList(Integer studentId, Integer state) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("state", state);
        List<Map<String, Object>> result = studentChoiceMapper.queryMyCurriculumList(params);
        return result;
    }

    @Override
    public Map<String, Object> selectByOrderId(String orderId) {
        return studentChoiceMapper.selectByOrderId(orderId);
    }

    @Override
    public int queryParentApplyForIsQualified(Integer schoolId, String grade, Integer scaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        params.put("grade", grade);
        params.put("scaId", scaId);
        return studentChoiceMapper.queryParentApplyForIsQualified(params);
    }

    @Override
    public int queryIsCanRefund(Integer scaId) {
        return studentChoiceMapper.queryIsCanRefund(scaId);
    }
}
