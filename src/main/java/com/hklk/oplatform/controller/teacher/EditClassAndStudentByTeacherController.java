package com.hklk.oplatform.controller.teacher;

import com.hklk.oplatform.comm.LoginTeacher;
import com.hklk.oplatform.controller.BaseController;
import com.hklk.oplatform.entity.table.SClass;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.vo.ImportStudentVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.entity.vo.SClassVo;
import com.hklk.oplatform.filter.repo.TeacherLoginRepository;
import com.hklk.oplatform.service.SClassService;
import com.hklk.oplatform.service.SStudentService;
import com.hklk.oplatform.util.ExcelUtils;
import com.hklk.oplatform.util.FileUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 老师管理学生
 *
 * @author 曹良峰
 * @since 1.0
 */
@TeacherLoginRepository
@RequestMapping("/tEditStudent")
@Controller
public class EditClassAndStudentByTeacherController extends BaseController {

    @Autowired
    SClassService sClassService;
    @Autowired
    SStudentService sStudentService;

    /**
     * 2018/7/4 12:15
     * 分页查询班级
     *
     * @param param   筛选参数
     * @param pageNum 分页参数
     * @return {"resultData":{"currentPage":1,"pageSize":20,"beginIndex":0,"endIndex":20,"pageCount":1,"totalCount":14,"objList":[{"id":21,"schoolId":5,"grade":4,"remark":"阿斯顿发生大范德萨都是撒的发生发的","name":"四年级四班","createBy":null,"createTime":"2018-06-25 03:29:24","studentNum":21},{"id":23,"schoolId":5,"grade":1,"remark":"一年级一班","name":"一年级一班","createBy":null,"createTime":"2018-06-27 10:02:27","studentNum":0},{"id":25,"schoolId":5,"grade":1,"remark":"一年级四班edit","name":"一年级四班edit","createBy":null,"createTime":"2018-06-27 10:03:21","studentNum":0},{"id":26,"schoolId":5,"grade":1,"remark":"一年级add","name":"add","createBy":null,"createTime":"2018-06-27 10:03:55","studentNum":0},{"id":29,"schoolId":5,"grade":1,"remark":"阿斯蒂芬","name":"部门三三edit","createBy":null,"createTime":"2018-06-27 10:32:11","studentNum":0},{"id":30,"schoolId":5,"grade":1,"remark":"啊沙发电视柜课时发水立方第四个","name":"after edit——add","createBy":null,"createTime":"2018-06-27 10:32:36","studentNum":0},{"id":31,"schoolId":5,"grade":1,"remark":"asfdsf","name":"asfdsf","createBy":null,"createTime":"2018-06-27 11:02:04","studentNum":1},{"id":32,"schoolId":5,"grade":1,"remark":"safdsgds","name":"asfdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":35,"schoolId":5,"grade":1,"remark":"sadsfdsg","name":"asdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":36,"schoolId":5,"grade":1,"remark":"safdsgds","name":"asfdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":37,"schoolId":5,"grade":1,"remark":"sadsfdsg","name":"asdsfds","createBy":null,"createTime":"2018-06-27 11:02:21","studentNum":0},{"id":38,"schoolId":5,"grade":1,"remark":"asfdsfds","name":"asdsfds123","createBy":null,"createTime":"2018-06-27 11:02:43","studentNum":0},{"id":48,"schoolId":5,"grade":3,"remark":"阿斯顿发送到覅就死按点击覅就是打击对方家里就","name":"三年级六班","createBy":null,"createTime":"2018-07-04 03:21:24","studentNum":0},{"id":49,"schoolId":5,"grade":3,"remark":"撒旦法规离开家里看到几个路口见对方立刻感觉","name":"三年级九班","createBy":null,"createTime":"2018-07-04 03:21:43","studentNum":0}]},"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryClasses")
    public String queryClasses(String param, int pageNum, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        LoginTeacher loginTeacher = getLoginTeacher(request);
        PageTableForm<SClassVo> pageTableForm = sClassService.queryClasses(param, loginTeacher.getSchoolId(), loginTeacher.getTeacherId(), pageNum, pageSize);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), pageTableForm);
    }

    /**
     * 2018/7/4 12:16
     * 添加修改班级
     *
     * @param sClass (Integer id,Integer grade,String remark, String name)
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateClass")
    public String addOrUpdateClass(SClass sClass, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        Integer schoolId = getLoginTeacher(request).getSchoolId();
        SClass temp = sClassService.selectByNameForValidate(sClass.getName(), schoolId);
        if (sClass.getId() == null && temp != null) {
            return ToolUtil.buildResultStr(StatusCode.CLASS_EX, StatusCode.getStatusMsg(StatusCode.CLASS_EX));
        } else if (sClass.getId() != null && temp != null && temp.getId() != sClass.getId()) {
            return ToolUtil.buildResultStr(StatusCode.CLASS_EX, StatusCode.getStatusMsg(StatusCode.CLASS_EX));
        } else {
            sClass.setSchoolId(schoolId);
            if (sClass.getId() != null) {
                sClass.setCreateBy(getLoginTeacher(request).getTeacherId());
            }
            sClassService.insertOrUpdateByPrimaryKeySelective(sClass);

            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 12:46
     * 删除班级
     *
     * @param id 班级id
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delClass")
    public String delClass(Integer id, HttpServletRequest request,
                           HttpServletResponse response, HttpSession session) {
        LoginTeacher loginTeacher = getLoginTeacher(request);
        SClass temp = sClassService.selectByPrimaryKey(id);
        if (temp.getCreateBy() == null || temp.getCreateBy() != loginTeacher.getTeacherId()) {
            return ToolUtil.buildResultStr(StatusCode.VALIDATE_CLASS_IS_TEACHER_CREATE, StatusCode.getStatusMsg(StatusCode.VALIDATE_CLASS_IS_TEACHER_CREATE));
        } else {
            sClassService.deleteByPrimaryKey(id);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 12:47
     * 查询班级下的学生
     *
     * @param param   筛选参数
     * @param classId 班级id
     * @return {"resultData":[{"id":43,"classId":21,"fullName":"阿斯顿","sex":"1","parentName":"换个地方","parentPhone":"312351351","sNum":"5351351351","wechatId":null,"createTime":"2018-07-02 02:42:20"},{"id":44,"classId":21,"fullName":"mnnmn","sex":"1","parentName":"sad开了房","parentPhone":"6354145","sNum":"251","wechatId":null,"createTime":"2018-07-02 03:31:33"},{"id":45,"classId":21,"fullName":"jk","sex":"1","parentName":"sad开了房","parentPhone":"6354145","sNum":"54351541","wechatId":null,"createTime":"2018-07-02 03:39:26"},{"id":46,"classId":21,"fullName":"科技进步快","sex":"1","parentName":"第三方","parentPhone":"5615151","sNum":"6515355","wechatId":null,"createTime":"2018-07-02 03:58:40"},{"id":47,"classId":21,"fullName":"撒旦法","sex":"1","parentName":"无法收到","parentPhone":"156516651651","sNum":"54515151","wechatId":null,"createTime":"2018-07-02 03:58:55"},{"id":48,"classId":21,"fullName":"申达股份","sex":"1","parentName":"本公告发布","parentPhone":"5515154135351","sNum":"646151","wechatId":null,"createTime":"2018-07-02 03:59:10"},{"id":49,"classId":21,"fullName":"大幅度发","sex":"1","parentName":"撒地方撒旦","parentPhone":"15151513311","sNum":"415151351315","wechatId":null,"createTime":"2018-07-02 03:59:22"},{"id":50,"classId":21,"fullName":"热毛巾","sex":"1","parentName":"韩国人","parentPhone":"2451331544414","sNum":"31565165151","wechatId":null,"createTime":"2018-07-02 03:59:38"},{"id":51,"classId":21,"fullName":"个人风格","sex":"1","parentName":"光和热","parentPhone":"65151","sNum":"651351351","wechatId":null,"createTime":"2018-07-02 03:59:51"},{"id":52,"classId":21,"fullName":"韩国人法国人","sex":"1","parentName":"个人个人","parentPhone":"1531513135","sNum":"6516556","wechatId":null,"createTime":"2018-07-02 04:00:02"},{"id":53,"classId":21,"fullName":"电饭锅不","sex":"1","parentName":"豆腐干啥都","parentPhone":"56515135315","sNum":"54156454468","wechatId":null,"createTime":"2018-07-02 04:00:15"},{"id":54,"classId":21,"fullName":"发个链","sex":"1","parentName":"大公鸡","parentPhone":"的方式发送到","sNum":"546554156","wechatId":null,"createTime":"2018-07-02 04:00:32"},{"id":55,"classId":21,"fullName":"得到更好的","sex":"1","parentName":"好人多个","parentPhone":"5764468565","sNum":"46465456","wechatId":null,"createTime":"2018-07-02 04:00:43"},{"id":56,"classId":21,"fullName":"电饭锅的说法","sex":"1","parentName":"撒旦法","parentPhone":"656151513","sNum":"9+5656556","wechatId":null,"createTime":"2018-07-02 04:00:57"},{"id":57,"classId":21,"fullName":"怪不得发生","sex":"1","parentName":"适当","parentPhone":"6511655611","sNum":"6516515151","wechatId":null,"createTime":"2018-07-02 04:01:09"},{"id":58,"classId":21,"fullName":"打个包","sex":"1","parentName":"打开了毛孔立马","parentPhone":"65155551351","sNum":"411515151","wechatId":null,"createTime":"2018-07-02 04:01:20"},{"id":59,"classId":21,"fullName":"豆腐干豆腐","sex":"1","parentName":"大幅度发","parentPhone":"5415455151","sNum":"5456545","wechatId":null,"createTime":"2018-07-02 04:01:36"},{"id":60,"classId":21,"fullName":"是豆腐干豆腐","sex":"1","parentName":"对方改变对方","parentPhone":"46844","sNum":"564565656","wechatId":null,"createTime":"2018-07-02 04:01:50"},{"id":61,"classId":21,"fullName":"多个板","sex":"1","parentName":"对反腐败","parentPhone":"98489448","sNum":"65168486489","wechatId":null,"createTime":"2018-07-02 04:02:04"},{"id":62,"classId":21,"fullName":"分管干部","sex":"1","parentName":"翻滚吧","parentPhone":"156155485485","sNum":"897897484","wechatId":null,"createTime":"2018-07-02 04:02:20"},{"id":63,"classId":21,"fullName":"电饭煲的","sex":"1","parentName":"粉色代表","parentPhone":"98489455252","sNum":"9879844615","wechatId":null,"createTime":"2018-07-02 04:02:36"}],"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/queryStudentByClassId")
    public String queryStudentByClassId(String param, Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {
        List<SStudent> result = sStudentService.queryStudentByClassId(param, classId);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
    }

    /**
     * 2018/7/4 12:49
     * 添加修改学生
     *
     * @param sStudent 学生对象 (Integer classId,String fullName,String sex,private String parentName,private String parentPhone,String sNum)
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/addOrUpdateStudent")
    public String addOrUpdateStudent(SStudent sStudent, HttpServletRequest request,
                                     HttpServletResponse response, HttpSession session) {
        SStudent temp = sStudentService.selectBySNumForValidate(getLoginTeacher(request).getSchoolId(), sStudent.getsNum());
        if (sStudent.getId() == null && temp != null) {
            System.out.println(getLoginTeacher(request).getNickName() + "老师操作!");
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else if (sStudent.getId() != null && temp != null && temp.getId() != sStudent.getId()) {
            return ToolUtil.buildResultStr(StatusCode.STUDENT_EX, StatusCode.getStatusMsg(StatusCode.STUDENT_EX));
        } else {
            sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        }
    }

    /**
     * 2018/7/4 12:50
     * 删除学生
     *
     * @param id 学生id
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @ResponseBody
    @RequestMapping("/delStudent")
    public String delStudent(Integer id, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        sStudentService.deleteByPrimaryKey(id);
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));

    }

    /**
     * 2018/7/4 12:51
     * 导出学生 excel
     *
     * @param classId 班级id
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @RequestMapping("/exportExcelForStudent")
    @ResponseBody
    public String exportExcelForStudent(Integer classId, HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {

        String[] columnHeader = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
        String[] fieldNames = {"学号", "姓名", "性别", "家长姓名", "家长联系电话"};
        List<SStudent> result = sStudentService.queryStudentByClassId(null, classId);
        try {
            System.out.println("teacher export ----------------------");
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=students.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", result, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }

    /**
     * 2018/7/4 12:52
     * 导入学生 excel
     *
     * @param classId 班级id
     * @return {"resultCode":200,"resultMsg":"成功"}  code:1015 存在失败  300 操作失败
     * @author 曹良峰
     */
    @RequestMapping("/importExcelForStudent")
    @ResponseBody
    public String importExcelForStudent(Integer classId, MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("importExcel");// 与页面input的name相同
            if (FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xlsx") == -1 && FileUtils.getFilenameExtension(file.getOriginalFilename()).indexOf("xls") == -1) {
                return ToolUtil.buildResultStr(StatusCode.FILE_IS_NOT_RIGHT, StatusCode.getStatusMsg(StatusCode.FILE_IS_NOT_RIGHT));
            }
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);
            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);
            Integer schoolId = getLoginTeacher(request).getSchoolId();
            List<ImportStudentVo> errorInsert = new ArrayList<>();
            int index = 0;
            for (SStudent sStudent : sStudents) {
                if (sStudent.getsNum() == null || "".equals(sStudent.getsNum())) {
                    ImportStudentVo importStudentVo = new ImportStudentVo();
                    importStudentVo.setFullName(sStudent.getFullName());
                    importStudentVo.setsNum(sStudent.getsNum());
                    importStudentVo.setReason("缺少关键字段，请检查后重新导入");
                    errorInsert.add(importStudentVo);
                    index++;
                    continue;
                }
                SStudent temp = sStudentService.selectBySNumForValidate(schoolId, sStudent.getsNum());
                if (sStudent.getId() == null && temp != null) {
                    ImportStudentVo importStudentVo = new ImportStudentVo();
                    importStudentVo.setFullName(sStudent.getFullName());
                    importStudentVo.setsNum(sStudent.getsNum());
                    SClass sClass = sClassService.selectByPrimaryKey(temp.getClassId());
                    importStudentVo.setReason("该学生已存在于" + sClass.getName() + "班中");
                    errorInsert.add(importStudentVo);
                    index++;
                } else {
                    sStudent.setClassId(classId);
                    sStudentService.insertOrUpdateByPrimaryKeySelective(sStudent);
                }
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", sStudents.size());
            result.put("failList", errorInsert);
            if (index > 0) {
                return ToolUtil.buildResultStr(StatusCode.IMPORTERROR_STUDENT, StatusCode.getStatusMsg(StatusCode.IMPORTERROR_STUDENT), result);
            } else {
                return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS), result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }
}
