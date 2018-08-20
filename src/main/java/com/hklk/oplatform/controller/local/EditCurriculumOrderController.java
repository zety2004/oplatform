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

/**
 * 运营课订单管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editcmo")
@Controller
public class EditCurriculumOrderController extends BaseController {
    @Autowired
    SCApplyService scApplyService;

    protected int pageSize = 12;

    /**
     * 2018/7/4 17:28
     * 查询订单
     *
     * @param queryParam 筛选条件
     * @param isHandle   可否操作
     * @param pageNum    分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumOrder")
    public String queryCurriculumOrder(String queryParam, Integer isHandle, Integer ishc, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumOrderVo> curriculumPageTableForm = scApplyService.queryCurriculumOrder(queryParam, isHandle, ishc, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    /**
     * 2018/7/4 17:28
     * 处理订单
     *
     * @param remark 订单备注
     * @param id     订单id
     * @return java.lang.String
     * @author 曹良峰
     */
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

    /**
     * 2018/7/4 17:29
     * 查询订单支付情况
     *
     * @param scaId 订单id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryOrderPlayFor")
    public String queryOrderPlayFor(Integer scaId, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        List<StudentPay> studentPays = scApplyService.queryStudentBySCAId(scaId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), studentPays);
    }
}
