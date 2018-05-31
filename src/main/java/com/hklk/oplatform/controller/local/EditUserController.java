package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Role;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.table.UserRoleKey;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.AuthenticationRpcService;
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

@LocalLoginRepository
@RequestMapping("/editUser")
@Controller
public class EditUserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    AuthenticationRpcService authenticationRpcService;

    @ResponseBody
    @RequestMapping("/queryUsers")
    public String queryUsers(User user, int pageNum, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        PageTableForm<User> users = userService.queryUsers(user, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), users);
    }

    @ResponseBody
    @RequestMapping("/selectUserById")
    public String queryUsers(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        User user = userService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), user);
    }


    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser(User user, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {

        User tmp = userService.selectByNameForValidate(user.getUsername());
        if (tmp == null) {
            userService.addUser(user);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, StatusCode.getStatusMsg(StatusCode.ADDUSER_USERNAME_EX));
        }
    }

    @ResponseBody
    @RequestMapping("/updateUser")
    public String updateUser(User user, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        User tmp = userService.selectByNameForValidate(user.getUsername());
        if (tmp != null && tmp.getId() != user.getId()) {
            return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, StatusCode.getStatusMsg(StatusCode.ADDUSER_USERNAME_EX));
        } else {
            userService.updateUser(user);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/updateUserPassword")
    public String updateUserPassword(String oldPassword, String newPassword, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        LoginUser loginUser = authenticationRpcService.findAuthInfo(tokenManager.userTokenKey, token);
        if (loginUser == null) {
            return ToolUtil.buildResultStr(StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
        }
        User temp = userService.selectByPrimaryKey(loginUser.getUserId());
        if (temp == null) {
            return ToolUtil.buildResultStr(StatusCode.USER_UNFIND, StatusCode.getStatusMsg(StatusCode.USER_UNFIND));
        } else if (temp != null && temp.getPassword().equals(PasswordProvider.encrypt(oldPassword))) {
            User param = new User();
            param.setId(loginUser.getUserId());
            param.setPassword(newPassword);
            userService.updateUser(param);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.PASSWORD_ERROR, StatusCode.getStatusMsg(StatusCode.PASSWORD_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/resetUserPassword")
    public String resetUserPassword(User user, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        user.setPassword("hklk123456");
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
