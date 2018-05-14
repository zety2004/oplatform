package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.RoleMapper;
import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.RoleService;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> queryRoles() {
        return roleMapper.selectRoles();
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
