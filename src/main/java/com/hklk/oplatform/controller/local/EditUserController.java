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
import com.hklk.oplatform.util.ResultCode;
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
 * 运营账号管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editUser")
@Controller
public class EditUserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    /**
     * 2018/7/4 17:57
     * 查询管理员账号
     *
     * @param user    筛选参数
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryUsers")
    public String queryUsers(User user, int pageNum, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        PageTableForm<User> users = userService.queryUsers(user, pageNum, pageSize);
        return ResultUtils.successStr(users);
    }

    /**
     * 2018/7/4 17:57
     * 根据id查询管理员
     *
     * @param id 主键
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectUserById")
    public String queryUsers(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        User user = userService.selectByPrimaryKey(id);
        return ResultUtils.successStr(user);
    }

    /**
     * 2018/7/4 17:58
     * 添加运营管理
     *
     * @param user 管理员对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser(User user, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session) {

        User tmp = userService.selectByNameForValidate(user.getUsername());
        if (tmp == null) {
            userService.addUser(user);
            return ResultUtils.successStr();
        } else {
            return ResultUtils.warnStr(ResultCode.ADDUSER_USERNAME_EX);
        }
    }

    /**
     * 2018/7/4 17:58
     * 更新运营信息
     *
     * @param user 运营对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateUser")
    public String updateUser(User user, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        User tmp = userService.selectByNameForValidate(user.getUsername());
        if (tmp != null && tmp.getId() != user.getId()) {
            return ResultUtils.warnStr(ResultCode.ADDUSER_USERNAME_EX);
        } else {
            userService.updateUser(user);
            return ResultUtils.successStr();
        }
    }

    /**
     * 2018/7/4 17:59
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateUserPassword")
    public String updateUserPassword(String oldPassword, String newPassword, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        LoginUser loginUser = getLoginUser(request);
        if (loginUser == null) {
            return ResultUtils.warnStr(ResultCode.SSO_TOKEN_ERROR);
        }
        User temp = userService.selectByPrimaryKey(loginUser.getUserId());
        if (temp == null) {
            return ResultUtils.warnStr(ResultCode.USER_UNFIND);
        } else if (temp != null && temp.getPassword().equals(PasswordProvider.encrypt(oldPassword))) {
            User param = new User();
            param.setId(loginUser.getUserId());
            param.setPassword(newPassword);
            userService.updateUser(param);
            return ResultUtils.successStr();
        } else {
            return ResultUtils.warnStr(ResultCode.PASSWORD_ERROR);
        }
    }

    /**
     * 2018/7/4 18:00
     * 重置密码
     *
     * @param user 运营用户
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/resetUserPassword")
    public String resetUserPassword(User user, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        user.setPassword("hklk123456");
        userService.updateUser(user);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 18:00
     * 删除密码
     *
     * @param id 运营id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteUser")
    public String deleteUser(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        userService.deleteUser(id);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 18:01
     * 查询运营权限
     *
     * @param userId 用户id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryUserRole")
    public String queryUserRole(Integer userId, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<Role> users = userRoleService.selectRoleByUserId(userId);
        return ResultUtils.successStr(users);
    }

    /**
     * 2018/7/4 18:01
     * 删除用户权限
     *
     * @param userRoleKey 运营权限中间对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteUserRole")
    public String deleteUserRole(UserRoleKey userRoleKey, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        userRoleService.deleteUserRole(userRoleKey);
        return ResultUtils.successStr();
    }
}
