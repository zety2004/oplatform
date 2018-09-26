package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.DicMapper;
import com.hklk.oplatform.dao.inter.STeacherMapper;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.TeacherVo;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.STeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class STeacherServiceServiceImpl implements STeacherService {

    @Autowired
    STeacherMapper sTeacherMapper;
    @Autowired
    DicMapper dicMapper;

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

    @Override
    public int queryCurriculumByTeacherId(Integer id) {
        return sTeacherMapper.queryCurriculumByTeacherId(id);
    }

    @Override
    public synchronized Map<String, Object> insertBatchTeacher(List<STeacher> sTeachers, Integer schoolId) {

        List<Map<String, Object>> errorInsert = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        sTeachers.forEach(sTeacher -> {
            if (sTeacher.gettName() == null || "".equals(sTeacher.gettName())) {
                Map<String, Object> importMap = new HashMap<>();
                importMap.put("tName", sTeacher.gettName());
                importMap.put("phone", sTeacher.getPhone());
                importMap.put("reason", "缺少关键字段，请检查后重新导入");
                errorInsert.add(importMap);
                index.getAndIncrement();
                return;
            }
            STeacher param = new STeacher();
            param.setSchoolId(schoolId);
            param.setPhone(sTeacher.getPhone());
            STeacher temp = sTeacherMapper.selectBySTeacher(param);
            if (temp != null) {
                Map<String, Object> importMap = new HashMap<>();
                importMap.put("tName", sTeacher.gettName());
                importMap.put("phone", sTeacher.getPhone());
                importMap.put("reason", "该老师已存在系统中");
                errorInsert.add(importMap);
                index.getAndIncrement();
            } else {
                sTeacher.setSchoolId(schoolId);
                sTeacher.setTag(this.getTeacherTag().toString());
                this.insertOrUpdateByPrimaryKeySelective(sTeacher);
            }
        });
        Map<String, Object> result = new HashMap<>();
        result.put("index", index.intValue());
        result.put("errorInsert", errorInsert);

        return result;
    }

    @Override
    public StringBuffer getTeacherTag() {

        List<Map<String, Object>> tags = dicMapper.queryForList("4");
        StringBuffer tag = new StringBuffer();
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Integer index = (int) (1 + Math.random() * (tags.size() - 1 + 1));
            while (indexs.contains(index)) {
                index = (int) (1 + Math.random() * (tags.size() - 1 + 1));
            }
            indexs.add(index);
            if (i == 2) {
                tag.append(tags.get(index).get("name").toString());
            } else {
                tag.append(tags.get(index).get("name").toString()).append(",");
            }
        }

        return tag;
    }
}
