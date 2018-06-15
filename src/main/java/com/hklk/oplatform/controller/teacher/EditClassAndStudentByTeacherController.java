package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;
import com.hklk.oplatform.service.SClassService;
import com.hklk.oplatform.service.SStudentService;
import com.hklk.oplatform.util.ExcelUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/tEditStudent")
@Controller
public class EditClassAndStudentByTeacherController extends BaseController {

    @Autowired
    SClassService sClassService;
    @Autowired
    SStudentService sStudentService;


    @ResponseBody
    @RequestMapping("/queryClasses")
    public String queryClasses(String param, int pageNum, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        PageTableForm<SClassVo> pageTableForm = sClassService.queryClasses(param, getLoginTeacher(request).getSchoolId(), pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }


    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public String addOrUpdateClass(SClass sClass, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginTeacher(request).getSchoolId();
        SClass temp = sClassService.selectByNameForValidate(sClass.getName(), schoolId);
        if (sClass.getId() == null && temp != null) {
            System.out.println(getLoginTeacher(request).getNickName() + "老师操作!");
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
        LoginTeacher loginTeacher = getLoginTeacher(request);
        SClass temp = sClassService.selectByPrimaryKey(id);
        if (temp.getCreateBy() == null || temp.getCreateBy() != loginTeacher.getTeacherId()) {
            return ToolUtil.buildResultStr(StatusCode.VALIDATE_CLASS_IS_TEACHER_CREATE, StatusCode.getStatusMsg(StatusCode.VALIDATE_CLASS_IS_TEACHER_CREATE));
        } else {
            sClassService.deleteByPrimaryKey(id);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/queryStudentByClassId")
    public String queryStudentByClassId(String param, Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        List<SStudent> result = sStudentService.queryStudentByClassId(param, classId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateStudent")
    public String addOrUpdateStudent(SStudent sStudent, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        SStudent temp = sStudentService.selectBySNumForValidate(getLoginTeacher(request).getSchoolId(), sStudent.getsNum());
        if (sStudent.getId() == null && temp != null) {
            System.out.println(getLoginTeacher(request).getNickName() + "老师操作!");
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else if (sStudent.getId() != null && temp != null && temp.getId() != sStudent.getId()) {
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else {
            sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/delStudent")
    public String delStudent(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        sStudentService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));

    }

    @RequestMapping("/exportExcelForStudent")
    @ResponseBody
    public String exportExcelForStudent(Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {

        String[] columnHeader = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
        String[] fieldNames = {"学号", "姓名", "性别", "家长姓名", "家长联系电话"};
        List<SStudent> result = sStudentService.queryStudentByClassId(null, classId);
        try {
            System.out.println("teacher export ----------------------");
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=students.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", result, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @RequestMapping("/importExcelForStudent")
    @ResponseBody
    public String importExcelForStudent(Integer classId, MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("importExcel");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);
            Integer schoolId = getLoginTeacher(request).getSchoolId();
            List<SStudent> errorInsert = new ArrayList<>();

            int inx = 0;
            for (SStudent sStudent : sStudents) {

                SStudent temp = sStudentService.selectBySNumForValidate(schoolId, sStudent.getsNum());
                if (sStudent.getId() == null && temp != null) {
                    System.out.println(sStudent.getsNum() + ":添加失败");
                    errorInsert.add(sStudent);
                    inx++;
                } else {
                    sStudent.setClassId(classId);
                    sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
                }
            }
            if (inx > 0) {
                return ToolUtil.buildResultStr(StatusCode.IMPORTERROR_STUDENT, StatusCode.getStatusMsg(StatusCode.IMPORTERROR_STUDENT), errorInsert);
            } else {
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }

}
