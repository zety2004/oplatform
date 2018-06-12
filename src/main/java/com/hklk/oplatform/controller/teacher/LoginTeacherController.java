package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginSchool;
import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.SchoolAdminVo;
import com.hklk.oplatform.entity.vo.TeacherVo;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.AuthenticationRpcService;
import com.hklk.oplatform.service.STeacherService;
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

@RequestMapping("/loginTeacher")
@Controller
public class LoginTeacherController extends BaseController {
    @Resource
    private TokenManager tokenManager;
    @Autowired
    STeacherService sTeacherService;

    @ResponseBody
    @RequestMapping("/login")
    public String loginTeacher(@RequestParam(value = "account") String account, @RequestParam(value = "pwd") String pwd, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        TeacherVo teacherVo = sTeacherService.loginTeacher(account, pwd);
        if (teacherVo != null && teacherVo.getStatus() == 1 && teacherVo.getSchoolStatus() == 1) {
            LoginTeacher loginTeacher = new LoginTeacher(teacherVo.getId(), teacherVo.getPhone(), teacherVo.gettName(), "", teacherVo.getSchoolId());
            String token = createToken(loginTeacher);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), token);
        } else if (teacherVo != null && teacherVo.getSchoolStatus() != 1) {
            System.out.println("学校停用");
            return ToolUtil.buildResultStr(StatusCode.SCHOOL_STATUS, StatusCode.getStatusMsg(StatusCode.SCHOOL_STATUS));
        } else if (teacherVo != null && teacherVo.getStatus() != 1) {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_DISABLE, StatusCode.getStatusMsg(StatusCode.LOGIN_DISABLE));
        } else {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_NAME_OR_PWD_ERROR, StatusCode.getStatusMsg(StatusCode.LOGIN_NAME_OR_PWD_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/updateTeacherPassword")
    public String updateUserPassword(String oldPassword, String newPassword, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        LoginTeacher loginTeacher = getLoginTeacher(request);
        if (loginTeacher == null) {
            return ToolUtil.buildResultStr(StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
        }
        STeacher sTeacher = new STeacher();
        sTeacher.setId(loginTeacher.getTeacherId());
        STeacher temp = sTeacherService.selectBySTeacher(sTeacher);
        if (temp == null) {
            return ToolUtil.buildResultStr(StatusCode.USER_UNFIND, StatusCode.getStatusMsg(StatusCode.USER_UNFIND));
        } else if (temp != null && temp.getPwd().equals(PasswordProvider.encrypt(oldPassword))) {
            STeacher param = new STeacher();
            param.setId(loginTeacher.getTeacherId());
            param.setPwd(newPassword);
            sTeacherService.insertOrUpdateByPrimaryKeySelective(param);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.PASSWORD_ERROR, StatusCode.getStatusMsg(StatusCode.PASSWORD_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.teacherTokenKey, token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.teacherTokenKey, token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    private String createToken(LoginTeacher loginTeacher) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.teacherTokenKey, token, loginTeacher);
        return token;
    }



}
