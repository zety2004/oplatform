package com.hklk.oplatform.controller.local;

import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.ChannelSchool;
import com.hklk.oplatform.entity.table.School;
import com.hklk.oplatform.entity.table.SchoolAdmin;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SchoolVo;
import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.SchoolAdminService;
import com.hklk.oplatform.service.SchoolChannelService;
import com.hklk.oplatform.service.SchoolService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 管理学校
 *
 * @author 曹良峰
 * @since 1.0
 */
@LocalLoginRepository
@RequestMapping("/editSchool")
@Controller
public class EditSchoolController extends BaseController {
    @Autowired
    SchoolService schoolService;
    @Autowired
    SchoolAdminService schoolAdminService;
    @Autowired
    SchoolChannelService schoolChannelService;


    /**
     * 2018/7/4 17:50
     * 查询学校
     *
     * @param param   学校查询参数
     * @param pageNum 分页参数
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/querySchool")
    public String querySchool(String param, int pageNum, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {
        PageTableForm<SchoolVo> schoolPageTableForm = schoolService.querySchools(param, pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), schoolPageTableForm);
    }

    /**
     * 2018/7/4 17:52
     * 根据学校id查询学校
     *
     * @param id 学校id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectSchoolById")
    public String selectSchoolById(int id, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Map<String,Object> school = schoolService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), school);
    }

    /**
     * 2018/7/4 17:52
     * 验证学校名称
     *
     * @param school 学校对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/validateSchoolName")
    public String validateSchoolName(School school, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_UNEX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_UNEX));
        }
    }

    /**
     * 2018/7/4 17:52
     * 添加学校
     *
     * @param school 学校对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addSchool")
    public String addSchool(School school, Integer channelId, HttpServletRequest request,
                            HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {


            schoolService.insertSelective(school);
            if (channelId != null) {
                String sign = ToolUtil.createId(32);
                school.setSign(sign);
                insertSchoolChannel(sign, channelId, null);
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    public void insertSchoolChannel(String sign, Integer channelId, Integer schoolId) {
        ChannelSchool channelSchool = new ChannelSchool();
        channelSchool.setChannelId(channelId);
        if (schoolId != null) {
            schoolChannelService.delChannelSchoolBySchoolId(schoolId);
            channelSchool.setSchoolId(schoolId);
        } else {
            School school = schoolService.selectSchoolBySign(sign);
            channelSchool.setSchoolId(school.getId());
        }
        schoolChannelService.insertChannelSchool(channelSchool);
    }

    /**
     * 2018/7/4 17:54
     * 修改学校
     *
     * @param school
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateSchool")
    public String updateSchool(School school, Integer channelId, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        School tmp = schoolService.selectSchoolByName(school.getName());
        if (tmp != null && tmp.getId() != school.getId()) {
            return ToolUtil.buildResultStr(StatusCode.SCHOOLNAME_EX, StatusCode.getStatusMsg(StatusCode.SCHOOLNAME_EX));
        } else {
            schoolService.updateByPrimaryKeySelective(school);
            if (channelId != null) {
                insertSchoolChannel(null, channelId, school.getId());
            }
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 17:54
     * 删除学校
     *
     * @param id 学校id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteSchool")
    public String deleteSchool(int id, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        School school = new School();
        school.setId(id);
        school.setStatus(-1);
        schoolService.updateByPrimaryKeySelective(school);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:54
     * 查询学校管理
     *
     * @param schoolId 学校id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/querySchoolAdmin")
    public String querySchoolAdmin(int schoolId, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        schoolAdminService.querySchoolAdminsBySchoolId(schoolId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:54
     * 根据id查询学校管理员
     *
     * @param id 管理员主键
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/selectSchoolAdminById")
    public String selectSchoolAdminById(int id, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        SchoolAdmin schoolAdmin = schoolAdminService.selectByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), schoolAdmin);
    }

    /**
     * 2018/7/4 17:55
     * 添加学校管理员
     *
     * @param schoolAdmin 管理员对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addSchoolAdmin")
    public String addSchoolAdmin(SchoolAdmin schoolAdmin, HttpServletRequest request,
                                 HttpServletResponse response, HttpSession session) {
        int tmp = schoolAdminService.querySchoolAdminsForCount(schoolAdmin.getSchoolId());
        if (tmp >= 10) {
            return ToolUtil.buildResultStr(StatusCode.ADMIN_NUM_VALIDATE, StatusCode.getStatusMsg(StatusCode.ADMIN_NUM_VALIDATE));
        } else {
            SchoolAdmin tmpAdmin = schoolAdminService.querySchoolAdminsByName(schoolAdmin.getAccount());
            if (tmpAdmin != null) {
                return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, "手机号已存在!");
            }
            schoolAdminService.insertSelective(schoolAdmin);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 17:55
     * 修改学校管理员
     *
     * @param schoolAdmin 管理员对象
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/updateSchoolAdmin")
    public String updateSchoolAdmin(SchoolAdmin schoolAdmin, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        SchoolAdmin tmpAdmin = schoolAdminService.querySchoolAdminsByName(schoolAdmin.getAccount());
        if (tmpAdmin != null && tmpAdmin.getId() != schoolAdmin.getId()) {
            return ToolUtil.buildResultStr(StatusCode.ADDUSER_USERNAME_EX, StatusCode.getStatusMsg(StatusCode.ADDUSER_USERNAME_EX));
        }
        schoolAdminService.updateByPrimaryKeySelective(schoolAdmin);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 17:55
     * 重置学校管理员密码
     *
     * @param id 管理员id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/resetSchoolAdminPwd")
    public String updateSchoolAdmin(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        SchoolAdmin schoolAdmin = new SchoolAdmin();
        schoolAdmin.setId(id);
        schoolAdmin.setPwd("93b1c7f49c7b917831a942fd90ffe0ca");

        schoolAdminService.updateByPrimaryKeySelective(schoolAdmin);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }


    /**
     * 2018/7/4 17:56
     * 删除学校管理员
     *
     * @param id 管理员id
     * @return java.lang.String
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/deleteSchoolAdmin")
    public String deleteSchoolAdmin(Integer id, HttpServletRequest request,
                                    HttpServletResponse response, HttpSession session) {
        schoolAdminService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }
}
