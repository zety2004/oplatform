package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User loginUser(String username, String pwd) {
        return null;
    }
}
