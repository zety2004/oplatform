package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.UserMapper;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User loginUser(String username, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        if(pwd!=null){
            params.put("password", PasswordProvider.encrypt(pwd));
        }
        return userMapper.selectByNameAndPassword(params);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addUser(User user) {
        if (!"".equals(user.getPassword()) && user.getPassword() != null) {
            user.setPassword(PasswordProvider.encrypt(user.getPassword()));
        }
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        if (!"".equals(user.getPassword()) && user.getPassword() != null) {
            user.setPassword(PasswordProvider.encrypt(user.getPassword()));
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageTableForm<User> queryUsers(User user,int pageNum,int pageSize) {
        Page page = PageHelper.startPage(pageNum,pageSize,true);
        userMapper.selectUsers(user);
        PageTableForm<User> pageTableForm = new PageTableForm<User>(page);
        return pageTableForm;
    }

    @Override
    public List<PPage> queryUserPages(Integer id) {
        return userMapper.selectPageForUser(id);
    }

    @Override
    public int selectByNameForValidate(String name) {
        return userMapper.selectByNameForValidate(name);
    }

}
