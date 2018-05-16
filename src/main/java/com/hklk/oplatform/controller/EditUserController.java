package com.hklk.oplatform.controller;

import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.table.UserRoleKey;
import com.hklk.oplatform.service.UserRoleService;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/editUser")
@Controller
public class EditUserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @ResponseBody
    @RequestMapping("/queryUsers")
    public String queryUsers(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        List<User> users = userService.queryUsers();
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), users);
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser(User user, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {
        userService.addUser(user);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updateUser")
    public String updateUser(User user, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        userService.updateUser(user);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }


    @ResponseBody
    @RequestMapping("/deleteUser")
    public String deleteUser(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        userService.deleteUser(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryUserRole")
    public String queryUserRole(Integer userId, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<Role> users = userRoleService.selectRoleByUserId(userId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), users);
    }

    @ResponseBody
    @RequestMapping("/deleteUserRole")
    public String deleteUserRole(UserRoleKey userRoleKey, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        userRoleService.deleteUserRole(userRoleKey);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
