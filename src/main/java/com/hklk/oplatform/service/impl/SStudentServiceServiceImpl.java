package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.SStudentMapper;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.ImportStudentVo;
import com.hklk.oplatform.service.SClassService;
import com.hklk.oplatform.service.SStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SStudentServiceServiceImpl implements SStudentService {

    @Autowired
    SStudentMapper sStudentMapper;
    @Autowired
    SClassService sClassService;

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

    @Override
    public synchronized Map<String, Object> insertBatchStudent(List<SStudent> sStudents, Integer schoolId, Integer classId) {
        List<ImportStudentVo> errorInsert = new ArrayList<>();
        int index = 0;
        for (SStudent sStudent : sStudents) {
            if (sStudent.getsNum() == null || "".equals(sStudent.getsNum())) {
                ImportStudentVo importStudentVo = new ImportStudentVo();
                importStudentVo.setFullName(sStudent.getFullName());
                importStudentVo.setsNum(sStudent.getsNum());
                importStudentVo.setReason("缺少关键字段，请检查后重新导入");
                errorInsert.add(importStudentVo);
                index++;
                continue;
            }
            SStudent temp = this.selectBySNumForValidate(schoolId, sStudent.getsNum());
            if (sStudent.getId() == null && temp != null) {
                ImportStudentVo importStudentVo = new ImportStudentVo();
                importStudentVo.setFullName(sStudent.getFullName());
                importStudentVo.setsNum(sStudent.getsNum());
                SClass sClass = sClassService.selectByPrimaryKey(temp.getClassId());
                importStudentVo.setReason("该学生已存在于" + sClass.getName() + "班中");
                errorInsert.add(importStudentVo);
                index++;
            } else {
                sStudent.setClassId(classId);
                this.insertOrUpdateByPrimaryKeySelective(sStudent);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("index", index);
        result.put("errorInsert", errorInsert);
        return result;
    }
}
