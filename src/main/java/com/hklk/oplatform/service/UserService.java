package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Page;
import com.hklk.oplatform.entity.table.User;

import java.util.List;

public interface UserService {
    User loginUser(String username,String pwd);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);

    List<User> queryUsers();

    List<Page> queryUserPages(Integer id);
}
