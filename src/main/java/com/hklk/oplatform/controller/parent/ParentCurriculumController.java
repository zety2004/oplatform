package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.StudentChoice;
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
        Map<String, List<Map<String, Object>>> curriculumPageTableForm = scApplyService.queryHotCurriculumForParent(loginParent.getSchoolId(), null);
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
    public String queryAllCurriculumByParent(Integer isAll, Integer weekType, HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        List<Map<String, Object>> curriculumChoiceVos;
        if (isAll == null || "".equals(isAll)) {
            curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(loginParent.getSchoolId(), null, weekType);
        } else {
            curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(loginParent.getSchoolId(), loginParent.getGrade(), weekType);
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
    public String insertStudentChoice(Integer scaId, int isHc, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        //未绑定学生
        if (loginParent == null || loginParent.getStudentId() == null) {
            return ToolUtil.buildResultStr(StatusCode.NO_BINDING_STUDENT, StatusCode.getStatusMsg(StatusCode.NO_BINDING_STUDENT));
        }
        //验证该课程是否可以选择(查询该课程是否是该学校的)
        Integer isQualified = studentChoiceService.queryParentApplyForIsQualified(loginParent.getSchoolId(), loginParent.getGrade().toString(), scaId);
        if (isQualified == null || isQualified == 0) {
            return ToolUtil.buildResultStr(StatusCode.STUDENT_IS_NO_QUALIFIED, StatusCode.getStatusMsg(StatusCode.STUDENT_IS_NO_QUALIFIED));
        }
        //验证课程是否在选课时间内
        Integer verificationTimeNum = studentChoiceService.queryParentApplyForVerificationTime(scaId);
        if (verificationTimeNum == 0) {
            return ToolUtil.buildResultStr(StatusCode.PARENT_APPLY_CURR_FOR_VER_NUM, StatusCode.getStatusMsg(StatusCode.PARENT_APPLY_CURR_FOR_VER_NUM));
        }

        int isApply = studentChoiceService.queryParentApplyForIsApply(scaId, loginParent.getStudentId());
        if (isApply > 0) {
            return ToolUtil.buildResultStr(StatusCode.BUY_CURR_FOR_PARENT, "您申请的课程存在时间冲突，或存在未支付的订单！");
        } else {
            Map<String, Object> objectMap = studentChoiceService.queryCurriculumForParentApply(scaId);
            String orderId = System.currentTimeMillis() + "";
            StudentChoice studentChoice = new StudentChoice();
            studentChoice.setOrderId(orderId);
            studentChoice.setScaId(scaId);
            studentChoice.setStudentId(loginParent.getStudentId());
            studentChoice.setPayMoney((Double) objectMap.get("kcPrice") + (Double) objectMap.get("hcPrice"));
            if (isHc == 0) {
                studentChoice.setPayHcMoney(0.00);
            } else {
                studentChoice.setPayHcMoney((Double) objectMap.get("hcPrice"));
            }
            studentChoice.setCommodityName(objectMap.get("name").toString());
            studentChoiceService.insertSelective(studentChoice);
            ParentMessage parentMessage = new ParentMessage();
            parentMessage.setStudentId(loginParent.getStudentId());
            parentMessage.setMessage("您为 " + loginParent.getChildName() + "同学成功报名 " + objectMap.get("name").toString() + " 课程！请确认支付。");
            parentMessageService.insertSelective(parentMessage);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), orderId);
        }
    }


    @ResponseBody
    @RequestMapping("/wxPay")
    public String wxPay(String orderId, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {
        Map<String, Object> order = studentChoiceService.selectByOrderId(orderId);
        if ((double) order.get("payMoney") == 0) {
            StudentChoice studentChoice = new StudentChoice();
            studentChoice.setId((int) order.get("id"));
            studentChoice.setPayState(1);
            studentChoiceService.updateByPrimaryKeySelective(studentChoice);
            return ToolUtil.buildResultStr(StatusCode.DONOT_NEED_PAY, "订单金额为0，无需支付!");
        }
        Map<String, Object> parameters = PayUtil.getWXPrePayID();
        parameters.put("body", "好课乐课-课程选课");
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("out_trade_no", order.get("orderId"));
        parameters.put("notify_url", PropUtil.getProperty("notifyUrl"));
        parameters.put("openid", getLoginParent(request).getOpenid());
        parameters.put("sign_type", "MD5");
        // parameters.put("total_fee", 1); // 上线后，将此代码放开
        parameters.put("total_fee", (int) ((double) order.get("payMoney") * 100)); // 上线后，将此代码放开

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

    @RequestMapping("/wxPayNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = PayUtil.reciverWx(request);
        Map<String, String> m = new HashMap<>();
        if (m != null && !"".equals(m)) {
            m = PayUtil.xmlStr2Map(result);
        }
        System.out.println("wxNotify:" + JsonUtil.toJson(m));
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
        String resXml;
        if (PayUtil.isTenpaySign(packageParams)) {
            if ("SUCCESS".equals(packageParams.get("return_code"))) {
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号

                String orderId = out_trade_no;
                Map<String, Object> orders = studentChoiceService.selectByOrderId(orderId);
                System.out.println("订单信息:" + orders);
                String payMoney = String.valueOf(((Double) ((double) orders.get("payMoney") * 100)).intValue());
                if (PropUtil.getProperty("mchId").equals(mch_id) && orders != null && total_fee.trim().equals(payMoney)) {
                    StudentChoice studentChoice = new StudentChoice();
                    studentChoice.setPayState(1);
                    studentChoice.setId((Integer) orders.get("id"));
                    studentChoice.setTransactionId(transaction_id);
                    studentChoiceService.updateByPrimaryKeySelective(studentChoice);

                    ParentMessage parentMessage = new ParentMessage();
                    parentMessage.setStudentId((Integer) orders.get("studentId"));
                    parentMessage.setMessage("您的申请的课程  《" + orders.get("curriculumNum") + "》  已经支付成功，请确认信息。");
                    parentMessageService.insertSelective(parentMessage);
                    resXml = PayUtil.setXML("SUCCESS", "OK");
                } else {
                    resXml = PayUtil.setXML("FAIL", "参数错误");
                }
            } else {
                resXml = PayUtil.setXML("FAIL", "交易失败");
            }
        } else {
            resXml = PayUtil.setXML("FAIL", "通知签名验证失败");
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        System.out.println("返回微信消息:" + resXml);
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @ResponseBody
    @RequestMapping("/returnPay")
    public String returnPay(String orderId) throws Exception {
        Map<String, Object> order = studentChoiceService.selectByOrderId(orderId);

        int isCanBeRefund = studentChoiceService.queryIsCanRefund((int) order.get("scaId"));

        if (isCanBeRefund != 1) {
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, "这条订单不满足退款条件哦。");
        }

        if (Double.valueOf(order.get("payMoney").toString()).intValue() == 0) {
            this.updateRefundOrder(order);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, "取消报名成功");
        }

        String out_trade_no = orderId;   //退款订单
        int all_total_fee = ((Double) ((double) order.get("payMoney") * 100)).intValue();
        int refund_fee = ((Double) ((double) order.get("payMoney") * 100)).intValue();
//        int all_total_fee = 1;    //订单金额
//        int refund_fee = 1;      //退款金额
        String appid = PropUtil.getProperty("wxAppid");
        String mch_id = PropUtil.getProperty("mchId");

        Map<String, Object> packageParams = new HashMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("op_user_id", mch_id);
        packageParams.put("nonce_str", PayUtil.CreateNoncestr());
        packageParams.put("notify_url", PropUtil.getProperty("returnNotifyUrl"));
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("out_refund_no", PayUtil.CreateNoncestr());
        packageParams.put("total_fee", String.valueOf(all_total_fee));
        packageParams.put("refund_fee", String.valueOf(refund_fee));
        String sign = PayUtil.createSign(packageParams);
        packageParams.put("sign", sign);

        String XML = PayUtil.getRequestXml(packageParams);

        String result = ClientCustomSSL.doRefund(PropUtil.getProperty("returnUrl"), XML);
        Map<String, String> refundmap = PayUtil.xmlStr2Map(result);
        if (refundmap.get("return_code").equals("SUCCESS")) {
            if (refundmap.get("result_code").equals("FAIL")) {
                return ToolUtil.buildResultStr(10000, "退款失败:原因" + refundmap.get("err_code_des"));
            } else {
                this.updateRefundOrder(order);
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, "退款成功");
            }
        } else {
            return ToolUtil.buildResultStr(10000, "退款失败:原因" + refundmap.get("return_ms"));
        }
    }

    private void updateRefundOrder(Map<String, Object> order) {
        StudentChoice studentChoice = new StudentChoice();
        studentChoice.setPayState(2);
        studentChoice.setId((Integer) order.get("id"));
        studentChoiceService.updateByPrimaryKeySelective(studentChoice);
        ParentMessage parentMessage = new ParentMessage();
        parentMessage.setStudentId((Integer) order.get("studentId"));
        parentMessage.setMessage("您申报的课程 《" + order.get("curriculumNum") + "》 已经退款成功，请确认信息。");
        parentMessageService.insertSelective(parentMessage);

    }

    @RequestMapping("/wxRefundNotify")
    @ResponseBody
    public void wxRefundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = PayUtil.reciverWx(request);
        Map<String, String> m = new HashMap<>();
        if (m != null && !"".equals(m)) {
            m = PayUtil.xmlStr2Map(result);
        }
        System.out.println("wxRefundNotify:" + JsonUtil.toJson(m));
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
        String resXml;
        if (PayUtil.isTenpaySign(packageParams)) {
            if ("SUCCESS".equals(packageParams.get("return_code"))) {
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");

                String orderId = out_trade_no;
                Map<String, Object> orders = studentChoiceService.selectByOrderId(orderId);
                System.out.println("订单信息:" + orders);
                if (PropUtil.getProperty("mchId").equals(mch_id) && orders != null && total_fee.trim().equals(String.valueOf(((Double) ((double) orders.get("pay_money") * 100)).intValue()))) {
                    StudentChoice studentChoice = new StudentChoice();
                    studentChoice.setPayState(2);
                    studentChoice.setId((Integer) orders.get("id"));
                    studentChoiceService.updateByPrimaryKeySelective(studentChoice);

                    ParentMessage parentMessage = new ParentMessage();
                    parentMessage.setStudentId((Integer) orders.get("studentId"));
                    parentMessage.setMessage("您的订单 " + orderId + " 已经退款成功，请确认信息。");
                    parentMessageService.insertSelective(parentMessage);
                    resXml = PayUtil.setXML("SUCCESS", "OK");
                } else {
                    resXml = PayUtil.setXML("FAIL", "参数错误");
                }
            } else {
                resXml = PayUtil.setXML("FAIL", "交易失败");
            }
        } else {
            resXml = PayUtil.setXML("FAIL", "通知签名验证失败");
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        System.out.println("返回微信消息:" + resXml);
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 2018/7/16 15:25
     * 家长查询课程页面
     *
     * @param isEnd 是否完成
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryMyCurriculum")
    public String queryMyCurriculum(Integer isEnd, HttpServletRequest request) {
        LoginParent loginParent = getLoginParent(request);
        List<Map<String, Object>> myCurriculum = studentChoiceService.queryMyCurriculumList(loginParent.getStudentId(), isEnd);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), myCurriculum);
    }

    /**
     * 2018/9/4 20:02
     * 描述一下方法的作用
     *
     * @param sccId 取消购买
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delOrder")
    public String delOrder(Integer sccId) {
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
    public String queryCurriculumForWeekType(HttpServletRequest request) {
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
