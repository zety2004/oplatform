package com.hklk.oplatform.controller;

import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.provider.IdProvider;
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

@RequestMapping("/loginUser")
@Controller
public class LoginUserController extends BaseController {
    @Autowired
    UserService userService;

    @Resource
    private TokenManager tokenManager;

    @ResponseBody
    @RequestMapping("/login")
    public String loginUser(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            List<PPage> PPages = userService.queryUserPages(user.getId());
            StringBuffer rolePage = new StringBuffer();
            for (PPage pPage : PPages) {
                rolePage.append(pPage.getPageSrc());
            }
            LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), rolePage.toString());
            String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
            if (StringUtils.isBlank(token) || tokenManager.validate(token) == null) {// 没有登录的情况
                token = createToken(loginUser);
                addTokenInCookie(token, request, response);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), token);
        } else {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_NAME_OR_PWD_ERROR, StatusCode.getStatusMsg(StatusCode.LOGIN_NAME_OR_PWD_ERROR));
        }
    }

    private String createToken(LoginUser loginUser) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(token, loginUser);
        return token;
    }

    private void addTokenInCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        // Cookie添加token
        Cookie cookie = new Cookie(TokenManager.TOKEN, token);
        cookie.setPath("/");
        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryUserPages")
    public String queryUserPages(int id, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        List<PPage> PPages = userService.queryUserPages(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), PPages);
    }
}
