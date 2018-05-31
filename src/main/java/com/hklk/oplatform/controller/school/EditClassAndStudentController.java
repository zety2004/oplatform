package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.SClassService;
import com.hklk.oplatform.service.SStudentService;
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

@SchoolLoginRepository
@RequestMapping("/EditStudent")
@Controller
public class EditClassAndStudentController extends BaseController {
    @Autowired
    SClassService sClassService;
    @Autowired
    SStudentService sStudentService;
    private int pageSize = 20;

    @ResponseBody
    @RequestMapping("/queryClasses")
    public String queryClasses(int pageNum, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        PageTableForm<SClass> pageTableForm = sClassService.queryClasses(getLoginSchool(request).getSchoolId(), pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public String addOrUpdateClass(SClass sClass, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginSchool(request).getSchoolId();
        SClass temp = sClassService.selectByNameForValidate(sClass.getName(), schoolId);
        if (sClass.getId() == null && temp != null) {
            return ToolUtil.buildResultStr(StatusCode.CLASS_EX, StatusCode.getStatusMsg(StatusCode.CLASS_EX));
        } else if (sClass.getId() != null && temp != null && temp.getId() != sClass.getId()) {
            return ToolUtil.buildResultStr(StatusCode.CLASS_EX, StatusCode.getStatusMsg(StatusCode.CLASS_EX));
        } else {
            sClassService.insertOrUpdateByPrimaryKeySelective(sClass);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/delClass")
    public String delClass(Integer id, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        sClassService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryStudentByClassId")
    public String queryStudentByClassId(Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        List<SStudent> result = sStudentService.queryStudentByClassId(classId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateStudent")
    public String addOrUpdateStudent(SStudent sStudent, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        SStudent temp = sStudentService.selectBySNumForValidate(getLoginSchool(request).getSchoolId(), sStudent.getsNum());
        if (sStudent.getId() == null && temp != null) {
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else if (sStudent.getId() != null && temp != null && temp.getId() != sStudent.getId()) {
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else {
            sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

}
