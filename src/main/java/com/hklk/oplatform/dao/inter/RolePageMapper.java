package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.RolePage;

import java.util.List;

public interface RolePageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePage record);

    int insertSelective(RolePage record);

    RolePage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePage record);

    int updateByPrimaryKey(RolePage record);

    List<PPage> selectPageByRoleId(Integer roleId);
}