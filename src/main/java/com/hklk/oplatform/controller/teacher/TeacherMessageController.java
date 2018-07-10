package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
import com.hklk.oplatform.service.TeacherMessageService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 老师消息通知
 *
 * @author 曹良峰
 * @since 1.0
 */
/*@TeacherLoginRepository
@RequestMapping("/teacherMessage")*/
/*@Controller*/
public class TeacherMessageController extends BaseController {

    @Autowired
    TeacherMessageService teacherMessageService;

    /**
     * 2018/7/4 12:11
     * 查询教师消息
     *
     * @return {"resultData":{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","enclosure":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX43743b8d4baee12223be9382e13c2736.zip","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"des":"","consumables":[]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryTeacherMessage")
    public String queryTeacherMessage(HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        List <TeacherMessage> teacherMessages = teacherMessageService.queryTeacherMessage(getLoginTeacher(request).getTeacherId());
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), teacherMessages);
    }
}
