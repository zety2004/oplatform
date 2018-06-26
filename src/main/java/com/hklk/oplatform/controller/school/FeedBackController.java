package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.FeedBackService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SchoolLoginRepository
@RequestMapping("/feedBackForSchool")
@Controller
public class FeedBackController extends BaseController {
    @Autowired
    FeedBackService feedBackService;

    @ResponseBody
    @RequestMapping("/addFeedBack")
    public String addFeedBack(String content, Integer category, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        FeedBack feedBack = new FeedBack();
        feedBack.setForTable("hklk_School_admin");
        feedBack.setFeedbackUser(getLoginSchool(request).getSchoolAdminId());
        feedBack.setContent(content);
        feedBack.setCategory(category);
        feedBackService.insertSelective(feedBack);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryFeedBack")
    public String queryFeedBack(int pageNum, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        PageTableForm<Map<String, Object>> pageTableForm = feedBackService.queryFeedBackList(pageNum,pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
