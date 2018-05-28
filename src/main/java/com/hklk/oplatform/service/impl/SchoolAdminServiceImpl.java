package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.SchoolAdminMapper;
import com.hklk.oplatform.dao.inter.SchoolMapper;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.SchoolAdminService;
import com.hklk.oplatform.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SchoolAdminServiceImpl implements SchoolAdminService {

    @Autowired
    SchoolAdminMapper schoolAdminMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return schoolAdminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(SchoolAdmin record) {
        return schoolAdminMapper.insertSelective(record);
    }

    @Override
    public SchoolAdmin selectByPrimaryKey(Integer id) {
        return schoolAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public SchoolAdmin loginSchool(String account, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        if (pwd != null) {
            params.put("pwd", PasswordProvider.encrypt(pwd));
        }
        return schoolAdminMapper.loginSchool(params);
    }

    @Override
    public int updateByPrimaryKeySelective(SchoolAdmin record) {
        return schoolAdminMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SchoolAdmin> querySchoolAdminsBySchoolId(int schoolId) {
        return schoolAdminMapper.querySchoolAdminsBySchoolId(schoolId);
    }

    @Override
    public int querySchoolAdminsForCount(int schoolId) {
        return schoolAdminMapper.querySchoolAdminsForCount(schoolId);
    }

    @Override
    public SchoolAdmin querySchoolAdminsByName(String account) {
        return schoolAdminMapper.querySchoolAdminsByName(account);
    }
}
