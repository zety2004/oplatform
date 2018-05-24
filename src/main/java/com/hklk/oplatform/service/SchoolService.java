package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SchoolVo;

public interface SchoolService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    PageTableForm<SchoolVo> querySchools(String param,int pageNum, int pageSize);

    School selectSchoolByName(String name);
}
