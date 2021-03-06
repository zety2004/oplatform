package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.comm.LoginSchool;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.SchoolAdminVo;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.SchoolAdminService;
import com.hklk.oplatform.util.*;
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

/**
 * 学校登陆
 *
 * @author 曹良峰
 * @since 1.0
 */
@RequestMapping("/loginSchool")
@Controller
public class LoginSchoolController extends BaseController {
    @Autowired
    SchoolAdminService schoolAdminService;
    @Resource
    private TokenManager tokenManager;

    /**
     * 学校登陆
     *
     * @param account 账号
     * @param pwd     密码
     * @return code: 200 成功  1008 学校禁用  1000 账号禁用  1001 登陆名或密码错误
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/login")
    public String loginSchool(@RequestParam(value = "account") String account, @RequestParam(value = "pwd") String pwd, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        SchoolAdminVo schoolAdmin = schoolAdminService.loginSchool(account, pwd);
        if (schoolAdmin != null && schoolAdmin.getStatus() == 1 && schoolAdmin.getSchoolStatus() == 1) {
            LoginSchool loginSchool = new LoginSchool(schoolAdmin.getId(), schoolAdmin.getAccount(), schoolAdmin.getNickname(), "", schoolAdmin.getSchoolId(), schoolAdmin.getSchoolName(), schoolAdmin.getSchoolLogo());
            String token = createToken(loginSchool);
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("nickName", schoolAdmin.getNickname());
            result.put("schoolName", schoolAdmin.getSchoolName());
            result.put("schoolLogo", schoolAdmin.getSchoolLogo());
            if (schoolAdmin.getLoginNum() == 0 && schoolAdmin.getAccount().length() == 11) {
                Map<String, String> smsParam = new HashMap<>();
                smsParam.put("attr1", schoolAdmin.getNickname());
                try {
                    SmsUtil.sendSms(schoolAdmin.getAccount(), "SMS_144147386", smsParam);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SchoolAdmin loginNum = new SchoolAdmin();
            loginNum.setId(schoolAdmin.getId());
            loginNum.setLoginNum(schoolAdmin.getLoginNum() + 1);
            schoolAdminService.updateByPrimaryKeySelective(loginNum);

            return ResultUtils.successStr(result);
        } else if (schoolAdmin != null && schoolAdmin.getSchoolStatus() != 1) {
            return ResultUtils.warnStr(ResultCode.SCHOOL_STATUS);
        } else if (schoolAdmin != null && schoolAdmin.getStatus() != 1) {
            return ResultUtils.warnStr(ResultCode.LOGIN_DISABLE);
        } else {
            return ResultUtils.warnStr(ResultCode.LOGIN_NAME_OR_PWD_ERROR);
        }
    }

    /**
     * 2018/7/4 16:02
     * 描述一下方法的作用
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return code : 1011 账号不纯在  200 成功  1012 原密码错误  1006 token过期
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateSchoolAdminPassword")
    public String updateSchoolAdminPassword(String oldPassword, String newPassword, HttpServletRequest request,
                                            HttpServletResponse response, HttpSession session) {
        LoginSchool loginSchool = getLoginSchool(request);
        if (loginSchool == null) {
            return ResultUtils.warnStr(ResultCode.SSO_TOKEN_ERROR);
        }
        SchoolAdmin temp = schoolAdminService.selectByPrimaryKey(loginSchool.getSchoolAdminId());
        if (temp == null) {
            return ResultUtils.warnStr(ResultCode.USER_UNFIND);
        } else if (temp != null && temp.getPwd().equals(PasswordProvider.encrypt(oldPassword))) {
            SchoolAdmin param = new SchoolAdmin();
            param.setId(loginSchool.getSchoolAdminId());
            param.setPwd(PasswordProvider.encrypt(newPassword));
            schoolAdminService.updateByPrimaryKeySelective(param);
            return ResultUtils.successStr();
        } else {
            return ResultUtils.warnStr(ResultCode.PASSWORD_ERROR);
        }
    }

    /**
     * 2018/7/4 16:05
     * 找回密码
     *
     * @param account 账号
     * @return code ：1011 账号不存在 200 成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/findAccountForSchoolAdmin")
    public String findAccountForSchoolAdmin(String account, HttpServletRequest request,
                                            HttpServletResponse response, HttpSession session) {
        SchoolAdmin param = schoolAdminService.querySchoolAdminsByName(account);
        if (param == null) {
            return ResultUtils.warnStr(ResultCode.USER_UNFIND);
        } else {
            return ResultUtils.successStr();
        }
    }

    /**
     * 2018/7/4 16:06
     * 修改昵称
     *
     * @param nickName 昵称
     * @return code:200 成功  1006 token过期
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateSchoolNickName")
    public String updateSchoolNickName(String nickName, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        LoginSchool loginSchool = getLoginSchool(request);
        if (loginSchool == null) {
            return ResultUtils.warnStr(ResultCode.SSO_TOKEN_ERROR);
        }
        SchoolAdmin param = new SchoolAdmin();
        param.setId(loginSchool.getSchoolAdminId());
        param.setNickname(nickName);
        schoolAdminService.updateByPrimaryKeySelective(param);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 16:07
     * 退出登陆
     *
     * @return code :200 成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        String token = request.getHeader("Access-Toke");
        tokenManager.remove(tokenManager.schoolTokenKey, token);
        return ResultUtils.successStr();
    }

    private String createToken(LoginSchool loginSchool) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.schoolTokenKey, token, loginSchool);
        return token;
    }
}
