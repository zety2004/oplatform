package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.comm.LoginSchool;
import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.SchoolAdminService;
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
    SchoolAdminService schoolAdminService;
    @Resource
    private TokenManager tokenManager;

    @ResponseBody
    @RequestMapping("/login")
    public String loginSchool(@RequestParam(value = "account", required = false) String account, @RequestParam(value = "pwd", required = false) String pwd, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        SchoolAdmin schoolAdmin = schoolAdminService.loginSchool(account, pwd);
        LoginSchool loginSchool = new LoginSchool(schoolAdmin.getId(), schoolAdmin.getAccount(), schoolAdmin.getNickname(), "");
        String token = createToken(loginSchool);
        System.out.println(token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), token);
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.schoolTokenKey, token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    private String createToken(LoginSchool loginSchool) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.schoolTokenKey, token, loginSchool);
        return token;
    }
}
