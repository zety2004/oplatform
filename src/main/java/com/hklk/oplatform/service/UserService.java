package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.User;

public interface UserService {
    User loginUser(String username,String pwd);
}
