package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.dao.inter.UserRoleMapper;
import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.table.UserRoleKey;
import com.hklk.oplatform.service.UserRoleService;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public int addUserRole(UserRoleKey userRoleKey) {
        return userRoleMapper.insertSelective(userRoleKey);
    }

    @Override
    public int deleteUserRole(UserRoleKey userRoleKey) {
        return userRoleMapper.deleteByPrimaryKey(userRoleKey);
    }

    @Override
    public List<User> selectUserByRoleId(Integer roleId) {
        return userRoleMapper.selectUserByRoleId(roleId);
    }

    @Override
    public List<Role> selectRoleByUserId(Integer userId) {
        return userRoleMapper.selectRoleByUserId(userId);
    }
}
