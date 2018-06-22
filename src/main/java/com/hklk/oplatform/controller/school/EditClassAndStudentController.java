package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.ImportStudentVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.SClassService;
import com.hklk.oplatform.service.SStudentService;
import com.hklk.oplatform.util.ExcelUtils;
import com.hklk.oplatform.util.FileUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SchoolLoginRepository
@RequestMapping("/editStudent")
@Controller
public class EditClassAndStudentController extends BaseController {
    @Autowired
    SClassService sClassService;
    @Autowired
    SStudentService sStudentService;

    @ResponseBody
    @RequestMapping("/queryClasses")
    public String queryClasses(String param, int pageNum, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        PageTableForm<SClassVo> pageTableForm = sClassService.queryClasses(param, getLoginSchool(request).getSchoolId(), pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryClassesById")
    public String queryClassesById(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        SClass pageTableForm = sClassService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public String addOrUpdateClass(SClass sClass, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginSchool(request).getSchoolId();
        sClass.setSchoolId(schoolId);
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
    public String queryStudentByClassId(String param, Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        List<SStudent> result = sStudentService.queryStudentByClassId(param, classId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/queryStudentById")
    public String queryStudentById(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        SStudent sStudent = sStudentService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), sStudent);
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
            if (FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xlsx") == -1 && FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xls") == -1) {
                return ToolUtil.buildResultStr(StatusCode.FILE_IS_NOT_RIGHT, StatusCode.getStatusMsg(StatusCode.FILE_IS_NOT_RIGHT));
            }
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);
            Integer schoolId = getLoginSchool(request).getSchoolId();
            List<ImportStudentVo> errorInsert = new ArrayList<>();
            int index = 0;
            for (SStudent sStudent : sStudents) {
                if (sStudent.getsNum() == null || "".equals(sStudent.getsNum())) {
                    ImportStudentVo importStudentVo = new ImportStudentVo();
                    importStudentVo.setFullName(sStudent.getFullName());
                    importStudentVo.setsNum(sStudent.getsNum());
                    importStudentVo.setReason("缺少关键字段，请检查后重新导入");
                    errorInsert.add(importStudentVo);
                    index++;
                    break;
                }
                SStudent temp = sStudentService.selectBySNumForValidate(schoolId, sStudent.getsNum());
                if (sStudent.getId() == null && temp != null) {
                    ImportStudentVo importStudentVo = new ImportStudentVo();
                    importStudentVo.setFullName(sStudent.getFullName());
                    importStudentVo.setsNum(sStudent.getsNum());
                    SClass sClass = sClassService.selectByPrimaryKey(temp.getClassId());
                    importStudentVo.setReason("该学生已存在于" + sClass.getName() + "班中");
                    errorInsert.add(importStudentVo);
                    index++;
                } else {
                    sStudent.setClassId(classId);
                    sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
                }
            }
            if (index > 0) {
                Map<String, Object> result = new HashMap<>();
                result.put("total", sStudents.size());
                result.put("failList", errorInsert);

                return ToolUtil.buildResultStr(StatusCode.IMPORTERROR_STUDENT, StatusCode.getStatusMsg(StatusCode.IMPORTERROR_STUDENT), result);
            } else {
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }
}
