package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.STeacherMapper;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.TeacherVo;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.STeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class STeacherServiceServiceImpl implements STeacherService {

    @Autowired
    STeacherMapper sTeacherMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sTeacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertOrUpdateByPrimaryKeySelective(STeacher sTeacher) {
        if (sTeacher.getId() != null) {
            return sTeacherMapper.updateByPrimaryKeySelective(sTeacher);
        } else {
            return sTeacherMapper.insertSelective(sTeacher);
        }
    }

    @Override
    public STeacher selectBySTeacher(STeacher sTeacher) {
        return sTeacherMapper.selectBySTeacher(sTeacher);
    }

    @Override
    public PageTableForm<STeacher> queryTeacherBySchoolId(Integer schoolId, String param, int pageNum, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        params.put("param", param);
        Page<STeacher> page = PageHelper.startPage(pageNum, pageSize, true);
        sTeacherMapper.queryTeacherBySchoolId(params);
        PageTableForm<STeacher> pageTableForm = new PageTableForm<>(page);
        return pageTableForm;
    }

    @Override
    public List<STeacher> queryTeacherBySchoolIdForList(Integer schoolId) {
        Map<String, Object> params = new HashMap<>();
        params.put("schoolId", schoolId);
        return sTeacherMapper.queryTeacherBySchoolId(params);
    }

    @Override
    public TeacherVo loginTeacher(String account, String pwd) {
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        if (pwd != null) {
            params.put("pwd", PasswordProvider.encrypt(pwd));
        }
        return sTeacherMapper.loginTeacher(params);
    }
}
