package com.hklk.oplatform.controller;

import com.hklk.oplatform.comm.*;
import com.hklk.oplatform.service.AuthenticationRpcService;
import com.hklk.oplatform.util.ResultCode;
import com.hklk.oplatform.util.ResultUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import com.hklk.oplatform.util.editor.DateEditor;
import com.hklk.oplatform.util.editor.DoubleEditor;
import com.hklk.oplatform.util.editor.IntegerEditor;
import com.hklk.oplatform.util.editor.LongEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public abstract class BaseController {
    @Resource
    public TokenManager tokenManager;
    @Autowired
    AuthenticationRpcService authenticationRpcService;

    protected int pageSize = 20;
    protected int pageNum = 1;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    public LoginSchool getLoginSchool(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        LoginSchool loginSchool = authenticationRpcService.findAuthInfo(tokenManager.schoolTokenKey, token, LoginSchool.class);
        return loginSchool;
    }

    public LoginTeacher getLoginTeacher(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        LoginTeacher loginTeacher = authenticationRpcService.findAuthInfo(tokenManager.teacherTokenKey, token, LoginTeacher.class);
        return loginTeacher;
    }

    public LoginParent getLoginParent(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        LoginParent loginParent = authenticationRpcService.findAuthInfo(tokenManager.parentTokenKey, token, LoginParent.class);
        return loginParent;
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        LoginUser loginUser = authenticationRpcService.findAuthInfo(tokenManager.userTokenKey, token, LoginUser.class);
        return loginUser;
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        request.setAttribute("exception", e);
        return ResultUtils.warnStr(ResultCode.SYS_ERROR);
    }
}
