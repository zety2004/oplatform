package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
import com.hklk.oplatform.service.ParentMessageService;
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
import java.util.List;


/**
 * 家长消息通知
 *
 * @author 曹良峰
 * @since 1.0
 */

@RequestMapping("/parentMessage")
@Controller
public class ParentMessageController extends BaseController {

    @Autowired
    ParentMessageService parentMessageService;

    /**
     * 2018/7/11 9:43
     * 查询家长消息
     *
     * @param isRead
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryParentMessage")
    public String queryTeacherMessage(Integer isRead, HttpServletRequest request) {
        List<ParentMessage> teacherMessages = parentMessageService.queryParentMessage(getLoginParent(request).getStudentId(), isRead);
        return ResultUtils.successStr(teacherMessages);
    }

    /**
     * 2018/7/11 9:43
     * 修改家长全部消息为已读
     *
     * @return code: 200  成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateMessageIsReadByTeacher")
    public String updateMessageIsReadByTeacher(HttpServletRequest request) {
        parentMessageService.updateIsReadByStudentId(getLoginParent(request).getStudentId());
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/11 9:43
     * 修改家长单个消息为已读
     *
     * @param id 主键
     * @return code: 200  成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateMessageIsReadById")
    public String updateMessageIsReadById(Integer id) {
        parentMessageService.updateIsReadById(id);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/20 11:32
     * 描述一下方法的作用
     *
     * @param id 主键
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delMessageById")
    public String delMessageById(Integer id) {
        parentMessageService.deleteByPrimaryKey(id);
        return ResultUtils.successStr();
    }
}
