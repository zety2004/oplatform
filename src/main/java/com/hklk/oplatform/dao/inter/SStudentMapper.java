package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SStudent;

import java.util.List;
import java.util.Map;

public interface SStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SStudent record);

    int insertSelective(SStudent record);

    SStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SStudent record);

    int updateByPrimaryKey(SStudent record);

    List<SStudent> queryStudentByClassId(Integer classId);

    SStudent selectBySNumForValidate(Map<String, Object> map);
}