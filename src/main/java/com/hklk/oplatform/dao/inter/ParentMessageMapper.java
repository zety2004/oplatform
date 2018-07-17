package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.ParentMessage;

import java.util.List;
import java.util.Map;

public interface ParentMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ParentMessage record);

    int insertSelective(ParentMessage record);

    ParentMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParentMessage record);

    int updateIsReadByStudentId(Integer studentId);

    int updateIsReadById(Integer id);

    List<ParentMessage> queryParentMessage(Map<String, Object> params);
}