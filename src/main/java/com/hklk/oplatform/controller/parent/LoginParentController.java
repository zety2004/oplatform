package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.service.SStudentService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 家长登陆模块
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
     * 2018/7/17 16:02
     * 获取wx session_key
     *
     * @param code
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/loginWx")
    public String getWxOpenId(@RequestParam(value = "code") String code) {

        String wxResult = HttpRequestUtils.httpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + PropUtil.getProperty("wxAppid") + "&secret=" + PropUtil.getProperty("wxSecret") + "&js_code=" + code + "&grant_type=authorization_code");
        Map<String, String> resultMap = JsonUtil.jsonToMapStr(wxResult);
        if (resultMap.get("errcode") != null) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        } else {
            List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(null, resultMap.get("openid"), null);

            if (student != null && student.size() != 0) {
                List result = new ArrayList<Map<String, Object>>();
                student.forEach(map->{
                    LoginParent loginParent = new LoginParent((Integer) map.get("id"), (String) map.get("phone"), (String) map.get("childName"), (Integer) map.get("classId"), (String) map.get("className"), (Integer) map.get("schoolId"), (String) map.get("schoolName"), (Integer) map.get("grade"), (String) map.get("schoolLogo"), resultMap.get("openid"), resultMap.get("session_key"));
                    String token = createToken(loginParent);
                    Map<String, Object> stu = new HashMap<>();
                    stu.put("childName", loginParent.getChildName());
                    stu.put("schoolName", map.get("schoolName"));
                    stu.put("className", map.get("className"));
                    stu.put("schoolLogo", map.get("schoolLogo"));
                    stu.put("token", token);
                    result.add(stu);
                });
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
            } else {
                LoginParent loginParent = new LoginParent(null, null, null, null, null, null, null, null, null, resultMap.get("openid"), resultMap.get("session_key"));
                String token = createToken(loginParent);
                return ToolUtil.buildResultStr(StatusCode.ERROR_MSG, StatusCode.getStatusMsg(StatusCode.ERROR_MSG), token);
            }
        }
    }

    /**
     * 2018/7/17 16:17
     * 绑定微信
     *
     * @param phone
     * @param sNum
     * @return code: 200 成功  1025 学生已经被绑定  1024 未找到学生
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/bindingWeChat")
    public String bindingWeChat(@RequestParam(value = "phone") String phone, @RequestParam(value = "sNum") String sNum, HttpServletRequest request) {
        List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(phone, null, sNum);

        if (student != null && student.size() != 0) {
            if (student.get(0).get("wechatId") != null && !"".equals(student.get(0).get("wechatId"))) {
                return ToolUtil.buildResultStr(StatusCode.STUDENT_WAS_BINDING, StatusCode.getStatusMsg(StatusCode.STUDENT_WAS_BINDING));
            }
            List<Map<String, Object>> studentBinding = sStudentService.queryStudentByPhoneNum(phone, null, null);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Map<String, Object> map : studentBinding) {
                LoginParent loginParent = new LoginParent((Integer) map.get("id"), (String) map.get("phone"), (String) map.get("childName"), (Integer) map.get("classId"), (String) map.get("className"), (Integer) map.get("schoolId"), (String) map.get("schoolName"), (Integer) map.get("grade"), (String) map.get("schoolLogo"), getLoginParent(request).getOpenid(), getLoginParent(request).getSession_key());
                String token = createToken(loginParent);
                Map<String, Object> stu = new HashMap<>();
                stu.put("childName", loginParent.getChildName());
                stu.put("schoolName", map.get("schoolName"));
                stu.put("className", map.get("className"));
                stu.put("schoolLogo", map.get("schoolLogo"));
                stu.put("token", token);
                SStudent sStudent = new SStudent();
                sStudent.setId((Integer) map.get("id"));
                sStudent.setWechatId(getLoginParent(request).getOpenid());
                sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
                result.add(stu);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
        } else {
            return ToolUtil.buildResultStr(StatusCode.NO_FOUND_STUDENT, StatusCode.getStatusMsg(StatusCode.NO_FOUND_STUDENT));
        }
    }

    /**
     * 2018/8/3 10:41
     * 学生解绑微信
     *
     * @param phone
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/unBindingWeChat")
    public String unbindingWeChat(@RequestParam(value = "phone") String phone) {
        List<Map<String, Object>> studentBinding = sStudentService.queryStudentByPhoneNum(phone, null, null);
        for (Map<String, Object> student : studentBinding) {
            SStudent sStudent = new SStudent();
            sStudent.setId((Integer) student.get("id"));
            sStudent.setWechatId("");
            sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    private String createToken(LoginParent loginParent) {
        // 生成token
        String token = IdProvider.createUUIDId();
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.parentTokenKey, token, loginParent);
        return token;
    }

    private String updateToken(String token, LoginParent loginParent) {
        // 缓存中添加token对应User
        tokenManager.addToken(tokenManager.parentTokenKey, token, loginParent);
        return token;
    }
}
