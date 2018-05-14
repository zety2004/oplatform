package com.hklk.oplatform.controller;

import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/login")
@Controller
public class LoginUserController {
    @Autowired
    UserService userService;

    @RequestMapping("/loginUser")
    public String loginUser(HttpServletRequest request,
                            HttpServletResponse response, HttpSession session, Model model) {
        try {
            String name = request.getParameter("username");
            String password = request.getParameter("password");
            User result = userService.loginUser(name, password);
            if (result != null) {
                session.setAttribute("user", result);
                return "/jsp/index";
            } else {
                return "/jsp/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.SYS_ERROR, StatusCode.getStatusMsg(StatusCode.SYS_ERROR),
                    new Object());
        }
    }

    @ResponseBody
    @RequestMapping("/loginUserAjax")
    public String loginUser(HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        try {
            String name = request.getParameter("username");
            String password = request.getParameter("password");
            User result = userService.loginUser(name, password);
            if (result != null) {
                session.setAttribute("user", result);
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
            } else {
                return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.SYS_ERROR, StatusCode.getStatusMsg(StatusCode.SYS_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        session.setAttribute("user", null);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

}
