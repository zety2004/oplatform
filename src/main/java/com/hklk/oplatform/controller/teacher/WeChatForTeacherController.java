package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.TeacherVo;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.service.STeacherService;
import com.hklk.oplatform.util.ResultCode;
import com.hklk.oplatform.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * oplatform
 * 2018/10/22 11:04
 * 老师微信操作
 *
 * @author 曹良峰
 * @since
 **/
@RequestMapping("/loginWeChatTeacher")
public class WeChatForTeacherController extends BaseController {

    @Autowired
    STeacherService sTeacherService;
    @Resource
    private TokenManager tokenManager;

    /**
     * 2018/7/17 16:02
     * 获取wx session_key
     *
     * @param code
     * @return java.lang.String
     * @author 曹良峰
     */
   /* @ResponseBody
    @RequestMapping("/loginWx")
    public String getWxOpenId(@RequestParam(value = "code") String code) {

        String wxResult = HttpRequestUtils.httpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + PropUtil.getProperty("wxAppid") + "&secret=" + PropUtil.getProperty("wxSecret") + "&js_code=" + code + "&grant_type=authorization_code");
        Map<String, String> resultMap = JsonUtil.jsonToMapStr(wxResult);
        if (resultMap.get("errcode") != null) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        } else {
            List<Map<String, Object>> student = sTeacherService.queryStudentByPhoneNum(null, resultMap.get("openid"), null);

            if (student != null && student.size() != 0) {
                List result = new ArrayList<Map<String, Object>>();
                student.forEach(map -> {
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
    }*/

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
    public String bindingWeChat(@RequestParam(value = "account") String account, @RequestParam(value = "pwd") String pwd, HttpServletRequest request) {
        TeacherVo teacherVo = sTeacherService.loginTeacher(account, pwd);

        if (teacherVo != null && teacherVo.getId() != null) {
            if (teacherVo.getWechatId() != null && !"".equals(teacherVo.getWechatId())) {
                return ResultUtils.warnStr(ResultCode.ACCOUNT_WAS_BINDING);
            }
            LoginTeacher loginTeacher = new LoginTeacher(teacherVo.getId(), teacherVo.getPhone(), teacherVo.getNickname(), "", teacherVo.getSchoolId(), teacherVo.getSchoolName(), teacherVo.getSchoolLogo(), teacherVo.getHeadIco(), teacherVo.getRemark(), teacherVo.getTag(), getLoginTeacher(request).getOpenid(), getLoginTeacher(request).getSession_key());
            String token = createToken(loginTeacher);
            Map<String, Object> result = new HashMap<>();
            result.put("nickName", loginTeacher.getNickName());
            result.put("schoolLogo", loginTeacher.getSchoolLogo());
            result.put("schoolName", loginTeacher.getSchoolName());
            result.put("remark", loginTeacher.getRemark());
            result.put("headIco", loginTeacher.getHeadIco());
            result.put("tag", loginTeacher.getTag());
            result.put("token", token);
            STeacher sTeacher = new STeacher();
            sTeacher.setId(teacherVo.getId());
            sTeacher.setWechatId(getLoginTeacher(request).getOpenid());
            sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
            return ResultUtils.successStr(result);
        } else {
            return ResultUtils.warnStr(ResultCode.NO_FOUND_STUDENT);
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
