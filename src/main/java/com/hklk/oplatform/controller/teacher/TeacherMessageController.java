package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
import com.hklk.oplatform.service.TeacherMessageService;
import com.hklk.oplatform.util.ResultUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 老师消息通知
 *
 * @author 曹良峰
 * @since 1.0
 */
@TeacherLoginRepository
@RequestMapping("/teacherMessage")
@Controller
public class TeacherMessageController extends BaseController {

    @Autowired
    TeacherMessageService teacherMessageService;

    /**
     * 2018/7/11 9:43
     * 查询老师消息
     *
     * @param isRead
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryTeacherMessage")
    public String queryTeacherMessage(Integer isRead, Integer pageNum, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        PageTableForm<TeacherMessage> teacherMessages = teacherMessageService.queryTeacherMessage(getLoginTeacher(request).getTeacherId(), isRead, pageNum, pageSize);
        return ResultUtils.successStr(teacherMessages);
    }

    /**
     * 2018/7/11 9:43
     * 修改老师全部消息为已读
     *
     * @return code: 200  成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateMessageIsReadByTeacher")
    public String updateMessageIsReadByTeacher(HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) {
        teacherMessageService.updateIsReadByTeacherId(getLoginTeacher(request).getTeacherId());
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/11 9:43
     * 修改老师单个消息为已读
     *
     * @return code: 200  成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateMessageIsReadById")
    public String updateMessageIsReadById(Integer id, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        teacherMessageService.updateIsReadById(id);
        return ResultUtils.successStr();
    }
}
