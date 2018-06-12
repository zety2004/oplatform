package com.hklk.oplatform.controller.school;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
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
import java.util.Date;
import java.util.List;

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
    public String queryCurriculumApply(Integer status, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumApplyVo> curriculumPageTableForm = scApplyService.queryCurriculumApply(getLoginSchool(request).getSchoolId(), status, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryCurriculumChoice")
    public String queryCurriculumChoice(int pageNum,String param, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm = scApplyService.queryCurriculumChoice(getLoginSchool(request).getSchoolId(),param, pageNum, pageSize);
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
        if (temp.getOperatorId() != null) {
            return ToolUtil.buildResultStr(StatusCode.CHECK_OPERATOR, StatusCode.getStatusMsg(StatusCode.CHECK_OPERATOR));
        } else {
            scApplyService.updateByPrimaryKeySelective(scApply);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    @ResponseBody
    @RequestMapping("/addOrUpdateSyllabus")
    public String addOrUpdateSyllabus(Integer id, Integer scaId, Integer timeType, Integer weekType, String classPlace, Date beginOfSelectTime, Date endOfSelectTime, Date currStartTime, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        SSyllabus sSyllabus = new SSyllabus();
        sSyllabus.setId(id);
        sSyllabus.setScaId(scaId);
        sSyllabus.setSchoolId(getLoginSchool(request).getSchoolId());
        sSyllabus.setTimeType(timeType);
        sSyllabus.setWeekType(weekType);

        SCApply scApply = new SCApply();
        scApply.setId(scaId);
        scApply.setClassPlace(classPlace);
        scApply.setBeginOfSelectTime(beginOfSelectTime);
        scApply.setEndOfSelectTime(endOfSelectTime);
        scApply.setStatus(3);
        scApply.setCurrStartTime(currStartTime);


        sSyllabusService.insertOrUpdateByPrimaryKeySelective(sSyllabus, scApply);

        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
