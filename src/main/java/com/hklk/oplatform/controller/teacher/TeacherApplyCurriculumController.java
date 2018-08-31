package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.CurriculumInsertVo;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.vo.*;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
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
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 老师申报模块
 *
 * @author 曹良峰
 * @since 1.0
 */
@TeacherLoginRepository
@RequestMapping("/teacherApply")
@Controller
public class TeacherApplyCurriculumController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    SCApplyService scApplyService;
    @Autowired
    STeacherService sTeacherService;
    @Autowired
    SSyllabusService sSyllabusService;
    @Autowired
    ConsumablesService consumablesService;

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
     * 2018/7/4 12:03
     * 分页查询课程列表
     *
     * @param curriculum 课程对象 (id,name)
     * @param pageNum    分页参数
     * @return {"resultData":{"currentPage":1,"pageSize":20,"beginIndex":0,"endIndex":20,"pageCount":2,"totalCount":39,"objList":[{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"ishc":false},{"id":67,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe88226201ae04527e8fdbbac853fbf99.jpg","name":"学生科幻画之畅想未来","subject":"21","grade":"7,8","learningStyle":"18","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":66,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXd4f42c9bc06f6399291e44ae0f4ad242.jpg","name":"玩转数学游戏","subject":"23","grade":"1,2,3,4,5,6","learningStyle":"12","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":65,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"数学经典之旅","subject":"23","grade":"7,8,9","learningStyle":"17","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《数学经典之旅》（教师 用书）《数学经典之旅》（学生用书）《数学经典之旅》（课程纲要）；","status":1,"ishc":false},{"id":64,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"挑战大脑","subject":"23","grade":"3","learningStyle":"12","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《挑战大脑》（教师用书）《挑战大脑》（学生用书）；","status":1,"ishc":false},{"id":63,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"格物致知数学STEM——高年级","subject":"23","grade":"5","learningStyle":"13","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":62,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX53b8124c02c93b262d3a09657cbaee51.jpg","name":"格物致知数学STEM——低年级","subject":"23","grade":"3","learningStyle":"13","classHours":"18","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《格物致知数学STEM》（课程纲要）《格物致知数学STEM》（教师用书）《格物致知数学STEM》（学生用书）；","status":1,"ishc":false},{"id":61,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX823eeef5210191d09596186d3c932fc8.jpg","name":"童心童画——油画","subject":"21","grade":"3,4,5,6","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《童心童画——油画》（教师用书）《童心童画——油画》（学生用书）；","status":1,"ishc":false},{"id":60,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX4d57f44095e35383ad57e0762810fbd9.jpg","name":"陶·韵--十二孔陶笛基础入门","subject":"24","grade":"3,4,5","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"","status":1,"ishc":false},{"id":59,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXcb2a5a20a82799709f94d417ce5efb25.jpg","name":"播种微笑","subject":"22","grade":"1","learningStyle":"11","classHours":"16","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《播种微笑》（教师用书）；","status":1,"ishc":false},{"id":58,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXfe0772b90bd9b0e4a8ef5cc211203610.jpg","name":"中小学生四阶自主班会能力课程","subject":"22","grade":"3,4,5,6","learningStyle":"11","classHours":"8","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《中小学生四阶自主班会能力课程》（教师用书） 《中小学生四阶自主班会能力课程》 （学生用书）；","status":1,"ishc":false},{"id":56,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX5005e61e36beb71ca6a1209d1b94327b.jpg","name":"语言表达训练","subject":"26","grade":"4","learningStyle":"14","classHours":"15","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《口语表达实践》（教师用书）《口语表达实践》（学生用书）；","status":1,"ishc":false},{"id":55,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXca32e02cb541554b90e1671d561e9229.jpg","name":"植物生态园","subject":"25","grade":"3","learningStyle":"17","classHours":"26","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《植物生态园》（教书用书）《植物生态园》（学生用书）；","status":1,"ishc":false},{"id":54,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX24fefc3c88bf462684f6d1581e3489fd.jpg","name":"书香润泽生命，悦读成就未来","subject":"26","grade":"5,6","learningStyle":"14","classHours":"16","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《书香润泽生命 悦读成就未来》（教师用书）《书香润泽生命 悦读成就未来》（学生用书）；","status":1,"ishc":false},{"id":53,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX597ae3fe9e8eef1d7c7e657f93135ebe.jpg","name":"初中英语情景短剧学习与表演课程","subject":"27","grade":"7,8","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《初中英语情景短剧学习与表演课程》（教师用书） 《初中英语情景短剧学习与表演课程》（学生用书）；","status":1,"ishc":false},{"id":52,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX35ae6706645e58c992c981385cad5b29.jpg","name":"七年级英语口语交际课程","subject":"27","grade":"7","learningStyle":"14","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《七年级英语口语交际课程》（教师用书）《七年级英语口语交际课程》（学生用书）","status":1,"ishc":false},{"id":51,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX38d8d1245e48ba10ca6197c872c103aa.jpg","name":"“深圳精神”主题体验式德育课程","subject":"22","grade":"1,2,3,4,5,6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《深圳精神》（教师手册）《深圳精神》（学生用书）；","status":1,"ishc":false},{"id":50,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX9bc4c5b9b36cf7fe507020aae0df1a68.jpg","name":"心理委员成长","subject":"22","grade":"6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《心理委员成长》（教师用书） 《心理委员成长》（学生用书）；","status":1,"ishc":false},{"id":49,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX24d9c59b56f6c79f022291883ae3c73c.jpg","name":"共乐课程（一年级下册）","subject":"22","grade":"1","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《共乐课程（一年级下册）》（教师用书） 《共乐课程（一年级下册）》（学生用书）；","status":1,"ishc":false},{"id":48,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe473d8a06c765f47dc2485cf99dfa569.jpg","name":"德韵德育课程","subject":"22,25","grade":"3,4,5,6","learningStyle":"11","classHours":"12","collectionNum":0,"author":"好课乐课教育","encDes":"附件包含《德韵德育课程》（教书用书）《德韵德育课程》（学生用书）；","status":1,"ishc":false}]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        if (curriculum.getIsPublic() != null) {
            curriculum.setTeacherId(getLoginTeacher(request).getTeacherId());
        }
        PageTableForm<Map<String, Object>> curriculumPageTableForm = curriculumService.queryCurriculumsForTeacher(curriculum, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    /**
     * 2018/7/4 17:17
     * 添加课程
     *
     * @param curriculum 课程对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addCurriculum")
    public String addCurriculum(Curriculum curriculum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        String uniqueNum = IdProvider.createUUIDId();
        curriculum.setUniqueNum(uniqueNum);
        curriculum.setIsPublic(0);
        curriculum.setTeacherId(getLoginTeacher(request).getTeacherId());
        CurriculumInsertVo curriculumInsertVo = new CurriculumInsertVo(curriculum);
        curriculumService.addCurriculum(curriculumInsertVo);
        Integer id = curriculumService.selectIdByUniqueNum(curriculum.getUniqueNum());
        System.out.println(curriculum.getUniqueNum());
        Object returnMessage;
        if (id == null) {
            returnMessage = "未找到返回记录";
        } else {
            returnMessage = id;
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), returnMessage);
    }

    /**
     * 2018/7/4 17:17
     * 修改课程
     *
     * @param curriculum 课程对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateCurriculum")
    public String updateCurriculum(Curriculum curriculum, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        CurriculumInsertVo curriculumInsertVo = new CurriculumInsertVo(curriculum);
        curriculumService.updateCurriculum(curriculumInsertVo);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:18
     * 删除课程
     *
     * @param id 课程id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteCurriculum")
    public String deleteCurriculum(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        curriculumService.deleteCurriculum(id, getLoginTeacher(request).getTeacherId());
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:26
     * 添加耗材
     *
     * @param consumables 耗材对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addConsumables")
    public String addConsumables(Consumables consumables, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        consumablesService.insertSelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:25
     * 查询课程耗材
     *
     * @param curId 课程id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryConsumablesByCurId")
    public String queryConsumablesByCurId(Integer curId, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        List<Consumables> consumablesList = consumablesService.queryConsumablesByCurId(curId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), consumablesList);
    }

    /**
     * 2018/7/4 17:26
     * 修改耗材对象
     *
     * @param consumables 修改耗材对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateConsumables")
    public String updateConsumables(Consumables consumables, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.updateByPrimaryKeySelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:27
     * 删除耗材
     *
     * @param id 耗材id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteConsumables")
    public String deleteConsumables(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 12:11
     * 老师查询课程详情
     *
     * @param id
     * @return {"resultData":{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","enclosure":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX43743b8d4baee12223be9382e13c2736.zip","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"des":"","consumables":[]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        Map<String, Object> curriculum = curriculumService.selectCurriculumByTeacher(id, getLoginTeacher(request).getTeacherId());
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }

    /**
     * 2018/7/4 15:52
     * 查询申报课程
     *
     * @param status  申报状态
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumApply")
    public String queryCurriculumApply(Integer status, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        if (status == null) {
            status = 0;
        }
        PageTableForm<CurriculumApplyVo> curriculumPageTableForm = scApplyService.queryCurriculumApply(getLoginTeacher(request).getSchoolId(), status, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
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
    public String queryCurriculumChoice(int pageNum, String param, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumChoiceVo> curriculumChoiceVoPageTableForm = scApplyService.queryCurriculumChoice(getLoginTeacher(request).getSchoolId(), param, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumChoiceVoPageTableForm);
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
    public String queryStudentBySCAId(Integer scaId, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        List<StudentPay> sStudents = scApplyService.queryStudentBySCAId(scaId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), sStudents);
    }

    /**
     * 2018/7/4 12:12
     * 申报课程
     *
     * @param scApply 申请对象（Integer id, Integer curriculumId, String applyRemark, Date beginOfSelectTime, Date endOfSelectTime, Date currStartTime, String classPlace, Integer maxNum,String grade）
     * @return 200 成功
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateCurriculumApply")
    public String addOrUpdateCurriculumApply(SCApply scApply, Integer num, HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        if (num > 5) {
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
        LoginTeacher loginTeacher = getLoginTeacher(request);
        scApply.setSchoolId(loginTeacher.getSchoolId());
        scApply.setTeacherId(loginTeacher.getTeacherId());
        if (scApply.getId() == null) {
            for (int i = 0; i < num; i++) {
                scApplyService.insertSelective(scApply);
            }
        } else {
            SCApply temp = scApplyService.selectByPrimaryKey(scApply.getId());
            if (temp.getOperatorId() != null && temp.getStatus() != 0) {
                return ToolUtil.buildResultStr(StatusCode.UPDATE_ERROR_FOR_IS_EXAMINE, StatusCode.getStatusMsg(StatusCode.UPDATE_ERROR_FOR_IS_EXAMINE));
            } else {
                scApplyService.updateByPrimaryKeySelective(scApply);
            }
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/11 10:22
     * 查询我的课程
     *
     * @param id 课程主键
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delCurriculumApply")
    public String delCurriculumApply(Integer id, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        SCApply temp = scApplyService.selectByPrimaryKey(id);
        if (temp.getOperatorId() != null && temp.getStatus() != 0) {
            return ToolUtil.buildResultStr(StatusCode.UPDATE_ERROR_FOR_IS_EXAMINE, StatusCode.getStatusMsg(StatusCode.UPDATE_ERROR_FOR_IS_EXAMINE));
        } else {
            scApplyService.deleteByPrimaryKey(id);
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/11 10:22
     * 查询我的课程
     *
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculumApplyByTeacherId")
    public String queryCurriculumApplyByTeacherId(Integer status, int pageNum, String searchParam, HttpServletRequest request,
                                                  HttpServletResponse response, HttpSession session) {
        PageTableForm<Map<String, Object>> curriculumChoiceVos = scApplyService.queryByTeacherId(getLoginTeacher(request).getTeacherId(), status, searchParam, pageNum, 10);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumChoiceVos);
    }

    /**
     * 2018/7/11 10:22
     * 查询我的课程表
     *
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/querySyllabusByTeacherId")
    public String querySyllabusByTeacherId(HttpServletRequest request,
                                           HttpServletResponse response, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            result.put("week" + i, sSyllabusService.querySyllabusByTeacher(i, getLoginTeacher(request).getTeacherId()));
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }
}
