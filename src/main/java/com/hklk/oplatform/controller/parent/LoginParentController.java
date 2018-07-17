package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.service.SStudentService;
import com.hklk.oplatform.util.HttpRequestUtils;
import com.hklk.oplatform.util.JsonUtil;
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
    public String getWxOpenId(@RequestParam(value = "code") String code, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        String appid = "wxc01e65175cb40604";
        String secret = "a9da86b619d3d1d6cabd60b184e0fa6f";
        String wxResult = HttpRequestUtils.httpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code");
        Map<String, String> resultMap = JsonUtil.jsonToMapStr(wxResult);
        if (resultMap.get("errcode") != null) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        } else {
            List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(null, resultMap.get("openid"), null);

            if (student != null && student.size() != 0) {
                Object result;
                if (student.size() > 1) {
                    result = new ArrayList<Map<String, Object>>();
                    for (Map map : student) {
                        Map<String, Object> temp = new HashMap<String, Object>();
                        LoginParent loginParent = new LoginParent((Integer) map.get("id"), (String) map.get("phone"), (String) map.get("childName"), (Integer) map.get("classId"), (String) map.get("className"), (Integer) map.get("schoolId"), (String) map.get("schoolName"), (Integer) map.get("grade"), (String) map.get("schoolLogo"), resultMap.get("openid"), resultMap.get("session_key"));
                        String token = createToken(loginParent);
                        temp.put("childName", loginParent.getChildName());
                        temp.put("token", token);

                        ((ArrayList) result).add(temp);
                    }
                } else {
                    result = new HashMap<String, Object>();
                    LoginParent loginParent = new LoginParent((Integer) student.get(0).get("id"), (String) student.get(0).get("phone"), (String) student.get(0).get("childName"), (Integer) student.get(0).get("classId"), (String) student.get(0).get("className"), (Integer) student.get(0).get("schoolId"), (String) student.get(0).get("schoolName"), (Integer) student.get(0).get("grade"), (String) student.get(0).get("schoolLogo"), resultMap.get("openid"), resultMap.get("session_key"));
                    String token = createToken(loginParent);
                    ((HashMap) result).put("token", token);
                }
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
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/bindingWeChat")
    public String bindingWeChat(@RequestParam(value = "phone") String phone, @RequestParam(value = "sNum") String sNum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        List<Map<String, Object>> student = sStudentService.queryStudentByPhoneNum(phone, null, sNum);

        List<Map<String, Object>> studentBinding = sStudentService.queryStudentByPhoneNum(phone, null, null);
        if (student != null && student.size() != 0) {
            Map<String, Object> result = new HashMap<String, Object>();
            for (Map<String, Object> map : studentBinding) {

                LoginParent loginParent = new LoginParent((Integer) map.get("id"), (String) map.get("phone"), (String) map.get("childName"), (Integer) map.get("classId"), (String) map.get("className"), (Integer) map.get("schoolId"), (String) map.get("schoolName"), (Integer) map.get("grade"), (String) map.get("schoolLogo"), getLoginParent(request).getOpenid(), getLoginParent(request).getSession_key());
                String token = createToken(loginParent);
                result.put("childName", loginParent.getChildName());
                result.put("token", token);

                SStudent sStudent = new SStudent();
                sStudent.setId((Integer) map.get("id"));
                sStudent.setWechatId(getLoginParent(request).getOpenid());
                sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
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
