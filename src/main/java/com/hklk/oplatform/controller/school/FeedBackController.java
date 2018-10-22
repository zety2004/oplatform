package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.FeedBackService;
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
 * 学校反馈接口
 *
 * @author 曹良峰
 * @since 1.0
 */
@SchoolLoginRepository
@RequestMapping("/feedBackForSchool")
@Controller
public class FeedBackController extends BaseController {
    @Autowired
    FeedBackService feedBackService;

    /**
     * 2018/7/4 16:08
     * 添加反馈信息
     *
     * @param content  内容
     * @param category 类型
     * @return code ：200 成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addFeedBack")
    public String addFeedBack(String content, Integer category, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        FeedBack feedBack = new FeedBack();
        feedBack.setForTable("hklk_school_admin");
        feedBack.setFeedbackUser(getLoginSchool(request).getSchoolAdminId());
        feedBack.setContent(content);
        feedBack.setCategory(category);
        feedBackService.insertSelective(feedBack);
        return ResultUtils.successStr();
    }
}
