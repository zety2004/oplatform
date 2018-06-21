package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SSyllabus;
import com.hklk.oplatform.entity.vo.*;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SCApplyService;
import com.hklk.oplatform.service.SSyllabusService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@SchoolLoginRepository
@RequestMapping("/schoolCurriculum")
@Controller
public class SchoolCurriculumController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    SCApplyService scApplyService;
    @Autowired
    SSyllabusService sSyllabusService;

    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumForListVo> curriculumPageTableForm = curriculumService.queryCurriculums(curriculum, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        CurriculumVo curriculum = curriculumService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }

    @ResponseBody
    @RequestMapping("/queryCurriculumApply")
    public String queryCurriculumApply(String param, Integer status, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        if (status == null) {
            status = 0;
        }
        PageTableForm<CurriculumApplyVo> curriculumPageTableForm = scApplyService.queryCurriculumApply(getLoginSchool(request).getSchoolId(), status, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryCurriculumChoice")
    public String queryCurriculumChoice(int pageNum, String param, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm = scApplyService.queryCurriculumChoice(getLoginSchool(request).getSchoolId(), param, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumChoiceVoPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryStudentBySCAId")
    public String queryStudentBySCAId(Integer scaId, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        List<StudentPay> sStudents = scApplyService.queryStudentBySCAId(scaId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), sStudents);
    }

    @ResponseBody
    @RequestMapping("/updateCurriculumApply")
    public String updateCurriculumApply(SCApply scApply, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        SCApply temp = scApplyService.selectByPrimaryKey(scApply.getId());
        if (temp.getOperatorId() != null && scApply.getStatus() != 0) {
            return ToolUtil.buildResultStr(StatusCode.CHECK_OPERATOR, StatusCode.getStatusMsg(StatusCode.CHECK_OPERATOR));
        } else {
            scApplyService.updateByPrimaryKeySelective(scApply);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateSyllabusAll")
    public String addOrUpdateSyllabusAll(String param, HttpServletRequest request,
                                         HttpServletResponse response, HttpSession session) {
        sSyllabusService.delAndbatchSaveSyllabus(param, getLoginSchool(request).getSchoolId());
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/querySyllabusAll")
    public String querySyllabusAll(HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String classTime = "";
        for (int i = 1; i < 6; i++) {
            List<Map<String, String>> temp = sSyllabusService.queryMapByWeekType(i, getLoginSchool(request).getSchoolId());
            if ("".equals(classTime) && temp.size() > 0) {
                classTime = temp.get(0).get("classTime");
                result.put("classTime", classTime);
            }
            result.put("week" + i, temp);
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/querySyllabusByTimeType")
    public String querySyllabusByTimeType(HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new LinkedHashMap<>();
        Integer schoolId = getLoginSchool(request).getSchoolId();
        Integer maxTimeType = sSyllabusService.selectMaxTimeType(schoolId);
        for (int i = 0; i <= maxTimeType; i++) {
            result.put("timeType" + i, sSyllabusService.queryMapByTimeType(i, schoolId));
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    @ResponseBody
    @RequestMapping("/querySyllabusByTimeTypeForEnd")
    public String querySyllabusByTimeTypeForEnd(HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            result.put("week" + i, sSyllabusService.queryMapByTimeTypeForEnd(i, getLoginSchool(request).getSchoolId()));
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }
}
