package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.*;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.RolePageService;
import com.hklk.oplatform.service.RoleService;
import com.hklk.oplatform.service.UserRoleService;
import com.hklk.oplatform.util.ResultUtils;
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

/**
 * 用户权限管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editRole")
@Controller
public class EditRoleController extends BaseController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePageService rolePageService;

    @ResponseBody
    @RequestMapping("/queryRoles")
    public String queryRoles(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {

        List<Role> roles = roleService.queryRoles();
        return ResultUtils.successStr(roles);
    }

    @ResponseBody
    @RequestMapping("/addRole")
    public String addRole(Role role, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {
        roleService.addRole(role);
        return ResultUtils.successStr();
    }

    @ResponseBody
    @RequestMapping("/updateRole")
    public String updateRole(Role role, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        roleService.updateRole(role);
        return ResultUtils.successStr();
    }


    @ResponseBody
    @RequestMapping("/deleteRole")
    public String deleteUser(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        roleService.deleteRole(id);
        return ResultUtils.successStr();
    }

    @ResponseBody
    @RequestMapping("/queryRoleUser")
    public String queryRoleUser(int roleId, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<User> users = userRoleService.selectUserByRoleId(roleId);
        return ResultUtils.successStr(users);
    }

    @ResponseBody
    @RequestMapping("/addUserRole")
    public String addUserRole(UserRoleKey userRoleKey, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        userRoleService.addUserRole(userRoleKey);
        return ResultUtils.successStr();
    }

    @ResponseBody
    @RequestMapping("/queryRolePage")
    public String queryRolePage(int roleId, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<PPage> PPages = rolePageService.selectPageByRoleId(roleId);
        return ResultUtils.successStr(PPages);
    }

    @ResponseBody
    @RequestMapping("/addRolePage")
    public String addRolePage(RolePage rolePage, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        rolePageService.addRolePage(rolePage);
        return ResultUtils.successStr();
    }
}
