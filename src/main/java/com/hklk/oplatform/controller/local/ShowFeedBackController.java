package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
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

@LocalLoginRepository
@RequestMapping("/feedBack")
@Controller
public class ShowFeedBackController extends BaseController {
    @Autowired
    FeedBackService feedBackService;

    @ResponseBody
    @RequestMapping("/queryFeedBack")
    public String queryFeedBack(int pageNum, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        PageTableForm<Map<String, Object>> pageTableForm = feedBackService.queryFeedBackList(pageNum,pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
