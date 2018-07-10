package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.service.SStudentService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 老师登陆模块
 *
 * @author 曹良峰
 * @since 1.0
 */
@RequestMapping("/loginParent")
@Controller
public class LoginParentController extends BaseController {
    @Resource
    private TokenManager tokenManager;
    @Autowired
    SStudentService sStudentService;

    /**
     * 登陆
     * 账号的登陆，登陆成功后保存token发送到前端。
     *
     * @param wechatId 用户名
     * @param phone    密码
     * @return {"resultCode":200,"resultMsg":"成功"}  code: 1008 学校禁用  1000 账号禁用 1001 账号或密码错误
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/login")
    public String loginParent(@RequestParam(value = "wechatId") String wechatId, @RequestParam(value = "phone") String phone, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(phone, wechatId);
        if (student != null && student.size() != 0) {
            Object result;
            if (student.size() > 1) {
                result = new ArrayList<Map<String, Object>>();
                for (Map map : student) {
                    Map<String, Object> temp = new HashMap<String, Object>();
                    LoginParent loginParent = new LoginParent((Integer) map.get("id"), (String) map.get("phone"), (String) map.get("childName"), (Integer) map.get("classId"), (String) map.get("className"), (Integer) map.get("schoolId"), (String) map.get("schoolName"), (Integer) map.get("grade"), (String) map.get("schoolLogo"));
                    String token = createToken(loginParent);
                    temp.put("childName", loginParent.getChildName());
                    temp.put("token", token);
                    ((ArrayList) result).add(temp);
                }
            } else {
                result = new HashMap<String, Object>();
                LoginParent loginParent = new LoginParent((Integer) student.get(0).get("id"), (String) student.get(0).get("phone"), (String) student.get(0).get("childName"), (Integer) student.get(0).get("classId"), (String) student.get(0).get("className"), (Integer) student.get(0).get("schoolId"), (String) student.get(0).get("schoolName"), (Integer) student.get(0).get("grade"), (String) student.get(0).get("schoolLogo"));
                String token = createToken(loginParent);
                ((HashMap) result).put("token", token);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
        } else {
            return ToolUtil.buildResultStr(StatusCode.ERROR_MSG, StatusCode.getStatusMsg(StatusCode.ERROR_MSG));
        }
    }

    @ResponseBody
    @RequestMapping("/bindingWeChat")
    public String bindingWeChat(@RequestParam(value = "wechatId") String wechatId, @RequestParam(value = "phone") String phone, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(phone, null);
        if (student != null && student.size() != 0) {
            for (Map<String, Object> map : student) {
                SStudent sStudent = new SStudent();
                sStudent.setId((Integer) map.get("id"));
                sStudent.setWechatId(wechatId);
                sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } else {
            return ToolUtil.buildResultStr(StatusCode.ERROR_MSG, StatusCode.getStatusMsg(StatusCode.ERROR_MSG));
        }
    }

    private String createToken(LoginParent loginParent) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.parentTokenKey, token, loginParent);
        return token;
    }


}
