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
        if (sStudent.getId() != null) {
            return sStudentMapper.insertSelective(sStudent);
        } else {
            return sStudentMapper.updateByPrimaryKeySelective(sStudent);
        }
    }

    @Override
    public List<SStudent> queryStudentByClassId(Integer classId) {
        return sStudentMapper.queryStudentByClassId(classId);
    }

    @Override
    public SStudent selectBySNumForValidate(Integer schoolId, String sNum) {
        Map<String, Object> param = new HashMap<>();
        param.put("schoolId", schoolId);
        param.put("sNum", sNum);
        return sStudentMapper.selectBySNumForValidate(param);
    }
}
