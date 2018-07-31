package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.StudentChoice;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.*;
import com.hklk.oplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 家长课程查询操作
 *
 * @author 曹良峰
 * @since 1.0
 */

@RequestMapping("/parentCurriculum")
@Controller
public class ParentCurriculumController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    SCApplyService scApplyService;
    @Autowired
    SSyllabusService sSyllabusService;
    @Autowired
    StudentChoiceService studentChoiceService;
    @Autowired
    ParentMessageService parentMessageService;

    /**
     * 2018/7/4 17:17
     * 上传文件
     *
     * @param request （uploadfile）
     * @return java.lang.String
     * @author 曹良峰
     */

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("uploadfile");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            System.out.println(PasswordProvider.getMd5ByFile(fileTemp));
            String fileKey = "KCGX" + PasswordProvider.getMd5ByFile(fileTemp) + "." + FileUtils.getFilenameExtension(file.getOriginalFilename());
            OssUtil.uploadFile(fileKey, fileTemp);
            String accessToDomainNames = PropUtil.getProperty("ossAccessToDomainNames") + "/" + fileKey;
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), accessToDomainNames);
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.UPLOAD_ERROR, StatusCode.getStatusMsg(StatusCode.UPLOAD_ERROR));
        }
    }


    /**
     * 2018/7/4 12:11
     * 家长查询课程详情
     *
     * @param scaId
     * @return {"resultData":{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","enclosure":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX43743b8d4baee12223be9382e13c2736.zip","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"des":"","consumables":[]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectApplyCurriculumById")
    public String selectApplyCurriculumById(Integer scaId, HttpServletRequest request,
                                            HttpServletResponse response, HttpSession session) {
        Map<String, Object> curriculum = scApplyService.selectByApplyCurriculmForParentById(scaId);

        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }

    /**
     * 2018/7/16 15:25
     * 家长查询课程页面
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryHotCurriculumByParent")
    public String queryHotCurriculumByParent(HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        Map<String, List<Map<String, Object>>> curriculumPageTableForm = scApplyService.queryHotCurriculumForParent(loginParent.getSchoolId(), loginParent.getGrade());
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    /**
     * 2018/7/16 15:25
     * 家长查询课程页面
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryAllCurriculumByParent")
    public String queryAllCurriculumByParent(Integer isAll, HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        List<Map<String, Object>> curriculumChoiceVos;
        if (isAll == null || "".equals(isAll)) {
            curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(loginParent.getSchoolId(), null);
        } else {
            curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(loginParent.getSchoolId(), loginParent.getGrade());
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumChoiceVos);
    }


    /**
     * 2018/7/16 15:25
     * 家长选课
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/saveStudentChoice")
    public String insertStudentChoice(Integer scaId, String curriculumName, String payMoney, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        Map<String, Object> isApply = studentChoiceService.queryParentApplyForIsApply(scaId, loginParent.getStudentId());
        if (isApply != null) {
            if ((int) isApply.get("pay_state") == 0) {
                return ToolUtil.buildResultStr(StatusCode.BUY_CURR_FOR_PARENT, "您已申请该课程，尚未付款，请前往订单页完成支付！");
            } else {
                return ToolUtil.buildResultStr(StatusCode.BUY_CURR_FOR_PARENT, "您已申请该课程！");
            }
        }
        //验证不通课程是否时间冲突
        Integer num = studentChoiceService.queryParentApplyForVerification(loginParent.getSchoolId(), scaId, loginParent.getStudentId());
        if (num > 1) {
            return ToolUtil.buildResultStr(StatusCode.INSERT_ERROR_FOR_PARENT_APPLY, StatusCode.getStatusMsg(StatusCode.INSERT_ERROR_FOR_PARENT_APPLY));
        } else {
            String orderId = System.currentTimeMillis() + "";
            StudentChoice studentChoice = new StudentChoice();
            studentChoice.setOrderId(orderId);
            studentChoice.setScaId(scaId);
            studentChoice.setStudentId(loginParent.getStudentId());
            studentChoice.setPayMoney(payMoney);
            studentChoice.setCommodityName(curriculumName);
            studentChoiceService.insertSelective(studentChoice);
            ParentMessage parentMessage = new ParentMessage();
            parentMessage.setStudentId(loginParent.getStudentId());
            parentMessage.setMessage("您为 " + loginParent.getChildName() + "同学报名成功 " + curriculumName + " 课程！");
            parentMessageService.insertSelective(parentMessage);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), orderId);
        }
    }


    @ResponseBody
    @RequestMapping("/wxPay")
    public String wxPay(String orderId, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {
        Map<String, Object> order = studentChoiceService.selectByOrderId(orderId);
        if ((double) order.get("payMoney") == (double) order.get("realMoney") && (double) order.get("payMoney") == 0) {
            StudentChoice studentChoice = new StudentChoice();
            studentChoice.setId((int) order.get("id"));
            studentChoice.setPayState(1);
            studentChoiceService.updateByPrimaryKeySelective(studentChoice);
            return ToolUtil.buildResultStr(StatusCode.DONOT_NEED_PAY, "订单金额为0，无需支付!");
        } else if ((double) order.get("payMoney") != (double) order.get("realMoney")) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, "订单金额不正确，支付失败!");
        }
        Map<String, Object> parameters = PayUtil.getWXPrePayID();
        parameters.put("body", "好课乐课-课程选课");
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("out_trade_no", order.get("orderId"));
        parameters.put("notify_url", PropUtil.getProperty("notifyUrl"));
        parameters.put("openid", getLoginParent(request).getOpenid());
        parameters.put("sign_type", "MD5");
        parameters.put("total_fee", "1"); // 测试
        //parameters.put("total_fee", (Double) order.get("realMoney") * 100); // 上线后，将此代码放开

        String sign = PayUtil.getSign(parameters);
        parameters.put("sign", sign);
        // 封装请求参数结束
        String requestXML = PayUtil.getRequestXml(parameters); // 获取xml结果
        // 调用统一下单接口
        String result = PayUtil.httpsRequest(PropUtil.getProperty("payUrl"), "POST", requestXML);
        Map<String, Object> parMap = PayUtil.startWXPay(result);
        if (parMap == null) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, "支付出现异常，请稍后重试!");
        } else {
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), parMap);
        }
    }

    @RequestMapping("/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        String result = PayUtil.reciverWx(request); // 接收到异步的参数
        Map<String, String> m = new HashMap<>();// 解析xml成map
        if (m != null && !"".equals(m)) {
            m = PayUtil.xmlStr2Map(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml;
        if (PayUtil.isTenpaySign(packageParams)) {
            if ("SUCCESS".equals(packageParams.get("return_code"))) {
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
                // 查询订单 根据订单号查询订单
                String orderId = out_trade_no;
                Map<String, Object> orders = studentChoiceService.selectByOrderId(orderId);

                // 验证商户ID 和 价格 以防止篡改金额
                if (PropUtil.getProperty("mchId").equals(mch_id)
                    //&& orders != null
                    //&&
                    // total_fee.trim().toString().equals(orders.getOrderAmount())
                    // // 实际项目中将此注释删掉，以保证支付金额相等
                        ) {

                    // TODO Auto-generated catch block


                    resXml = PayUtil.setXML("SUCCESS", "OK");
                } else {
                    resXml = PayUtil.setXML("FAIL", "参数错误");
                }
            } else // 如果微信返回支付失败，将错误信息返回给微信
            {
                resXml = PayUtil.setXML("FAIL", "交易失败");
            }
        } else {
            resXml = resXml = PayUtil.setXML("FAIL", "通知签名验证失败");
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 2018/7/16 15:25
     * 家长查询课程页面
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryMyCurriculum")
    public String queryMyCurriculum(Integer isEnd, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        List<Map<String, Object>> myCurriculum = studentChoiceService.queryMyCurriculumList(loginParent.getStudentId(), isEnd);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), myCurriculum);
    }

    @ResponseBody
    @RequestMapping("/delOrder")
    public String delOrder(Integer sccId, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        studentChoiceService.deleteByPrimaryKey(sccId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/16 15:25
     * 家长查询课程页面
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumForWeekType")
    public String queryCurriculumForWeekType(HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        Map<String, Object> result = new HashMap<>();
        result.put("Mon", studentChoiceService.queryMyCurriculum(loginParent.getStudentId(), 1));
        result.put("Tues", studentChoiceService.queryMyCurriculum(loginParent.getStudentId(), 2));
        result.put("Wed", studentChoiceService.queryMyCurriculum(loginParent.getStudentId(), 3));
        result.put("Thur", studentChoiceService.queryMyCurriculum(loginParent.getStudentId(), 4));
        result.put("Fri", studentChoiceService.queryMyCurriculum(loginParent.getStudentId(), 5));
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }
}
