package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.StudentPay;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
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

@LocalLoginRepository
@RequestMapping("/editcmo")
@Controller
public class EditCurriculumOrderController extends BaseController {
    @Autowired
    SCApplyService scApplyService;

    protected int pageSize = 12;

    @ResponseBody
    @RequestMapping("/queryCurriculumOrder")
    public String queryCurriculumOrder(Integer isHandle, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumOrderVo> curriculumPageTableForm = scApplyService.queryCurriculumOrder(isHandle, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/handleCurriculumOrder")
    public String handleCurriculumOrder(String remark, Integer id, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        SCApply temp = scApplyService.selectByPrimaryKey(id);
        if (temp.getOperatorId() != null) {
            return ToolUtil.buildResultStr(StatusCode.ORDER_IS_HANDLE, StatusCode.getStatusMsg(StatusCode.ORDER_IS_HANDLE));
        } else {
            SCApply scApply = new SCApply();
            scApply.setId(id);
            scApply.setOrderOpUserId(getLoginUser(request).getUserId());
            scApply.setOrderRemark(remark);
            scApplyService.updateByPrimaryKeySelective(scApply);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }


    @ResponseBody
    @RequestMapping("/queryOrderPlayFor")
    public String queryOrderPlayFor(Integer scaId, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        List<StudentPay> studentPays = scApplyService.queryStudentBySCAId(scaId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), studentPays);
    }
}
