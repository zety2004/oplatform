package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.UserRoleKey;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);
}