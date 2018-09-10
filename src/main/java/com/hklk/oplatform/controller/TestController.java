package com.hklk.oplatform.controller;

import com.hklk.oplatform.comm.cache.RedisObjCache;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.STeacher;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.provider.PasswordProvider;
import com.hklk.oplatform.service.STeacherService;
import com.hklk.oplatform.service.UserService;
import com.hklk.oplatform.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@RequestMapping("/test")
@Controller
public class TestController extends BaseController {

    @Resource
    RedisObjCache redisObjCache;

    @Autowired
    UserService userService;

    @Autowired
    STeacherService sTeacherService;

    /**
     * @author 曹良峰
     * @Description 上传测试
     * @Date 16:10 2018/5/24
     * @Param [request]
     * @Return java.lang.String
     **/
    @RequestMapping("/Upload")
    @ResponseBody
    public String commUploadB(MultipartHttpServletRequest request) {
        try {
            MultipartFile file = request.getFile("file");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + System.currentTimeMillis() + "." + request.getParameter("type");
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
     * @author 曹良峰
     * @Description 测试导出excel
     * @Date 16:38 2018/5/24
     * @Param [request, response]
     * @Return java.lang.String
     **/
    @RequestMapping("/exportExcel")
    @ResponseBody
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) {
        String[] columnHeader = {"id", "username", "password", "createtime", "nickname", "remark", "state", "des", "userIco"};
        String[] fieldNames = {"主键", "用户名", "密码", "创建时间", "昵称", "备注", "状态", "描述", "头像"};
        PageTableForm<User> users = userService.queryUsers(new User(), 1, pageSize);
        List<User> tmp = users.getObjList();
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=details.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", tmp, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    /**
     * @author 曹良峰
     * @Description 上传测试
     * @Date 16:10 2018/5/24
     * @Param [request]
     * @Return java.lang.String
     **/
    @RequestMapping("/smsTest")
    @ResponseBody
    public String smsTest(String phoneNum) {
        try {
            String templateCode = "SMS_143863465";
            Map<String, String> param = new HashMap<>();
            param.put("curriculum", "走进社区 发现社会");
            param.put("reason", "申请人数不足");
            param.put("person", "杨主任");
            SmsUtil.sendSms(phoneNum, templateCode, param);
            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.UPLOAD_ERROR, StatusCode.getStatusMsg(StatusCode.UPLOAD_ERROR));
        }
    }

    /**
     * 2018/7/4 12:51
     * 导出老师列表 excel
     *
     * @return {"resultCode":200,"resultMsg":"成功"}
     * @author 曹良峰
     */
    @RequestMapping("/exportExcelForTeacher")
    @ResponseBody
    public String exportExcelForStudent(HttpServletRequest request,
                                        HttpServletResponse response, HttpSession session) {

        String[] columnHeader = {"tName", "phone", "sex", "remark"};
        String[] fieldNames = {"名称", "手机号", "性别", "备注"};
        List<STeacher> sTeachers = sTeacherService.queryTeacherBySchoolIdForList(1);
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=teachers.xlsx");
            response.setContentType("application/msexcel");
            ExcelUtils.exportExcel(out, "xlsx", sTeachers, fieldNames, columnHeader);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
    }


    @RequestMapping("/importExcel")
    @ResponseBody
    public String importExcel(MultipartHttpServletRequest request) {

        try {
            MultipartFile file = request.getFile("uploadfile");// 与页面input的name相同
            String savePath = request.getSession().getServletContext().getRealPath("/")
                    + "/uploadTempDirectory/" + file.getOriginalFilename();
            File fileTemp = new File(savePath);
            file.transferTo(fileTemp);

            String[] fieldNames = {"sNum", "fullName", "sex", "parentName", "parentPhone"};
            List<SStudent> sStudents = ExcelUtils.importExcel(fileTemp, SStudent.class, fieldNames, 1, 0, 0);

            return ToolUtil.buildResultStr(StatusCode.SUCCESS, StatusCode.getStatusMsg(StatusCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
        }
    }

    @RequestMapping("/testLong")
    @ResponseBody
    public String testRedis(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        final AsyncContext ctx = request.startAsync();

        System.out.println("进入Servlet的时间：" + System.currentTimeMillis() + ".");


        ctx.setTimeout(200000);
        //new Work(ctx).start();
        System.out.println("结束Servlet的时间：" + System.currentTimeMillis() + ".");

        return "111";
    }

    public @ResponseBody
    String timerPollReport() {
        Map<String, Object> report;
        //hold住连接
        while (true) {
            try {
                if (true) {
                    report = new HashMap<>();
                    report.put("test", "1");
                    System.out.println("report:" + report);
                    return JsonUtil.toJson(report);
                }
                Thread.sleep(30000);//防止循序太频繁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getminiqrQr(String accessToken) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<>();
            param.put("page", "pages/index/index");
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();

            inputStream = new ByteArrayInputStream(result);
            File file = new File("D:/用户目录/我的图片/1.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @RequestMapping("/wxMa")
    @ResponseBody
    public String wxMa(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String accessToken = "12_OWhCvsbqU-Oxd9ZLUW-LSH5WT3vU4SDqs3nkWnSL-0KuV8451pnn83XjNt-9q22GbGI6EJ-cOhAZGYQIa2x3o-4T1myM_sK3QwcNNQdofmAzz0jGOXBWAre0jQs-Xb-X-a0vEoZCMdO22gceYGLeAFAEMR";

        String result = getminiqrQr(accessToken);
        return result;
    }
}
