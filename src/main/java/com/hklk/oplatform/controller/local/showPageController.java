package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.ConsumablesService;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SCApplyService;
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
import java.util.Map;

@RequestMapping("/show")
@Controller
public class showPageController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    ConsumablesService consumablesService;
    @Autowired
    SCApplyService scApplyService;

    protected int pageSize = 12;

    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        curriculum.setIsPublic(1);
        PageTableForm<CurriculumForListVo> curriculumPageTableForm = curriculumService.queryCurriculums(curriculum, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryCurriculumForList")
    public String queryCurriculum() {
        List<CurriculumForListVo> curriculumPageTableForm = curriculumService.queryCurriculums();
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        Map<String, Object> curriculum = curriculumService.selectByPrimaryKey(id);
        curriculum.put("enclosure", null);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }
}
