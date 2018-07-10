package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.service.TeacherMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class TeacherMessageServiceImpl implements TeacherMessageService {

    @Autowired
    TeacherMessageService teacherMessageService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teacherMessageService.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(TeacherMessage teacherMessage) {
        return teacherMessageService.insertSelective(teacherMessage);
    }

    @Override
    public TeacherMessage selectByPrimaryKey(Integer id) {
        return teacherMessageService.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TeacherMessage teacherMessage) {
        return teacherMessageService.updateByPrimaryKeySelective(teacherMessage);
    }

    @Override
    public List<TeacherMessage> queryTeacherMessage(Integer teacherId) {
        return teacherMessageService.queryTeacherMessage(teacherId);
    }
}
