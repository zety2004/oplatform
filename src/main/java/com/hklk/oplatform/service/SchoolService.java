package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SchoolVo;

public interface SchoolService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    /**
    * @author 曹良峰
    * @Description 查询学校列表分页数据
    * @Date 16:01 2018/5/24
    * @Param [param, pageNum, pageSize]
    * @Return com.hklk.oplatform.entity.vo.PageTableForm<com.hklk.oplatform.entity.vo.SchoolVo>
    **/
    PageTableForm<SchoolVo> querySchools(String param,int pageNum, int pageSize);

    /**
    * @author 曹良峰
    * @Description 根据学校名称查询学校
    * @Date 16:01 2018/5/24
    * @Param [name]
    * @Return com.hklk.oplatform.entity.table.School
    **/
    School selectSchoolByName(String name);
}
