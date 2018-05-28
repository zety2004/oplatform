package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.CookieUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.StringUtils;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/loginSchool")
@Controller
public class LoginSchoolController extends BaseController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public String loginSchool(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        System.out.println("密码加密:" + PasswordProvider.encrypt(password));
        User user = userService.loginUser(username, password);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));

    }
}
