package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.table.UserRoleKey;

import java.util.List;

public interface UserRoleService {
    int addUserRole(UserRoleKey userRoleKey);

    int deleteUserRole(UserRoleKey userRoleKey);

    /**
    * @author 曹良峰
    * @Description 查询角色下的所有用户
    * @Date 16:39 2018/5/16
    * @Param [roleId]
    * @Return java.util.List<com.hklk.oplatform.entity.table.User>
    **/
    List<User> selectUserByRoleId(Integer roleId);

    /**
    * @author 曹良峰
    * @Description 查询用户所有角色
    * @Date 16:28 2018/5/16
    * @Param [userId]
    * @Return java.util.List<com.hklk.oplatform.entity.table.Role>
    **/
    List<Role> selectRoleByUserId(Integer userId);
}
