package com.hklk.oplatform.controller;

import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.ConsumablesService;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SchoolService;
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

@RequestMapping("/editSchool")
@Controller
public class EditSchoolController extends BaseController {
    @Autowired
    SchoolService schoolService;

    private int pageSize = 10;

    @ResponseBody
    @RequestMapping("/querySchool")
    public String querySchool(int pageNum, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        PageTableForm<School> schoolPageTableForm = schoolService.querySchools(pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), schoolPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectSchoolById")
    public String selectSchoolById(int id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {

        School school = schoolService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), school);
    }

    @ResponseBody
    @RequestMapping("/addSchool")
    public String addSchool(School school, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        schoolService.insertSelective(school);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updateSchool")
    public String updateSchool(School school, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        schoolService.updateByPrimaryKeySelective(school);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deleteSchool")
    public String deleteSchool(int id, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        schoolService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
