package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Page;
import com.hklk.oplatform.entity.table.RolePage;

import java.util.List;

public interface RolePageService {

    int addRolePage(RolePage rolePage);

    int updateRolePage(RolePage rolePage);

    int deleteRolePage(Integer id);

    List<Page> selectPageByRoleId(Integer roleId);
}
