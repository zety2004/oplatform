package com.hklk.oplatform.controller.school;

import com.aliyuncs.exceptions.ClientException;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.entity.vo.CurriculumApplyVo;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.StudentPay;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.*;
import com.hklk.oplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 学校管理课程
 *
 * @author 曹良峰
 * @since 1.0
 */
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
    @Autowired
    TeacherMessageService teacherMessageService;
    @Autowired
    ParentMessageService parentMessageService;

    /**
     * 2018/7/4 15:50
     * 学校查询课程
     *
     * @param curriculum 课程对象
     * @param pageNum    分页参数
     * @return {"resultData":{"currentPage":1,"pageSize":20,"beginIndex":0,"endIndex":20,"pageCount":2,"totalCount":39,"objList":[{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"ishc":false},{"id":67,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe88226201ae04527e8fdbbac853fbf99.jpg","name":"学生科幻画之畅想未来","subject":"21","grade":"7,8","learningStyle":"18","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":66,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXd4f42c9bc06f6399291e44ae0f4ad242.jpg","name":"玩转数学游戏","subject":"23","grade":"1,2,3,4,5,6","learningStyle":"12","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":65,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"数学经典之旅","subject":"23","grade":"7,8,9","learningStyle":"17","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《数学经典之旅》（教师 用书）《数学经典之旅》（学生用书）《数学经典之旅》（课程纲要）；","status":1,"ishc":false},{"id":64,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"挑战大脑","subject":"23","grade":"3","learningStyle":"12","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《挑战大脑》（教师用书）《挑战大脑》（学生用书）；","status":1,"ishc":false},{"id":63,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"格物致知数学STEM——高年级","subject":"23","grade":"5","learningStyle":"13","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":62,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"格物致知数学STEM——低年级","subject":"23","grade":"3","learningStyle":"13","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《格物致知数学STEM》（课程纲要）《格物致知数学STEM》（教师用书）《格物致知数学STEM》（学生用书）；","status":1,"ishc":false},{"id":61,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX823eeef5210191d09596186d3c932fc8.jpg","name":"童心童画——油画","subject":"21","grade":"3,4,5,6","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《童心童画——油画》（教师用书）《童心童画——油画》（学生用书）；","status":1,"ishc":false},{"id":60,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX4d57f44095e35383ad57e0762810fbd9.jpg","name":"陶·韵--十二孔陶笛基础入门","subject":"24","grade":"3,4,5","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":59,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXcb2a5a20a82799709f94d417ce5efb25.jpg","name":"播种微笑","subject":"22","grade":"1","learningStyle":"11","classHours":"16","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《播种微笑》（教师用书）；","status":1,"ishc":false},{"id":58,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXfe0772b90bd9b0e4a8ef5cc211203610.jpg","name":"中小学生四阶自主班会能力课程","subject":"22","grade":"3,4,5,6","learningStyle":"11","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《中小学生四阶自主班会能力课程》（教师用书） 《中小学生四阶自主班会能力课程》 （学生用书）；","status":1,"ishc":false},{"id":56,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX5005e61e36beb71ca6a1209d1b94327b.jpg","name":"语言表达训练","subject":"26","grade":"4","learningStyle":"14","classHours":"15","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《口语表达实践》（教师用书）《口语表达实践》（学生用书）；","status":1,"ishc":false},{"id":55,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXca32e02cb541554b90e1671d561e9229.jpg","name":"植物生态园","subject":"25","grade":"3","learningStyle":"17","classHours":"26","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《植物生态园》（教书用书）《植物生态园》（学生用书）；","status":1,"ishc":false},{"id":54,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX24fefc3c88bf462684f6d1581e3489fd.jpg","name":"书香润泽生命，悦读成就未来","subject":"26","grade":"5,6","learningStyle":"14","classHours":"16","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《书香润泽生命 悦读成就未来》（教师用书）《书香润泽生命 悦读成就未来》（学生用书）；","status":1,"ishc":false},{"id":53,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX597ae3fe9e8eef1d7c7e657f93135ebe.jpg","name":"初中英语情景短剧学习与表演课程","subject":"27","grade":"7,8","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《初中英语情景短剧学习与表演课程》（教师用书） 《初中英语情景短剧学习与表演课程》（学生用书）；","status":1,"ishc":false},{"id":52,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX35ae6706645e58c992c981385cad5b29.jpg","name":"七年级英语口语交际课程","subject":"27","grade":"7","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《七年级英语口语交际课程》（教师用书）《七年级英语口语交际课程》（学生用书）","status":1,"ishc":false},{"id":51,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX38d8d1245e48ba10ca6197c872c103aa.jpg","name":"“深圳精神”主题体验式德育课程","subject":"22","grade":"1,2,3,4,5,6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《深圳精神》（教师手册）《深圳精神》（学生用书）；","status":1,"ishc":false},{"id":50,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX9bc4c5b9b36cf7fe507020aae0df1a68.jpg","name":"心理委员成长","subject":"22","grade":"6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《心理委员成长》（教师用书） 《心理委员成长》（学生用书）；","status":1,"ishc":false},{"id":49,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX24d9c59b56f6c79f022291883ae3c73c.jpg","name":"共乐课程（一年级下册）","subject":"22","grade":"1","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《共乐课程（一年级下册）》（教师用书） 《共乐课程（一年级下册）》（学生用书）；","status":1,"ishc":false},{"id":48,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe473d8a06c765f47dc2485cf99dfa569.jpg","name":"德韵德育课程","subject":"22,25","grade":"3,4,5,6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《德韵德育课程》（教书用书）《德韵德育课程》（学生用书）；","status":1,"ishc":false}]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        //这里学校id放在老师id字段作为查询参数
        curriculum.setTeacherId(getLoginSchool(request).getSchoolId());
        PageTableForm<Map<String, Object>> curriculumPageTableForm = curriculumService.queryCurriculumsForSchool(curriculum, pageNum, pageSize);
        return ResultUtils.successStr(curriculumPageTableForm);
    }

    /**
     * 2018/7/4 15:51
     * 学校查询课程详情
     *
     * @param id 课程id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        Map<String, Object> curriculum = curriculumService.selectByPrimaryKey(id);
        return ResultUtils.successStr(curriculum);
    }

    /**
     * 2018/7/4 15:52
     * 查询申报课程
     *
     * @param status 申报状态
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumApply")
    public String queryCurriculumApply(Integer status, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        if (status == null) {
            status = 0;
        }
        List<CurriculumApplyVo> curriculumApplyVos = scApplyService.queryCurriculumApply(getLoginSchool(request).getSchoolId(), status);
        return ResultUtils.successStr(curriculumApplyVos);
    }

    /**
     * 2018/7/4 15:52
     * 学校查询老师待审核课程
     *
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumApplyForExamine")
    public String queryCurriculumApplyForExamine(int pageNum, HttpServletRequest request,
                                                 HttpServletResponse response, HttpSession session) {
        PageTableForm<Map<String, Object>> curriculumPageTableForm = scApplyService.queryCurriculumApplyForExamine(getLoginSchool(request).getSchoolId(), pageNum, pageSize);
        return ResultUtils.successStr(curriculumPageTableForm);
    }

    /**
     * 2018/7/4 15:53
     * 查询课程选课情况
     *
     * @param pageNum 分页参数
     * @param param   筛选参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumChoice")
    public String queryCurriculumChoice(int pageNum, String param, Integer isEnd, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm;
        if (isEnd == null || isEnd == 0) {
            curriculumChoiceVoPageTableForm = scApplyService.queryCurriculumChoice(getLoginSchool(request).getSchoolId(), param, pageNum, pageSize);
        } else {
            curriculumChoiceVoPageTableForm = scApplyService.queryCurriculumChoiceForEnd(getLoginSchool(request).getSchoolId(), param, pageNum, pageSize);
        }
        return ResultUtils.successStr(curriculumChoiceVoPageTableForm);
    }


    /**
     * 2018/7/4 15:55
     * 申报课程选课情况
     *
     * @param scaId 课程申报id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryStudentBySCAId")
    public String queryStudentBySCAId(Integer scaId, Integer isEnd, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        List<StudentPay> sStudents;
        if (isEnd == null || isEnd == 0) {
            sStudents = scApplyService.queryStudentBySCAId(scaId);
        } else {
            sStudents = scApplyService.queryStudentBySCAIdForEnd(scaId);
        }
        return ResultUtils.successStr(sStudents);
    }

    @ResponseBody
    @RequestMapping("/verificationStudentNum")
    public String verificationStudentNum(Integer scaId) {
        int studentNum = sSyllabusService.selectCountStudentNumBySCId(scaId);
        return ResultUtils.successStr(studentNum);
    }

    /**
     * 2018/8/9 18:06
     * 描述一下方法的作用
     *
     * @param ids
     * @param status
     * @param curriculumName
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateCurriculumApply")
    public String updateCurriculumApply(String ids, Integer status, String curriculumName, String closeReason, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) throws ClientException {
        for (String id : ids.split(",")) {
            Map<String, Object> temp = scApplyService.selectByTeacherApplyForAuditing(Integer.valueOf(id));
            boolean isOp = (int) temp.get("status") != 0 && status != 0 && (int) temp.get("status") != -1 && status != -1;
            if (temp.get("operator_id") != null && isOp) {
                return ResultUtils.warnStr(ResultCode.CHECK_OPERATOR);
            } else {
                if (status == 0) {
                    int studentNum = sSyllabusService.selectCountStudentNumBySCId((int) temp.get("id"));
                    if (studentNum > 0) {
                        return ResultUtils.warnStr(ResultCode.HAS_STUDENT);
                    }
                }
                SCApply scApply = new SCApply();
                scApply.setId(Integer.valueOf(id));
                scApply.setStatus(status);
                if (status == -1) {
                    scApply.setIsOpenClass(0);
                    scApply.setCloseReason(closeReason);
                }
                scApply.setOperatorId(getLoginSchool(request).getSchoolAdminId());
                scApplyService.updateByPrimaryKeySelective(scApply);
                TeacherMessage teacherMessage = new TeacherMessage();
                teacherMessage.setTeacherId((int) temp.get("teacher_id"));
                if (status == 1) {
                    teacherMessage.setMessage("您申报的课程 " + curriculumName + " 已经通过审批,进入排课阶段！");
                } else if (status == 2) {
                    teacherMessage.setMessage("您申报的课程 " + curriculumName + " 被驳回！");
                } else if (status == 0) {
                    teacherMessage.setMessage("您申报的课程 " + curriculumName + " 被退回到未审核状态！");
                } else if (status == -1) {

                    sSyllabusService.querySyllabusForSCAId(Integer.valueOf(id)).forEach(obj -> {

                        sSyllabusService.insertForEnd(obj);
                    });

                    Map<String, String> teacherSmsParam = new HashMap<>();
                    teacherSmsParam.put("curriculum", curriculumName);
                    teacherSmsParam.put("reason", closeReason);
                    teacherSmsParam.put("person", getLoginSchool(request).getNickName() + ",电话:" + getLoginSchool(request).getAccount());

                    SmsUtil.sendSms(temp.get("phone").toString(), "SMS_143863465", teacherSmsParam);
                    List<StudentPay> sStudents = scApplyService.queryStudentBySCAId(Integer.valueOf(id));
                    sStudents.forEach(obj -> {
                        Map<String, String> parentSmsParam = new HashMap<>();
                        parentSmsParam.put("attr1", obj.getFullName());
                        parentSmsParam.put("attr2", curriculumName);
                        parentSmsParam.put("attr3", closeReason);
                        try {
                            SmsUtil.sendSms(obj.getParentPhone(), "SMS_144450653", parentSmsParam);
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                        ParentMessage parentMessage = new ParentMessage();
                        parentMessage.setStudentId(obj.getId());
                        parentMessage.setSign(3);
                        parentMessage.setMessage("您申请的课程 《" + curriculumName + "》 已取消开课，请在选课详情里申请退款");
                        parentMessageService.insertSelective(parentMessage);
                    });
                }
                teacherMessageService.insertSelective(teacherMessage);
            }
        }
        return ResultUtils.successStr();
    }


    /**
     * 2018/7/4 15:57
     * 添加修改课程表
     *
     * @param param table结构参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateSyllabusAll")
    public String addOrUpdateSyllabusAll(String param, HttpServletRequest request,
                                         HttpServletResponse response, HttpSession session) {
        sSyllabusService.delAndbatchSaveSyllabus(param, getLoginSchool(request).getSchoolId());
        return ResultUtils.successStr();
    }

    /**
     * 2018/7/4 15:58
     * 查询课程表
     *
     * @return java.lang.String
     * @author 曹良峰
     */
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
        return ResultUtils.successStr(result);
    }

    /**
     * 2018/7/4 15:59
     * 按行查询课程表
     *
     * @return java.lang.String
     * @author 曹良峰
     */
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
        return ResultUtils.successStr(result);
    }

    /**
     * 2018/7/4 15:59
     * 查询结束课程表
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/querySyllabusByTimeTypeForEnd")
    public String querySyllabusByTimeTypeForEnd(HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            result.put("week" + i, sSyllabusService.queryMapByTimeTypeForEnd(i, getLoginSchool(request).getSchoolId()));
        }
        return ResultUtils.successStr(result);
    }
}
