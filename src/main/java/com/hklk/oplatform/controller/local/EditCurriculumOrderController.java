package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.StudentPay;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.SCApplyService;
import com.hklk.oplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        return ResultUtils.successStr(curriculumPageTableForm);
    }


    /**
     * 2018/9/18 15:11
     * 导出订单列表
     *
     * @param queryParam 文字搜索
     * @param isHandle   是否处理
     * @param ishc       是否有耗材
     * @return java.lang.String
     * @author 曹良峰
     */
    @RequestMapping("/exportExcelForOrders")
    @ResponseBody
    public String exportExcelForOrders(String queryParam, Integer isHandle, Integer ishc, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {

        String[] columnHeader = {"curriculumName", "teacherName", "schoolName", "beginOfSelectTime", "endOfSelectTime", "maxNum", "studentNum", "opPerson", "orderRemark", "kcPrice", "hcPrice"};
        String[] fieldNames = {"课程名称", "授课老师", "所属学校", "开始选课时间", "结束选课时间", "名额", "已报名人数", "订单操作人", "订单备注", "课程金额", "耗材金额"};
        List<CurriculumOrderVo> result = scApplyService.queryCurriculumOrderForList(queryParam, isHandle, ishc);
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=orders.xlsx");
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
        if (temp.getOrderOpUserId() != null) {
            return ResultUtils.warnStr(ResultCode.ORDER_IS_HANDLE);
        } else {
            SCApply scApply = new SCApply();
            scApply.setId(id);
            scApply.setOrderOpUserId(getLoginUser(request).getUserId());
            scApply.setOrderRemark(remark);
            scApplyService.updateByPrimaryKeySelective(scApply);
            return ResultUtils.successStr();
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
        return ResultUtils.successStr(studentPays);
    }
}
