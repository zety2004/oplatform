package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface UserService {
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
    public PageTableForm<User> queryUsers(User user, int pageNum, int pageSize);

    /**
    * @author 曹良峰
    * @Description 查询用户所有权限页面
    * @Date 16:39 2018/5/16
    * @Param [id]
    * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
    **/
    List<PPage> queryUserPages(Integer id);
}
