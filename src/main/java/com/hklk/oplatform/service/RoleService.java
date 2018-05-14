package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Role;

import java.util.List;

public interface RoleService {

    List<Role> queryRoles();

    int addRole(Role user);

    int updateRole(Role user);

    int deleteRole(Integer id);
}
