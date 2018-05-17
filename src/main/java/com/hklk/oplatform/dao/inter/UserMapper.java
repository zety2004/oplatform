package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByNameAndPassword(Map<String,String> map);

    List<User> selectUsers();

    List<PPage> selectPageForUser(Integer id);
}