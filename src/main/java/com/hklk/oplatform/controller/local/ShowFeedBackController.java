package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
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
import java.util.Map;

/**
 * 用户反馈管理
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/feedBack")
@Controller
public class ShowFeedBackController extends BaseController {
    @Autowired
    FeedBackService feedBackService;

    /**
     * 2018/7/4 18:04
     * 查询用户反馈
     * @param sign      标记
     * @param pageNum   分页参数
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/queryFeedBack")
    public String queryFeedBack(Integer sign, int pageNum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        PageTableForm<Map<String, Object>> pageTableForm = feedBackService.queryFeedBackList(sign, pageNum, pageSize);
        return ResultUtils.successStr( pageTableForm);
    }
    /**
     * 2018/7/4 18:04
     * 处理用户反馈
     * @param sign  标记
     * @param id    主键
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/updateFeedBackSign")
    public String updateFeedBackSign(Integer sign, Integer id, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        FeedBack feedBack = new FeedBack();
        feedBack.setId(id);
        feedBack.setSign(sign);
        feedBackService.updateByPrimaryKeySelective(feedBack);
        return ResultUtils.successStr();
    }
}
