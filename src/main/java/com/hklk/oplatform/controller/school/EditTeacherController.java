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

    /**
     * 2018/7/4 16:09
     * 查询老师
     * @param param     筛选条件
     * @param pageNum   分页参数
     * @author 曹良峰
     * @return java.lang.String
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
     * @param sTeacher  老师对象
     * @author 曹良峰
     * @return java.lang.String
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
     * @param sTeacher 老师对象
     * @author 曹良峰
     * @return code ：1009 老师已存在  200 成功
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
        } else if (sTeacher.getId() != null && result != null && result.getId() != sTeacher.getId()) {
            return ToolUtil.buildResultStr(StatusCode.TEACHER_EX, StatusCode.getStatusMsg(StatusCode.TEACHER_EX));
        } else {
            sTeacher.setSchoolId(schoolId);
            sTeacherService.insertOrUpdateByPrimaryKeySelective(sTeacher);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }
    
    /**
     * 2018/7/4 16:12
     * 修改老师状态
     * @param id    老师id
     * @param status    状态
     * @author 曹良峰
     * @return java.lang.String
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
     * @param id    老师id
     * @author 曹良峰
     * @return java.lang.String
     */
    @ResponseBody
    @RequestMapping("/delTeacher")
    public String delTeacher(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        sTeacherService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
