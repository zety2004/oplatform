package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.comm.LoginUser;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营登陆管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@RequestMapping("/loginUser")
@Controller
public class LoginUserController extends BaseController {
    @Autowired
    UserService userService;

    /**
     * 2018/7/4 18:02
     * 用户登陆
     *
     * @param username 用户名
     * @param password 用户密码
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/login")
    public String loginUser(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        User user = userService.loginUser(username, password);
        if (user != null && (user.getState() == 1 || user.getState() == 9)) {
            List<PPage> pPages = userService.queryUserPages(user.getId());
            LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), user.getNickname(), "");
            if (pPages.get(0) != null) {
                StringBuffer rolePage = new StringBuffer();
                for (PPage pPage : pPages) {
                    rolePage.append(pPage.getPageSrc());
                }
                loginUser.setRolePage(rolePage.toString());
            }
            String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
            if (StringUtils.isBlank(token) || tokenManager.validate(tokenManager.userTokenKey, token, LoginUser.class) == null) {// 没有登录的情况
                token = createToken(loginUser);
                addTokenInCookie(token, request, response);
            }
            return ResultUtils.successStr(token);
        } else if (user != null && user.getState() != 1) {
            return ResultUtils.warnStr(ResultCode.LOGIN_DISABLE);
        } else {
            return ResultUtils.warnStr(ResultCode.LOGIN_NAME_OR_PWD_ERROR);
        }
    }


    private String createToken(LoginUser loginUser) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.userTokenKey, token, loginUser);
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

    /**
     * 2018/7/4 18:03
     * 退出登陆
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.userTokenKey, token);
        return ResultUtils.successStr();
    }


}
