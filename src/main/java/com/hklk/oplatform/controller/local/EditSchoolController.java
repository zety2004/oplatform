package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SchoolVo;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.SchoolAdminService;
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

@LocalLoginRepository
@RequestMapping("/editSchool")
@Controller
public class EditSchoolController extends BaseController {
    @Autowired
    SchoolService schoolService;
    @Autowired
    SchoolAdminService schoolAdminService;

    @ResponseBody
    @RequestMapping("/querySchool")
    public String querySchool(String param, int pageNum, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        PageTableForm<SchoolVo> schoolPageTableForm = schoolService.querySchools(param, pageNum, pageSize);
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
    @RequestMapping("/validateSchoolName")
    public String validateSchoolName(School school, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_UNEX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_UNEX));
        }
    }

    @ResponseBody
    @RequestMapping("/addSchool")
    public String addSchool(School school, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {
            schoolService.insertSelective(school);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/updateSchool")
    public String updateSchool(School school, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null && tmp.getId() != school.getId()) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {
            schoolService.updateByPrimaryKeySelective(school);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/deleteSchool")
    public String deleteSchool(int id, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        schoolService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/querySchoolAdmin")
    public String querySchoolAdmin(int schoolId, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        schoolAdminService.querySchoolAdminsBySchoolId(schoolId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/selectSchoolAdminById")
    public String selectSchoolAdminById(int id, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        SchoolAdmin schoolAdmin = schoolAdminService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), schoolAdmin);
    }

    @ResponseBody
    @RequestMapping("/addSchoolAdmin")
    public String addSchoolAdmin(SchoolAdmin schoolAdmin, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        int tmp = schoolAdminService.querySchoolAdminsForCount(schoolAdmin.getSchoolId());
        if (tmp >= 10) {
            return ToolUtil.buildResultStr(StatusCode.ADMIN_NUM_VALIDATE, StatusCode.getStatusMsg(StatusCode.ADMIN_NUM_VALIDATE));
        } else {
            SchoolAdmin tmpAdmin = schoolAdminService.querySchoolAdminsByName(schoolAdmin.getAccount());
            if (tmpAdmin != null) {
                return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, StatusCode.getStatusMsg(StatusCode.ADDUSER_USERNAME_EX));
            }
            schoolAdminService.insertSelective(schoolAdmin);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }


    @ResponseBody
    @RequestMapping("/updateSchoolAdmin")
    public String updateSchoolAdmin(SchoolAdmin schoolAdmin, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        SchoolAdmin tmpAdmin = schoolAdminService.querySchoolAdminsByName(schoolAdmin.getAccount());
        if (tmpAdmin != null && tmpAdmin.getId() != schoolAdmin.getId()) {
            return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, StatusCode.getStatusMsg(StatusCode.ADDUSER_USERNAME_EX));
        }
        schoolAdminService.updateByPrimaryKeySelective(schoolAdmin);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deleteSchoolAdmin")
    public String deleteSchoolAdmin(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        schoolAdminService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
