package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.RolePage;

import java.util.List;

public interface RolePageService {
    int addRolePage(RolePage rolePage);

    int updateRolePage(RolePage rolePage);

    int deleteRolePage(Integer id);

    /**
    * @author 曹良峰
    * @Description 查询角色所有页面
    * @Date 16:39 2018/5/16
    * @Param [roleId]
    * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
    **/
    List<PPage> selectPageByRoleId(Integer roleId);
}
