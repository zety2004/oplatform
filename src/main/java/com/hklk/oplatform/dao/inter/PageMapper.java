package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Page;
import com.hklk.oplatform.entity.table.Role;

import java.util.List;

public interface PageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Page record);

    int insertSelective(Page record);

    Page selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Page record);

    int updateByPrimaryKey(Page record);

    List<Page> queryPages();
}