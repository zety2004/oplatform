package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.TeacherMessageMapper;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.TeacherMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherMessageServiceImpl implements TeacherMessageService {

    @Autowired
    TeacherMessageMapper teacherMessageMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teacherMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(TeacherMessage teacherMessage) {
        return teacherMessageMapper.insertSelective(teacherMessage);
    }

    @Override
    public TeacherMessage selectByPrimaryKey(Integer id) {
        return teacherMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TeacherMessage teacherMessage) {
        return teacherMessageMapper.updateByPrimaryKeySelective(teacherMessage);
    }

    @Override
    public int updateIsReadByTeacherId(Integer teacherId) {
        return teacherMessageMapper.updateIsReadByTeacherId(teacherId);
    }

    @Override
    public int updateIsReadById(Integer id) {
        return teacherMessageMapper.updateIsReadById(id);
    }

    @Override
    public PageTableForm<TeacherMessage> queryTeacherMessage(Integer teacherId, Integer isRead,Integer pageNum,Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        params.put("isRead", isRead);
        Page page = PageHelper.startPage(pageNum,pageSize,true);
        teacherMessageMapper.queryTeacherMessage(params);
        PageTableForm<TeacherMessage> pageTableForm = new PageTableForm<>(page);
        return pageTableForm;
    }
}
