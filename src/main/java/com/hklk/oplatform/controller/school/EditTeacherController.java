package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.*;
import com.hklk.oplatform.entity.vo.ImportStudentVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.DicService;
import com.hklk.oplatform.service.STeacherService;
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

/**
 * 学校管理老师接口
 *
 * @author 曹良峰
 * @since 1.0
 */
@SchoolLoginRepository
@RequestMapping("/editTeacher")
@Controller
public class EditTeacherController extends BaseController {
    @Autowired
    STeacherService sTeacherService;
    @Autowired
    DicService dicService;

    /**
     * 2018/7/4 16:09
     * 查询老师
     *
     * @param param   筛选条件
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryTeachers")
    public String queryTeachers(String param, int pageNum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        PageTableForm<STeacher> pageTableForm = sTeacherService.queryTeacherBySchoolId(getLoginSchool(request).getSchoolId(), param, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    /**
     * 2018/7/4 16:10
     * 根据条件查询老师
     *
     * @param sTeacher 老师对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectTeacherByParam")
    public String selectTeacherByParam(STeacher sTeacher, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        sTeacher.setSchoolId(getLoginSchool(request).getSchoolId());
        STeacher result = sTeacherService.selectBySTeacher(sTeacher);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    /**
     * 2018/7/4 16:10
     * 添加或修改老师
     *
     * @param sTeacher 老师对象
     * @return code ：1009 老师已存在  200 成功
     * @author 曹良峰
     */
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
        } else if (sTeacher.getId() != null && result != null && result.getId().intValue() != sTeacher.getId().intValue()) {
            return ToolUtil.buildResultStr(StatusCode.TEACHER_EX, StatusCode.getStatusMsg(StatusCode.TEACHER_EX));
        } else {
            sTeacher.setSchoolId(schoolId);
            List<Map<String, Object>> tags = dicService.queryForList("4");
            StringBuffer tag = new StringBuffer();
            List<Integer> indexs = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Integer index = (int) (1 + Math.random() * (tags.size() - 1 + 1));
                while (indexs.contains(index)) {
                    index = (int) (1 + Math.random() * (tags.size() - 1 + 1));
                }
                indexs.add(index);
                if (i == 2) {
                    tag.append(tags.get(index).get("name").toString());
                } else {
                    tag.append(tags.get(index).get("name").toString()).append(",");
                }
            }
            sTeacher.setTag(tag.toString());
            sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 12:51
     * 导出老师列表 excel
     *
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @RequestMapping("/exportExcelForTeacher")
    @ResponseBody
    public String exportExcelForTeacher(HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {

        String[] columnHeader = {"tName", "phone", "sex", "remark"};
        String[] fieldNames = {"名称", "手机号", "性别", "备注"};
        List<STeacher> sTeachers = sTeacherService.queryTeacherBySchoolIdForList(getLoginSchool(request).getSchoolId());
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=students.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", sTeachers, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 12:52
     * 导入学生 excel
     *
     * @return {"resultCode":200,"resultMsg":"成功"}  code:1015 存在失败  300 操作失败
     * @author 曹良峰
     */
    @RequestMapping("/importExcelForTeacher")
    @ResponseBody
    public String importExcelForTeacher(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("importExcel");// 与页面input的name相同
            if (FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xlsx") == -1 && FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xls") == -1) {
                return ToolUtil.buildResultStr(StatusCode.FILE_IS_NOT_RIGHT, StatusCode.getStatusMsg(StatusCode.FILE_IS_NOT_RIGHT));
            }
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String[] fieldNames = {"tName", "phone", "sex", "remark"};
            List<STeacher> sTeachers = ExcelUtils.importExcel(fileTemp, STeacher.class, fieldNames, 1, 0, 0);
            Integer schoolId = getLoginSchool(request).getSchoolId();
            List<Map<String, Object>> errorInsert = new ArrayList<>();
            int index = 0;
            for (STeacher sTeacher : sTeachers) {
                if (sTeacher.gettName() == null || "".equals(sTeacher.gettName())) {
                    Map<String, Object> importMap = new HashMap<>();
                    importMap.put("tName", sTeacher.gettName());
                    importMap.put("phone", sTeacher.getPhone());
                    importMap.put("reason", "缺少关键字段，请检查后重新导入");
                    errorInsert.add(importMap);
                    index++;
                    continue;
                }
                STeacher param = new STeacher();
                param.setSchoolId(schoolId);
                param.setPhone(sTeacher.getPhone());
                STeacher temp = sTeacherService.selectBySTeacher(param);
                if (temp != null) {
                    Map<String, Object> importMap = new HashMap<>();
                    importMap.put("tName", sTeacher.gettName());
                    importMap.put("phone", sTeacher.getPhone());
                    importMap.put("reason", "该老师已存在系统中");
                    errorInsert.add(importMap);
                    index++;
                } else {
                    sTeacher.setSchoolId(schoolId);
                    sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", sTeachers.size());
            result.put("failList", errorInsert);
            if (index > 0) {
                return ToolUtil.buildResultStr(StatusCode.IMPORTERROR_STUDENT, StatusCode.getStatusMsg(StatusCode.IMPORTERROR_STUDENT), result);
            } else {
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }


    @ResponseBody
    @RequestMapping("/resetTeacherPwd")
    public String updateSchoolAdmin(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        STeacher sTeacher = new STeacher();
        sTeacher.setId(id);
        sTeacher.setPwd("93b1c7f49c7b917831a942fd90ffe0ca");

        sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 16:12
     * 修改老师状态
     *
     * @param id     老师id
     * @param status 状态
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateTeacherStatus")
    public String updateTeacherStatus(Integer id, Integer status, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        STeacher param = new STeacher();
        param.setId(id);
        param.setStatus(status);
        sTeacherService.insertOrUpdateByPrimaryKeySelective(param);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 16:13
     * 删除老师
     *
     * @param id 老师id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delTeacher")
    public String delTeacher(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        STeacher sTeacher = new STeacher();
        sTeacher.setId(id);
        sTeacher.setStatus(0);
        sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
