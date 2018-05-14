package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByNameAndPassword(String username,String password);

    List<User> selectUsers();
}