package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.TeacherMessage;

import java.util.List;

public interface TeacherMessageService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TeacherMessage teacherMessage);

    TeacherMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherMessage teacherMessage);

    List<TeacherMessage> queryTeacherMessage(Integer teacherId);
}