package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User loginUser(String username, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", pwd);
        return userMapper.selectByNameAndPassword(params);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
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

    @Override
    public List<PPage> queryUserPages(Integer id) {
        return userMapper.selectPageForUser(id);
    }
}
