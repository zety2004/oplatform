package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.TeacherMessage;

import java.util.List;

public interface ParentMessageService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ParentMessage parentMessage);

    ParentMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParentMessage parentMessage);

    int updateIsReadByStudentId(Integer studentId);

    int updateIsReadById(Integer id);

    List<ParentMessage> queryParentMessage(Integer studentId, Integer isRead);
}