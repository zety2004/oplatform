package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.CurriculumInsertVo;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.provider.IdProvider;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.ConsumablesService;
import com.hklk.oplatform.service.CurriculumService;
import com.hklk.oplatform.service.SCApplyService;
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
 * 运营课程管理
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editcm")
@Controller
public class EditCurriculumController extends BaseController {
    @Autowired
    CurriculumService curriculumService;
    @Autowired
    ConsumablesService consumablesService;
    @Autowired
    SCApplyService scApplyService;

    protected int pageSize = 12;

    /**
     * 2018/7/4 17:15
     * 查询课程列表
     *
     * @param curriculum 课程对象
     * @param pageNum    分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumForListVo> curriculumPageTableForm = curriculumService.queryCurriculums(curriculum, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    /**
     * 2018/7/4 17:16
     * 查询课程详情
     *
     * @param id
     * @return java.lang.String
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
        CurriculumInsertVo curriculumInsertVo = new CurriculumInsertVo(curriculum);
        curriculumService.addCurriculum(curriculumInsertVo);
        Integer id = curriculumService.selectIdByUniqueNum(curriculum.getUniqueNum());
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
     * 2018/7/4 17:17
     * 上传裁剪图片
     *
     * @param request （uploadfile）
     * @return java.lang.String
     * @author 曹良峰
     */
    @RequestMapping("/uploadCutImage")
    @ResponseBody
    public String uploadCutImage(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("file");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + System.currentTimeMillis()+ "." + request.getParameter("type");
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String fileKey = "KCGX" + PasswordProvider.getMd5ByFile(fileTemp) + "." + request.getParameter("type");
            OssUtil.uploadFile(fileKey, fileTemp);
            String accessToDomainNames = PropUtil.getProperty("ossAccessToDomainNames") + "/" + fileKey;
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), accessToDomainNames);
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.UPLOAD_ERROR, StatusCode.getStatusMsg(StatusCode.UPLOAD_ERROR));
        }
    }


    /**
     * 2018/7/4 17:17
     * 上传文件
     *
     * @param request （uploadfile）
     * @return java.lang.String
     * @author 曹良峰
     */
    @RequestMapping("/uploadCurriculumCover")
    @ResponseBody
    public String uploadCurriculumCover(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("uploadfile");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
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
        curriculumService.deleteCurriculum(id);
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

}
