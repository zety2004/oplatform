package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.comm.LoginSchool;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.SchoolAdminVo;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.SchoolAdminService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/loginSchool")
@Controller
public class LoginSchoolController extends BaseController {
    @Autowired
    SchoolAdminService schoolAdminService;
    @Resource
    private TokenManager tokenManager;

    @ResponseBody
    @RequestMapping("/login")
    public String loginSchool(@RequestParam(value = "account") String account, @RequestParam(value = "pwd") String pwd, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        SchoolAdminVo schoolAdmin = schoolAdminService.loginSchool(account, pwd);
        if (schoolAdmin != null && schoolAdmin.getStatus() == 1 && schoolAdmin.getSchoolStatus() == 1) {
            LoginSchool loginSchool = new LoginSchool(schoolAdmin.getId(), schoolAdmin.getAccount(), schoolAdmin.getNickname(), "", schoolAdmin.getSchoolId());
            String token = createToken(loginSchool);
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("nickName", schoolAdmin.getNickname());
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
        } else if (schoolAdmin != null && schoolAdmin.getSchoolStatus() != 1) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOL_STATUS, StatusCode.getStatusMsg(StatusCode.SCHOOL_STATUS));
        } else if (schoolAdmin != null && schoolAdmin.getStatus() != 1) {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_DISABLE, StatusCode.getStatusMsg(StatusCode.LOGIN_DISABLE));
        } else {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_NAME_OR_PWD_ERROR, StatusCode.getStatusMsg(StatusCode.LOGIN_NAME_OR_PWD_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/updateSchoolAdminPassword")
    public String updateSchoolAdminPassword(String oldPassword, String newPassword, HttpServletRequest request,
                                            HttpServletResponse response, HttpSession session) {
        LoginSchool loginSchool = getLoginSchool(request);
        if (loginSchool == null) {
            return ToolUtil.buildResultStr(StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
        }
        SchoolAdmin temp = schoolAdminService.selectByPrimaryKey(loginSchool.getSchoolAdminId());
        if (temp == null) {
            return ToolUtil.buildResultStr(StatusCode.USER_UNFIND, StatusCode.getStatusMsg(StatusCode.USER_UNFIND));
        } else if (temp != null && temp.getPwd().equals(PasswordProvider.encrypt(oldPassword))) {
            SchoolAdmin param = new SchoolAdmin();
            param.setId(loginSchool.getSchoolAdminId());
            param.setPwd(newPassword);
            schoolAdminService.updateByPrimaryKeySelective(param);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.PASSWORD_ERROR, StatusCode.getStatusMsg(StatusCode.PASSWORD_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/updateSchoolNickName")
    public String updateSchoolNickName(String nickName, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        LoginSchool loginSchool = getLoginSchool(request);
        if (loginSchool == null) {
            return ToolUtil.buildResultStr(StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
        }
        loginSchool.setSchoolAdminId(2);
        SchoolAdmin param = new SchoolAdmin();
        param.setId(loginSchool.getSchoolAdminId());
        param.setNickname(nickName);
        schoolAdminService.updateByPrimaryKeySelective(param);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
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
