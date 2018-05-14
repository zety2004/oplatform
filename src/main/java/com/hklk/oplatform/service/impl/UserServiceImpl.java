package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User loginUser(String username, String pwd) {
        return userMapper.selectByNameAndPassword(username, pwd);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectUsers();
    }
}
