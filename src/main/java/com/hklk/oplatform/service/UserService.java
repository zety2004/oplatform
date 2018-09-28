package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface UserService {
    /**
    * @author 曹良峰
    * @Description 登陆
    * @Date 16:05 2018/5/24
    * @Param [username, pwd]
    * @Return com.hklk.oplatform.entity.table.User
    **/
    User loginUser(String username,String pwd);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);

    User selectByPrimaryKey(Integer id);

    /**
    * @author 曹良峰
    * @Description 查询用户列表
    * @Date 16:28 2018/5/16
    * @Param []
    * @Return java.util.List<com.hklk.oplatform.entity.table.User>
    **/
    PageTableForm<User> queryUsers(User user, int pageNum, int pageSize);

    /**
    * @author 曹良峰
    * @Description 查询用户所有权限页面
    * @Date 16:39 2018/5/16
    * @Param [id]
    * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
    **/
    List<PPage> queryUserPages(Integer id);

    /**
    * @author 曹良峰
    * @Description 验证用户名是否存在
    * @Date 10:31 2018/5/21
    * @Param [name]
    * @Return int
    **/
    User selectByNameForValidate(String name);
}
