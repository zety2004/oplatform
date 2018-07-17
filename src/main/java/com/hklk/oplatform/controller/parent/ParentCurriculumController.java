package com.hklk.oplatform.controller.parent;

import com.hklk.oplatform.comm.LoginParent;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.StudentChoice;
import com.hklk.oplatform.entity.vo.CurriculumChoiceVo;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SCApplyService;
import com.hklk.oplatform.service.SSyllabusService;
import com.hklk.oplatform.service.StudentChoiceService;
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
import java.util.List;
import java.util.Map;


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
     * @param id
     * @return {"resultData":{"id":68,"cover":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGXe92d624c22d76d35683e1f1f08b433f8.jpg","name":"用积木学科学","subject":"20","grade":"3","learningStyle":"18","classHours":"11","collectionNum":0,"author":"好课乐课教育","enclosure":"http://oss-cn-hklk.oss-cn-beijing.aliyuncs.com/KCGX43743b8d4baee12223be9382e13c2736.zip","encDes":"附件包含《用积木学科学》（教师用书）《用积木学科学》（学生用书）\n《用积木学科学》（课程纲要）；","status":1,"des":"","consumables":[]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        Map<String, Object> curriculum = curriculumService.selectByPrimaryKey(id);
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
        Map<String, List<CurriculumChoiceVo>> curriculumPageTableForm = scApplyService.queryHotCurriculumForParent(loginParent.getSchoolId(), loginParent.getGrade());
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
    public String queryAllCurriculumByParent(HttpServletRequest request,
                                             HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        List<CurriculumChoiceVo> curriculumChoiceVos = scApplyService.queryAllCurriculumForParent(loginParent.getSchoolId());
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
    public String insertStudentChoice(Integer scaId, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session) {
        LoginParent loginParent = getLoginParent(request);
        Integer num = studentChoiceService.queryParentApplyForVerification(loginParent.getSchoolId(), scaId, loginParent.getStudentId());
        if (num > 1) {
            return ToolUtil.buildResultStr(StatusCode.INSERT_ERROR_FOR_PARENT_APPLY, StatusCode.getStatusMsg(StatusCode.INSERT_ERROR_FOR_PARENT_APPLY));
        } else {
            StudentChoice studentChoice = new StudentChoice();
            studentChoice.setScaId(scaId);
            studentChoice.setStudentId(loginParent.getStudentId());
            studentChoiceService.insertSelective(studentChoice);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }


}