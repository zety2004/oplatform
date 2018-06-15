package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SCApplyService;
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

@RequestMapping("/teacherApply")
@Controller
public class TeacherApplyCurriculumController extends BaseController {

    @Autowired
    CurriculumService curriculumService;
    @Autowired
    SCApplyService scApplyService;

    @Autowired
    STeacherService sTeacherService;

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
    @RequestMapping("/addOrUpdateCurriculumApply")
    public String addOrUpdateCurriculumApply(SCApply scApply, HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        if (scApply.getId() == null) {
            scApplyService.insertSelective(scApply);
        } else {
            scApplyService.updateByPrimaryKeySelective(scApply);
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
