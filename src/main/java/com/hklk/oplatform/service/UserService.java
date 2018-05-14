package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.User;

public interface UserService {
    User loginUser(String username,String pwd);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);
}
