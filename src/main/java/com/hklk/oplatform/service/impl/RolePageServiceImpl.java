package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.RolePageMapper;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.RolePage;
import com.hklk.oplatform.service.RolePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RolePageServiceImpl implements RolePageService {

    @Autowired
    RolePageMapper rolePageMapper;

    @Override
    public int addRolePage(RolePage rolePage) {
        return rolePageMapper.insertSelective(rolePage);
    }

    @Override
    public int updateRolePage(RolePage rolePage) {
        return rolePageMapper.updateByPrimaryKeySelective(rolePage);
    }

    @Override
    public int deleteRolePage(Integer id) {
        return rolePageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PPage> selectPageByRoleId(Integer roleId) {
        return rolePageMapper.selectPageByRoleId(roleId);
    }
}
