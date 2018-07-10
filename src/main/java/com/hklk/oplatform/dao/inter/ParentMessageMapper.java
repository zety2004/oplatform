package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.TeacherMessage;

import java.util.List;
import java.util.Map;

public interface ParentMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ParentMessage record);

    int insertSelective(ParentMessage record);

    ParentMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParentMessage record);

    int updateByPrimaryKey(ParentMessage record);

    List<ParentMessage> queryParentMessage(Map<String, Object> param);
}