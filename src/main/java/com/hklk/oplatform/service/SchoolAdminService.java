package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.SchoolAdminVo;

import java.util.List;

public interface SchoolAdminService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SchoolAdmin record);

    SchoolAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolAdmin record);

    SchoolAdminVo loginSchool(String account, String pwd);

    /**
     * @author 曹良峰
     * @Description 根据学校id查询管理员列表
     * @Date 16:04 2018/5/24
     * @Param [schoolId]
     * @Return java.util.List<com.hklk.oplatform.entity.table.SchoolAdmin>
     **/
    List<SchoolAdmin> querySchoolAdminsBySchoolId(int schoolId);

    /**
     * @author 曹良峰
     * @Description 查询学校管理员数量
     * @Date 16:03 2018/5/24
     * @Param [schoolId]
     * @Return int
     **/
    int querySchoolAdminsForCount(int schoolId);

    /**
     * @author 曹良峰
     * @Description 根据登陆名查询管理员账号
     * @Date 16:02 2018/5/24
     * @Param [account]
     * @Return com.hklk.oplatform.entity.table.SchoolAdmin
     **/
    SchoolAdmin querySchoolAdminsByName(String account);
}
