package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.TeacherMessage;

import java.util.List;
import java.util.Map;

public interface TeacherMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherMessage record);

    int insertSelective(TeacherMessage record);

    TeacherMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherMessage record);

    int updateIsReadByTeacherId(Integer teacherId);

    int updateIsReadById(Integer id);

    List<TeacherMessage> queryTeacherMessage(Map<String, Object> param);
}