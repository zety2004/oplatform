package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface TeacherMessageService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TeacherMessage teacherMessage);

    TeacherMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherMessage teacherMessage);

    int updateIsReadByTeacherId(Integer teacherId);

    int updateIsReadById(Integer id);

    PageTableForm<TeacherMessage> queryTeacherMessage(Integer teacherId, Integer isRead,Integer pageNum,Integer pageSize);
}