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

    List<SStudent> queryStudentByClassId(Map<String, Object> params);

    SStudent selectBySNumForValidate(Map<String, Object> map);

    List<Map<String,Object>> queryStudentByPhoneNum(Map<String, Object> params);
}