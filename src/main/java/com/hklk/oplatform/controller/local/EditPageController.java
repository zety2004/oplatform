package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.PageService;
import com.hklk.oplatform.service.RolePageService;
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
 * 页面管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editPage")
@Controller
public class EditPageController extends BaseController {
    @Autowired
    PageService pageService;
    @Autowired
    RolePageService rolePageService;


    /**
     * 2018/7/4 17:35
     * 查询页面
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryPages")
    public String queryPages(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        List<PPage> roles = pageService.queryPages();
        return ResultUtils.successStr(roles);
    }

    /**
     * 2018/7/4 17:47
     * 添加页面
     * @param PPage 页面对象
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/addPage")
    public String addPages(PPage PPage, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        pageService.addPage(PPage);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 17:48
     * 修改页面
     * @param PPage 页面对象
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/updatePage")
    public String updatePage(PPage PPage, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        pageService.updatePage(PPage);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 17:48
     * 删除页面
     * @param id    页面id
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/deletePage")
    public String deletePage(int id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        pageService.deletePage(id);
        return ResultUtils.successStr();
    }

}
