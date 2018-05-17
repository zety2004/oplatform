package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.vo.PageTableForm;

public interface SchoolService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    PageTableForm<School> querySchools(int pageNum, int pageSize);
}
