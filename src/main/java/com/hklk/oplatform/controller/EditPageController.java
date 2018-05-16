package com.hklk.oplatform.controller;

import com.hklk.oplatform.entity.table.Page;
import com.hklk.oplatform.service.PageService;
import com.hklk.oplatform.service.RolePageService;
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

@RequestMapping("/editPage")
@Controller
public class EditPageController extends BaseController {
    @Autowired
    PageService pageService;
    @Autowired
    RolePageService rolePageService;

    @ResponseBody
    @RequestMapping("/queryPages")
    public String queryPages(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {

            List<Page> roles = pageService.queryPages();
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), roles);
    }

    @ResponseBody
    @RequestMapping("/addPage")
    public String addPages(Page page ,HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        pageService.addPage(page);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updatePage")
    public String updatePage(Page page ,HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        pageService.updatePage(page);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deletePage")
    public String deletePage(int id ,HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        pageService.deletePage(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

}
