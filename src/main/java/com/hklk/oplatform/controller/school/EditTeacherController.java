package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.STeacherService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SchoolLoginRepository
@RequestMapping("/editTeacher")
@Controller
public class EditTeacherController extends BaseController {
    @Autowired
    STeacherService sTeacherService;

    private int pageSize = 20;



    @ResponseBody
    @RequestMapping("/queryTeachers")
    public String queryTeachers(int pageNum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        PageTableForm<STeacher> pageTableForm = sTeacherService.queryTeacherBySchoolId(getLoginSchool(request).getSchoolId(), pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectTeacherByParam")
    public String selectTeacherByParam(STeacher sTeacher, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        sTeacher.setSchoolId(getLoginSchool(request).getSchoolId());
        STeacher result = sTeacherService.selectBySTeacher(sTeacher);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateTeacher")
    public String addOrUpdateTeacher(STeacher sTeacher, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginSchool(request).getSchoolId();
        STeacher param = new STeacher();
        param.setPhone(sTeacher.getPhone());
        param.setSchoolId(schoolId);
        STeacher result = sTeacherService.selectBySTeacher(param);
        if (sTeacher.getId() == null && result != null) {
            return ToolUtil.buildResultStr(StatusCode.TEACHER_EX, StatusCode.getStatusMsg(StatusCode.TEACHER_EX));
        } else if (sTeacher.getId() != null && result != null && result.getId() != sTeacher.getId()) {
            return ToolUtil.buildResultStr(StatusCode.TEACHER_EX, StatusCode.getStatusMsg(StatusCode.TEACHER_EX));
        } else {
            sTeacher.setSchoolId(schoolId);
            sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/delTeacher")
    public String delTeacher(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        sTeacherService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
