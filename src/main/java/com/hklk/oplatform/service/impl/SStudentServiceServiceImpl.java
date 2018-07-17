package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.SStudentMapper;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.service.SStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SStudentServiceServiceImpl implements SStudentService {

    @Autowired
    SStudentMapper sStudentMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sStudentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertOrUpdateByPrimaryKeySelective(SStudent sStudent) {
        if (sStudent.getId() == null) {
            return sStudentMapper.insertSelective(sStudent);
        } else {
            return sStudentMapper.updateByPrimaryKeySelective(sStudent);
        }
    }

    @Override
    public SStudent selectByPrimaryKey(Integer id) {
        return sStudentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SStudent> queryStudentByClassId(String param, Integer classId) {
        Map<String, Object> params = new HashMap<>();
        params.put("param", param);
        params.put("classId", classId);
        return sStudentMapper.queryStudentByClassId(params);
    }

    @Override
    public SStudent selectBySNumForValidate(Integer schoolId, String sNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("sNum", sNum);
        return sStudentMapper.selectBySNumForValidate(param);
    }

    @Override
    public List<Map<String, Object>> queryStudentByPhoneNum(String phone, String wechatId, String sNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        param.put("wechatId", wechatId);
        param.put("sNum", sNum);
        return sStudentMapper.queryStudentByPhoneNum(param);
    }
}
