package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.table.UserRoleKey;

import java.util.List;

public interface UserRoleService {
    int addUserRole(UserRoleKey userRoleKey);

    int deleteUserRole(UserRoleKey userRoleKey);

    List<User> selectUserByRoleId(Integer roleId);

    List<Role> selectRoleByUserId(Integer userId);
}
