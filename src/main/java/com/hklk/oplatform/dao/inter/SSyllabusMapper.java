package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.SSyllabus;

public interface SSyllabusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SSyllabus record);

    int insertSelective(SSyllabus record);

    SSyllabus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SSyllabus record);

    int updateByPrimaryKey(SSyllabus record);
}