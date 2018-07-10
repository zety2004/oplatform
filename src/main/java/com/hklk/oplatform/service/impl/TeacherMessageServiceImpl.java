package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.TeacherMessageMapper;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.service.TeacherMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<TeacherMessage> queryTeacherMessage(Integer teacherId) {
        return teacherMessageMapper.queryTeacherMessage(teacherId);
    }
}
