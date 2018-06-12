package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumOrderVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
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

/*@LocalLoginRepository*/
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

    @ResponseBody
    @RequestMapping("/queryCurriculum")
    public String queryCurriculum(Curriculum curriculum, int pageNum, HttpServletRequest request,
                                  HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumForListVo> curriculumPageTableForm = curriculumService.queryCurriculums(curriculum, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/queryCurriculumOrder")
    public String queryCurriculumOrder(Integer isHandle, int pageNum, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        PageTableForm<CurriculumOrderVo> curriculumPageTableForm = scApplyService.queryCurriculumOrder(isHandle, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculumPageTableForm);
    }

    @ResponseBody
    @RequestMapping("/selectCurriculumById")
    public String selectCurriculumById(Integer id, HttpServletRequest request,
                                       HttpServletResponse response, HttpSession session) {
        CurriculumVo curriculum = curriculumService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), curriculum);
    }

    @ResponseBody
    @RequestMapping("/addCurriculum")
    public String addCurriculum(Curriculum curriculum, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        String uniqueNum = IdProvider.createUUIDId();
        curriculum.setUniqueNum(uniqueNum);
        curriculumService.addCurriculum(curriculum);
        Curriculum result = curriculumService.selectIdByUniqueNum(curriculum.getUniqueNum());
        System.out.println(curriculum.getUniqueNum());
        Object returnMessage = new Object();
        if (result == null || result.getId() == null) {
            returnMessage = "未找到返回记录";
        } else {
            returnMessage = result.getId();
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), returnMessage);
    }

    @ResponseBody
    @RequestMapping("/updateCurriculum")
    public String updateCurriculum(Curriculum curriculum, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        curriculumService.updateCurriculum(curriculum);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @RequestMapping("/uploadCurriculumCover")
    @ResponseBody
    public String uploadCurriculumCover(MultipartHttpServletRequest request) {
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

    @ResponseBody
    @RequestMapping("/deleteCurriculum")
    public String deleteCurriculum(Integer id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        curriculumService.deleteCurriculum(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/queryConsumablesByCurId")
    public String queryConsumablesByCurId(Integer curId, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        List<Consumables> consumablesList = consumablesService.queryConsumablesByCurId(curId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), consumablesList);
    }

    @ResponseBody
    @RequestMapping("/addConsumables")
    public String addConsumables(Consumables consumables, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        consumablesService.insertSelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/updateConsumables")
    public String updateConsumables(Consumables consumables, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.updateByPrimaryKeySelective(consumables);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    @ResponseBody
    @RequestMapping("/deleteConsumables")
    public String deleteConsumables(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        consumablesService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

}
