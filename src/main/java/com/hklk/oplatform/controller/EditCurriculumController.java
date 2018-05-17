package com.hklk.oplatform.controller;

import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.ConsumablesService;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.PageService;
import com.hklk.oplatform.service.RolePageService;
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

@RequestMapping("/editcm")
@Controller
public class EditCurriculumController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    ConsumablesService consumablesService;

    private int pageSize = 10;

    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        PageTableForm<Curriculum> curriculumPageTableForm = curriculumService.queryCurriculums(pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(int id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {

        Curriculum curriculum = curriculumService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }

    @ResponseBody
    @RequestMapping("/addCurriculum")
    public String queryCurriculum(Curriculum curriculum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        curriculumService.addCurriculum(curriculum);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updateCurriculum")
    public String updateCurriculum(Curriculum curriculum, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        curriculumService.updateCurriculum(curriculum);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deleteCurriculum")
    public String deleteCurriculum(int id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        curriculumService.deleteCurriculum(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryConsumablesByCurId")
    public String queryConsumablesByCurId(int curId, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        List<Consumables> consumablesList = consumablesService.queryConsumablesByCurId(curId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), consumablesList);
    }

    @ResponseBody
    @RequestMapping("/addConsumables")
    public String addConsumables(Consumables consumables, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        consumablesService.insertSelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updateConsumables")
    public String updateConsumables(Consumables consumables, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.updateByPrimaryKeySelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deleteConsumables")
    public String deleteConsumables(int id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
