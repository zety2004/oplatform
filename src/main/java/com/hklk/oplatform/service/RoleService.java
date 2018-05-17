package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Role;

import java.util.List;

public interface RoleService {

    /**
    * @author 曹良峰
    * @Description 查询角色列表
    * @Date 16:39 2018/5/16
    * @Param []
    * @Return java.util.List<com.hklk.oplatform.entity.table.Role>
    **/
    List<Role> queryRoles();

    int addRole(Role user);

    int updateRole(Role user);

    int deleteRole(Integer id);
}
