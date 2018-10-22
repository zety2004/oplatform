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
import com.hklk.oplatform.util.*;
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

/**
 * 学校管理班级学生
 *
 * @author 曹良峰
 * @since 1.0
 */
@SchoolLoginRepository
@RequestMapping("/editStudent")
@Controller
public class EditClassAndStudentController extends BaseController {
    @Autowired
    SClassService sClassService;
    @Autowired
    SStudentService sStudentService;

    /**
     * 2018/7/4 12:15
     * 分页查询班级
     *
     * @param param   筛选参数
     * @param pageNum 分页参数
     * @return {"resultData":{"currentPage":1,"pageSize":20,"beginIndex":0,"endIndex":20,"pageCount":1,"totalCount":14,"objList":[{"id":21,"schoolId":5,"grade":4,"remark":"阿斯顿发生大范德萨都是撒的发生发的","name":"四年级四班","createBy":null,"createTime":"2018-06-25 03:29:24","studentNum":21},{"id":23,"schoolId":5,"grade":1,"remark":"一年级一班","name":"一年级一班","createBy":null,"createTime":"2018-06-27 10:02:27","studentNum":0},{"id":25,"schoolId":5,"grade":1,"remark":"一年级四班edit","name":"一年级四班edit","createBy":null,"createTime":"2018-06-27 10:03:21","studentNum":0},{"id":26,"schoolId":5,"grade":1,"remark":"一年级add","name":"add","createBy":null,"createTime":"2018-06-27 10:03:55","studentNum":0},{"id":29,"schoolId":5,"grade":1,"remark":"阿斯蒂芬","name":"部门三三edit","createBy":null,"createTime":"2018-06-27 10:32:11","studentNum":0},{"id":30,"schoolId":5,"grade":1,"remark":"啊沙发电视柜课时发水立方第四个","name":"after edit——add","createBy":null,"createTime":"2018-06-27 10:32:36","studentNum":0},{"id":31,"schoolId":5,"grade":1,"remark":"asfdsf","name":"asfdsf","createBy":null,"createTime":"2018-06-27 11:02:04","studentNum":1},{"id":32,"schoolId":5,"grade":1,"remark":"safdsgds","name":"asfdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":35,"schoolId":5,"grade":1,"remark":"sadsfdsg","name":"asdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":36,"schoolId":5,"grade":1,"remark":"safdsgds","name":"asfdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":37,"schoolId":5,"grade":1,"remark":"sadsfdsg","name":"asdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":38,"schoolId":5,"grade":1,"remark":"asfdsfds","name":"asdsfds123","createBy":null,"createTime":"2018-06-27 11:02:43","studentNum":0},{"id":48,"schoolId":5,"grade":3,"remark":"阿斯顿发送到覅就死按点击覅就是打击对方家里就","name":"三年级六班","createBy":null,"createTime":"2018-07-04 03:21:24","studentNum":0},{"id":49,"schoolId":5,"grade":3,"remark":"撒旦法规离开家里看到几个路口见对方立刻感觉","name":"三年级九班","createBy":null,"createTime":"2018-07-04 03:21:43","studentNum":0}]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryClasses")
    public String queryClasses(String param, int pageNum, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        PageTableForm<SClassVo> pageTableForm = sClassService.queryClasses(param, getLoginSchool(request).getSchoolId(), null, pageNum, pageSize);
        return ResultUtils.successStr(pageTableForm);
    }

    /**
     * 2018/7/4 16:14
     * 根据id查询班级
     *
     * @param id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryClassesById")
    public String queryClassesById(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        SClass pageTableForm = sClassService.selectByPrimaryKey(id);
        return ResultUtils.successStr(pageTableForm);
    }

    /**
     * 2018/7/4 16:15
     * 添加或修改班级
     *
     * @param sClass 班级对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public String addOrUpdateClass(SClass sClass, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginSchool(request).getSchoolId();
        sClass.setSchoolId(schoolId);
        SClass temp = sClassService.selectByNameForValidate(sClass.getName(), schoolId);
        if (sClass.getId() == null && temp != null) {
            return ResultUtils.warnStr(ResultCode.CLASS_EX);
        } else if (sClass.getId() != null && temp != null && temp.getId().intValue() != sClass.getId().intValue()) {
            return ResultUtils.warnStr(ResultCode.CLASS_EX);
        } else {
            sClassService.insertOrUpdateByPrimaryKeySelective(sClass);
            return ResultUtils.successStr();
        }
    }

    /**
     * 2018/7/4 16:15
     * 删除班级
     *
     * @param id 班级主键
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delClass")
    public String delClass(Integer id, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        sClassService.deleteByPrimaryKey(id);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 16:16
     * 根据班级查询学生
     *
     * @param param   筛选条件
     * @param classId 班级id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryStudentByClassId")
    public String queryStudentByClassId(String param, Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        List<SStudent> result = sStudentService.queryStudentByClassId(param, classId);
        return ResultUtils.successStr(result);
    }

    /**
     * 2018/7/4 16:17
     * 根据id查询学生
     *
     * @param id 学生id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryStudentById")
    public String queryStudentById(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        SStudent sStudent = sStudentService.selectByPrimaryKey(id);
        return ResultUtils.successStr(sStudent);
    }

    /**
     * 2018/7/4 16:17
     * 添加修改学生
     *
     * @param sStudent 学生对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateStudent")
    public String addOrUpdateStudent(SStudent sStudent, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        SStudent temp = sStudentService.selectBySNumForValidate(getLoginSchool(request).getSchoolId(), sStudent.getsNum());
        if (sStudent.getId() == null && temp != null) {
            return ResultUtils.warnStr(ResultCode.STUDENT_EX);
        } else if (sStudent.getId() != null && temp != null && temp.getId().intValue() != sStudent.getId().intValue()) {
            return ResultUtils.warnStr(ResultCode.STUDENT_EX);
        } else {
            sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            return ResultUtils.successStr();
        }
    }

    /**
     * 2018/7/4 16:18
     * 删除学生
     *
     * @param id 学生id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delStudent")
    public String delStudent(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        sStudentService.deleteByPrimaryKey(id);
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 12:51
     * 导出学生 excel
     *
     * @param classId 班级id
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
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
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 12:52
     * 导入学生 excel
     *
     * @param classId 班级id
     * @return {"resultCode":200,"resultMsg":"成功"}  code:1015 存在失败  300 操作失败
     * @author 曹良峰
     */
    @RequestMapping("/importExcelForStudent")
    @ResponseBody
    public String importExcelForStudent(Integer classId, MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("importExcel");// 与页面input的name相同
            if (FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xlsx") == -1 && FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xls") == -1) {
                return ResultUtils.warnStr(ResultCode.FILE_IS_NOT_RIGHT);
            }
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);
            Integer schoolId = getLoginSchool(request).getSchoolId();
            Map<String, Object> temp = sStudentService.insertBatchStudent(sStudents, schoolId, classId);
            List<ImportStudentVo> errorInsert = (List<ImportStudentVo>) temp.get("errorInsert");
            int index = (Integer) temp.get("index");
            Map<String, Object> result = new HashMap<>();
            result.put("total", sStudents.size());
            result.put("failList", errorInsert);
            if (index > 0) {
                return ResultUtils.warnStr(ResultCode.IMPORTERROR_STUDENT, result);
            } else {
                return ResultUtils.successStr(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.warnStr(ResultCode.ERROR);
        }
    }
}
