package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.TeacherVo;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.STeacherService;
import com.hklk.oplatform.service.SchoolAdminService;
import com.hklk.oplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * 老师登陆模块
 *
 * @author 曹良峰
 * @since 1.0
 */
@RequestMapping("/loginTeacher")
@Controller
public class LoginTeacherController extends BaseController {
    @Resource
    private TokenManager tokenManager;
    @Autowired
    STeacherService sTeacherService;
    @Autowired
    SchoolAdminService schoolAdminService;

    /**
     * 登陆
     * 账号的登陆，登陆成功后保存token发送到前端。
     *
     * @param account 用户名
     * @param pwd     密码
     * @return {"resultCode":200,"resultMsg":"成功"}  code: 1008 学校禁用  1000 账号禁用 1001 账号或密码错误
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/login")
    public String loginTeacher(@RequestParam(value = "account") String account, @RequestParam(value = "pwd") String pwd, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        TeacherVo teacherVo = sTeacherService.loginTeacher(account, pwd);
        if (teacherVo != null && teacherVo.getStatus() == 1 && teacherVo.getSchoolStatus() == 1) {
            LoginTeacher loginTeacher = new LoginTeacher(teacherVo.getId(), teacherVo.getPhone(), teacherVo.getNickname(), "", teacherVo.getSchoolId(), teacherVo.getSchoolName(), teacherVo.getSchoolLogo(), teacherVo.getHeadIco(), teacherVo.getIntroduction(), teacherVo.getTag());
            String token = createToken(loginTeacher);

            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("nickName", loginTeacher.getNickName());
            result.put("schoolLogo", loginTeacher.getSchoolLogo());
            result.put("schoolName", loginTeacher.getSchoolName());
            result.put("remark", loginTeacher.getRemark());
            result.put("headIco", loginTeacher.getHeadIco());
            result.put("tag", loginTeacher.getTag());

            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
        } else if (teacherVo != null && teacherVo.getSchoolStatus() != 1) {
            System.out.println(1);
            return ToolUtil.buildResultStr(StatusCode.SCHOOL_STATUS, StatusCode.getStatusMsg(StatusCode.SCHOOL_STATUS));
        } else if (teacherVo != null && teacherVo.getStatus() != 1) {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_DISABLE, StatusCode.getStatusMsg(StatusCode.LOGIN_DISABLE));
        } else {
            return ToolUtil.buildResultStr(StatusCode.LOGIN_NAME_OR_PWD_ERROR, StatusCode.getStatusMsg(StatusCode.LOGIN_NAME_OR_PWD_ERROR));
        }
    }


    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 200 成功  1012 原密码错误
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/updateTeacherPassword")
    public String updateTeacherPassword(String oldPassword, String newPassword, HttpServletRequest request,
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
            param.setPwd(PasswordProvider.encrypt(newPassword));
            sTeacherService.insertOrUpdateByPrimaryKeySelective(param);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.PASSWORD_ERROR, StatusCode.getStatusMsg(StatusCode.PASSWORD_ERROR));
        }
    }

    /**
     * 2018/7/9 18:56
     * 修改老师个人信息
     *
     * @param headIco  头像
     * @param nickName 昵称
     * @param remark   备注
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateTeacher")
    public String updateUserPassword(String headIco, String nickName, String remark, String tag, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        LoginTeacher loginTeacher = getLoginTeacher(request);
        if (loginTeacher == null) {
            return ToolUtil.buildResultStr(StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
        }
        STeacher param = new STeacher();
        param.setId(loginTeacher.getTeacherId());
        param.setHeadIco(headIco);
        param.settName(nickName);
        param.setIntroduction(remark);
        param.setTag(tag);
        sTeacherService.insertOrUpdateByPrimaryKeySelective(param);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 退出登陆
     *
     * @return 200 成功
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.teacherTokenKey, token);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 忘记密码
     * 返回学校管理员手机号
     *
     * @param account 账号
     * @return 200 成功
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/forgetPassword")
    public String forgetPassword(String account, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        STeacher sTeacher = new STeacher();
        sTeacher.setPhone(account);
        STeacher result = sTeacherService.selectBySTeacher(sTeacher);
        if (result == null) {
            return ToolUtil.buildResultStr(StatusCode.TEACHER_IS_NOT_EX, StatusCode.getStatusMsg(StatusCode.TEACHER_IS_NOT_EX));
        } else {
            SchoolAdmin schoolAdmin = schoolAdminService.querySchoolAdminsBySchoolId(result.getSchoolId()).get(0);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), schoolAdmin.getAccount());
        }

    }

    private String createToken(LoginTeacher loginTeacher) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.teacherTokenKey, token, loginTeacher);
        return token;
    }


}
