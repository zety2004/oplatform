package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.PPage;

import java.util.List;

public interface PageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PPage record);

    int insertSelective(PPage record);

    PPage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PPage record);

    int updateByPrimaryKey(PPage record);

    List<PPage> queryPages();
}